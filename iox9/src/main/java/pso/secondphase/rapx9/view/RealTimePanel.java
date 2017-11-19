/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import pso.secondphase.rapx9.control.MainControl;

/**
 *
 * @author vinihcampos
 */
public class RealTimePanel extends Application{
    
    static{ OpenCV.loadLocally(); }
    
    //Grids
    private GridPane rootPane;
    private GridPane vehicleInOut;
    private GridPane content;
    
    //Panels
    VehicleInPanel vehicleInPanel;
    VehicleOutPanel vehicleOutPanel;
    GraphsPanel graphsPanel;
    
    //Control
    MainControl control;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        vehicleInPanel = new VehicleInPanel();
        vehicleOutPanel = new VehicleOutPanel();
        graphsPanel = new GraphsPanel();
        
        //Load font
        Font.loadFont(getClass().getResource("/font/JosefinSans-Light.ttf").toExternalForm(), 20);
        
        //Init main grid
        initGrid();
        
        //Creating a scene object 
        Scene scene = new Scene(rootPane);
        
        //Defining stylesheet
        scene.getStylesheets().add(getClass().getResource("/styles/realtimepanellayout.css").toExternalForm());
      
        //Setting title to the Stage 
        stage.setTitle("Real Time Panel"); 

        //Adding scene to the stage 
        stage.setScene(scene); 
        
        //Displaying the contents of the stage 
        stage.show(); 
        
        control = new MainControl(vehicleInPanel, vehicleOutPanel, graphsPanel);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {   
        launch(args);
    }
    
    public void initGrid(){
        //Creating a Grid Pane 
        rootPane = new GridPane();
        rootPane.setPrefSize(1010, 700);
        rootPane.getStyleClass().add("root-pane");
        
        //Creating Sub-Grid Panes
        vehicleInOut = new GridPane();
        vehicleInOut.setPrefSize(1010, 350);
        
        vehicleInOut.add(vehicleInPanel.getPanel(), 0, 0);
        vehicleInOut.add(vehicleOutPanel.getPanel(), 1, 0);
        
        //Setting vehicleInOut postion
        rootPane.add(vehicleInOut, 0, 0);
        
        content = new GridPane();
        content.setPrefSize(1010, 350);
        
        GridPane notification = new GridPane();
        notification.setPrefSize(390, 350);
        notification.getStyleClass().add("gridpane-notification");
        
        content.add(graphsPanel.getPanel(), 0, 0);
        content.add(notification, 1, 0);
        
        //Setting content postion
        rootPane.add(content, 0, 1);
    }
    
}
