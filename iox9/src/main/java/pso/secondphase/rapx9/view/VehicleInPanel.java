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
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import java.util.ArrayList;
import java.util.List;
import pso.secondphase.iox9.business.notification.NotifierChainSingleton;
import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.business.statistics.CountByHoursInDayStatistics;
import pso.secondphase.iox9.business.statistics.CountByWeekDaysStatistics;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.Vehicle;

/**
 *
 * Simple example of a Vehicle Panel.
 * 
 * @author vitorgreati
 */
public class VehicleInPanel extends Observer {
    
    //Grids
    private GridPane vehicleIn; 
    private GridPane imageInAndSinesp;
    private GridPane imageIn;
    private GridPane sinespIn;
    private GridPane weekCounter;
    
    //Lables
    private Label plateIn, modelIn, brandIn, colorIn, valueIn, situationIn;
    private Label titleIn;
    private Label sunday, monday, tuesday, wednesday, thursday, friday, saturday, totalWeek;
    private Label sundayValue, mondayValue, tuesdayValue, wednesdayValue, thursdayValue, fridayValue, saturdayValue, totalWeekValue;
    
    //ImageView
    private ImageView cameraIn;
    
    public VehicleInPanel(){
    }
    
    public void init() {
        //Initiate grid
        initGrids();
        
        //Initiate titles
        initTitle();
        
        //Initiate sinesp areas
        initSinesp();
        
        //Initiate images
        initImage();
        
        //Initiate week counter
        initWeekCounter();
    }
    
    public GridPane getPanel(){
        return this.vehicleIn;
    }
    
    private void initGrids(){
        vehicleIn = new GridPane();
        vehicleIn.setPrefSize(500, 350);
        vehicleIn.getStyleClass().add("gridpane-left");
        
        imageInAndSinesp = new GridPane();
        imageInAndSinesp.setPrefSize(500, 200);
        
        imageIn = new GridPane();
        imageIn.setPrefSize(275, 200);
        imageIn.getStyleClass().add("camera");
        sinespIn = new GridPane();
        sinespIn.setPrefSize(225, 200);
        
        imageInAndSinesp.add(imageIn, 0, 0);
        imageInAndSinesp.add(sinespIn, 1, 0);
        
        weekCounter = new GridPane();
        weekCounter.setPrefSize(500, 100);
        
        vehicleIn.add(imageInAndSinesp, 0, 1);
        vehicleIn.add(weekCounter, 0, 2);
    }
    
    private void initTitle(){
        
        Rectangle bar = new Rectangle(350, 4);  
        bar.setArcWidth(6);  
        bar.setArcHeight(6);  
        bar.setFill(Color.rgb(142,68,173));
        
        titleIn = new Label("Entrada");
        titleIn.getStyleClass().add("title");
        VBox vBox = new VBox(titleIn, bar);  
        vBox.setSpacing(2);  
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));
        vehicleIn.add(vBox, 0, 0);
    }
    
    private void initSinesp(){
        //sinespIn
        plateIn = new Label("Placa: ");
        plateIn.setMinHeight(35);
        brandIn = new Label("Marca: ");
        brandIn.setMinHeight(35);
        modelIn = new Label("Modelo: ");
        modelIn.setMinHeight(35);
        colorIn = new Label("Cor: ");
        colorIn.setMinHeight(35);
        valueIn = new Label("Valor: ");
        valueIn.setMinHeight(35);
        situationIn = new Label("Situacao: ");
        situationIn.setMinHeight(35);
        
        sinespIn.add(plateIn, 0, 0);
        sinespIn.add(brandIn, 0, 1);
        sinespIn.add(modelIn, 0, 2);
        sinespIn.add(colorIn, 0, 3);
        sinespIn.add(valueIn, 0, 4);
        sinespIn.add(situationIn, 0, 5);
        
        plateIn.getStyleClass().add("sinesp");
        brandIn.getStyleClass().add("sinesp");
        modelIn.getStyleClass().add("sinesp");
        colorIn.getStyleClass().add("sinesp");
        valueIn.getStyleClass().add("sinesp");
        situationIn.getStyleClass().add("sinesp");
    }
    
    private void initImage(){        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        Image img = new Image(getClass().getResource("/img/default.jpg").toExternalForm());
        
        //imageIn
        cameraIn = new ImageView(img);
        cameraIn.setFitWidth(250);
        cameraIn.setFitHeight(200);
        imageIn.add(cameraIn, 0, 0);
    }
    
    private void initWeekCounter(){
        sunday = new Label("D");
        monday = new Label("S");
        tuesday = new Label("T");
        wednesday = new Label("Q");
        thursday = new Label("Q");
        friday = new Label("S");
        saturday = new Label("S");
        totalWeek = new Label("Total");
        
        sundayValue = new Label();
        mondayValue = new Label();
        tuesdayValue = new Label();
        wednesdayValue = new Label();
        thursdayValue = new Label();
        fridayValue = new Label();
        saturdayValue = new Label();
        totalWeekValue = new Label();
        
        weekCounter.add(sunday, 0, 0);
        weekCounter.add(monday, 1, 0);
        weekCounter.add(tuesday, 2, 0);
        weekCounter.add(wednesday, 3, 0);
        weekCounter.add(thursday, 4, 0);
        weekCounter.add(friday, 5, 0);
        weekCounter.add(saturday, 6, 0);
        weekCounter.add(totalWeek, 7, 0);
        weekCounter.add(sundayValue, 0, 1);
        weekCounter.add(mondayValue, 1, 1);
        weekCounter.add(tuesdayValue, 2, 1);
        weekCounter.add(wednesdayValue, 3, 1);
        weekCounter.add(thursdayValue, 4, 1);
        weekCounter.add(fridayValue, 5, 1);
        weekCounter.add(saturdayValue, 6, 1);
        weekCounter.add(totalWeekValue, 7, 1);
        
        sunday.getStyleClass().add("week-top-first");
        monday.getStyleClass().add("week-top");
        tuesday.getStyleClass().add("week-top");
        wednesday.getStyleClass().add("week-top");
        thursday.getStyleClass().add("week-top");
        friday.getStyleClass().add("week-top");
        saturday.getStyleClass().add("week-top");
        totalWeek.getStyleClass().add("week-top");
        
        sundayValue.getStyleClass().add("week-bottom-first");
        mondayValue.getStyleClass().add("week-bottom");
        tuesdayValue.getStyleClass().add("week-bottom");
        wednesdayValue.getStyleClass().add("week-bottom");
        thursdayValue.getStyleClass().add("week-bottom");
        fridayValue.getStyleClass().add("week-bottom");
        saturdayValue.getStyleClass().add("week-bottom");
        totalWeekValue.getStyleClass().add("week-bottom");
    }
    
    public void update(VehicleInProcessor observable, Object o) {
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Entity e = ((IORecord)o).getEntity();
                updateSinesp(e);
                System.out.println("Entrou (PIN): " + ((IORecord)o).getEntity().getIdentifier());
            }
        });        
        
    }
    
    public void updateSinesp(Entity v){
        // Panel IN
        plateIn.setText("Placa: " + v.getIdentifier());
        brandIn.setText("Marca: " + (v.getAttrs().get("brand") != null ? v.getAttrs().get("brand").value : ""));
        modelIn.setText("Modelo: " + (v.getAttrs().get("model") != null ? v.getAttrs().get("model").value : ""));
        colorIn.setText("Cor: " + (v.getAttrs().get("color") != null ? v.getAttrs().get("color").value : ""));
        valueIn.setText("Valor: R$ " + String.format("%.2f", (v.getAttrs().get("price") != null ? v.getAttrs().get("price").value : 0.0)));
        situationIn.setText("Situação: " + (v.getAttrs().get("situationCode") != null ? v.getAttrs().get("situationCode").value : ""));
        if(v.getAttrs().get("image") != null) cameraIn.setImage(SwingFXUtils.toFXImage((BufferedImage) v.getAttrs().get("image").value, null));
    }
    public void update(CountByWeekDaysStatistics observable, Object o) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<Integer> week = (ArrayList<Integer>) o;
                if(week.size() == 7){
                    sundayValue.setText(String.valueOf(week.get(0)));
                    mondayValue.setText(String.valueOf(week.get(1)));
                    tuesdayValue.setText(String.valueOf(week.get(2)));
                    wednesdayValue.setText(String.valueOf(week.get(3)));
                    thursdayValue.setText(String.valueOf(week.get(4)));
                    fridayValue.setText(String.valueOf(week.get(5)));
                    saturdayValue.setText(String.valueOf(week.get(6)));
                    
                    Integer total = 0;
                    for (Integer day : week){
                        total += day;
                    }
                    
                    totalWeekValue.setText(String.valueOf(total));
                }
                System.out.println("By week days:");
            }
        }); 
    }
    
    public void update(CountByHoursInDayStatistics observable, Object o) {
        System.out.println("By week days:");
        /*List<Integer> week = (ArrayList<Integer>) o;
        for (Integer i : week)
            System.out.println(i);
        */
    }  
}
