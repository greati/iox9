/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pso.secondphase.iox9.business.notification.NotifierChainSingleton;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.Vehicle;

/**
 * Simple example of a Vehicle Panel.
 * 
 * @author vitorgreati
 */
public class VehicleOutPanel extends Observer {
    
    //Grids
    private GridPane vehicleOut;
    private GridPane imageOutAndSinesp;
    private GridPane imageOut;
    private GridPane sinespOut;
    private GridPane timeCounter;
    
    //Labels
    private Label titleOut;
    private Label plateOut, modelOut, brandOut, colorOut, valueOut, situationOut;
    private Label entrance, exit;
    
    //ImageView
    private ImageView cameraOut;
    
    public VehicleOutPanel(){
        //Initiate grid
        initGrids();
        
        //Initiate titles
        initTitle();
        
        //Initiate sinesp areas
        initSinesp();
        
        //Initiate images
        initImage();
        
        //Initiate time counter
        initTimeCounter();
    }
    
    public GridPane getPanel(){
        return this.vehicleOut;
    }
    
    private void initGrids(){
        vehicleOut = new GridPane();
        vehicleOut.setPrefSize(510, 350);
        vehicleOut.getStyleClass().add("gridpane-right");
        
        imageOutAndSinesp = new GridPane();
        imageOutAndSinesp.setPrefSize(510, 225);
        
        imageOut = new GridPane();
        imageOut.setPrefSize(275, 200);        
        imageOut.getStyleClass().add("camera");
        sinespOut = new GridPane();
        sinespOut.setPrefSize(245, 200);
        
        imageOutAndSinesp.add(imageOut, 0, 0);
        imageOutAndSinesp.add(sinespOut, 1, 0);
        
        timeCounter = new GridPane();
        timeCounter.setPrefSize(510, 100);
        
        vehicleOut.add(imageOutAndSinesp, 0, 1);
        vehicleOut.add(timeCounter, 0, 2);
    } 
    
    private void initTitle(){
        
        Rectangle bar = new Rectangle(350, 4);  
        bar.setArcWidth(6);  
        bar.setArcHeight(6);  
        bar.setFill(Color.rgb(142,68,173));
        
        titleOut = new Label("Saída");        
        titleOut.getStyleClass().add("title");
        VBox vBox2 = new VBox(titleOut, bar);  
        vBox2.setSpacing(2);  
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setPadding(new Insets(10,10,10,10));        
        vehicleOut.add(vBox2, 0, 0);
    }
    
    private void initSinesp(){        
        //sinespOut
        plateOut= new Label("Placa: ");
        plateOut.setMinHeight(35);
        brandOut= new Label("Marca: ");
        brandOut.setMinHeight(35);
        modelOut= new Label("Modelo: ");
        modelOut.setMinHeight(35);
        colorOut= new Label("Cor: ");
        colorOut.setMinHeight(35);
        valueOut= new Label("Valor: ");
        valueOut.setMinHeight(35);
        situationOut= new Label("Situacao: ");
        situationOut.setMinHeight(35);
        
        sinespOut.add(plateOut, 0, 0);
        sinespOut.add(brandOut, 0, 1);
        sinespOut.add(modelOut, 0, 2);
        sinespOut.add(colorOut, 0, 3);
        sinespOut.add(valueOut, 0, 4);
        sinespOut.add(situationOut, 0, 5);
        
        plateOut.getStyleClass().add("sinesp");
        brandOut.getStyleClass().add("sinesp");
        modelOut.getStyleClass().add("sinesp");
        colorOut.getStyleClass().add("sinesp");
        valueOut.getStyleClass().add("sinesp");
        situationOut.getStyleClass().add("sinesp");
    }
    
    private void initImage(){        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        Image img = new Image(getClass().getResource("/img/default.jpg").toExternalForm());
        
        //imageOut
        cameraOut = new ImageView(img);
        cameraOut.setFitWidth(250);
        cameraOut.setFitHeight(200);
        imageOut.add(cameraOut, 0, 0);
    }
    
    private void initTimeCounter(){
        entrance = new Label("Entrada");
        exit = new Label("Saída");
        
        entrance.getStyleClass().add("time");
        exit.getStyleClass().add("time");
        
        timeCounter.add(entrance, 0, 0);
        timeCounter.add(exit, 1, 0);
    }
    
    public void update(VehicleOutProcessor observable, Object o) {
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Vehicle v = ((Vehicle)((IORecord)o).getEntity());
                updateSinesp(v);
                System.out.println("Saiu (POUT):"+((IORecord)o).getEntity().getIdentifier());
            }
        });  
        
    }
    
    public void updateSinesp(Vehicle v){
        // Panel IN
        plateOut.setText("Placa: " + v.getPlate());
        brandOut.setText("Marca: " + v.getBrand());
        modelOut.setText("Modelo: " + v.getModel());
        colorOut.setText("Cor: " + v.getColor());
        valueOut.setText("Valor: R$ " + String.format("%.2f", v.getValue()));
        situationOut.setText("Situação: ");
        if(v.getImage() != null) cameraOut.setImage(SwingFXUtils.toFXImage((BufferedImage) v.getImage(), null));
    }
    
    public void update(NotifierChainSingleton notifier, Object o) {
        System.out.println(((Notification)o).getMessage());
    }
    
}
