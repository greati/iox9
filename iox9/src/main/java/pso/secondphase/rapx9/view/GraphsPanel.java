/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.business.statistics.CountByHoursInDayStatistics;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;

/**
 *
 * @author vinihcampos
 */
public class GraphsPanel extends Observer {
    
    //Grid 
    private GridPane information;
    
    //Charts
    private LineChart countVehicles, meanValueVehicles;
    private Gauge parkingCount;
    
    //Auxiliar variable
    private Date d;
    private Integer numberVehicles;
    
    //Observable lists
    ObservableList<Entity> vehicles;
    
    //ListViews
    ListView<Entity> listViewVehicle;
    
    public GraphsPanel(){
        
        numberVehicles = 0;
        
        //Initiate grid
        initGrid();
        
        //Initiate charts
        initCharts();
        
        //Initiate listViews
        initListView();
    }
    
    public GridPane getPanel(){
        return this.information;
    }
    
    public void initGrid(){
        information = new GridPane();
        information.setPrefSize(620, 350);
        information.getStyleClass().add("gridpane-information");
    }

    private void initCharts(){
        //Defining the x axis             
        NumberAxis xAxisCount = new NumberAxis(0, 23, 2); 
        xAxisCount.setLabel("Horas");        
        NumberAxis xAxisMeanValues = new NumberAxis(0, 23, 2); 
        xAxisMeanValues.setLabel("Horas"); 

        //Defining the y axis   
        NumberAxis yAxisCount = new NumberAxis(0, 200, 50); 
        yAxisCount.setLabel("Nº de veículos");
        //Defining the y axis   
        NumberAxis yAxisMeanValues = new NumberAxis(0, 200, 50); 
        yAxisMeanValues.setLabel("\u0394 R$");
        
        countVehicles = new LineChart(xAxisCount, yAxisCount);
        meanValueVehicles = new LineChart(xAxisMeanValues, yAxisMeanValues);
        XYChart.Series seriesCount = new XYChart.Series();
        XYChart.Series seriesValues = new XYChart.Series();
                
        Label l = new Label("Nº de Veículos");
        l.getStyleClass().add("titles-information");
        Rectangle bar = new Rectangle(200, 2);  
        bar.setArcWidth(6);  
        bar.setArcHeight(6);  
        bar.setFill(Color.rgb(142,68,173));
        
        GaugeBuilder builder = GaugeBuilder.create().skinType(Gauge.SkinType.DASHBOARD);
        parkingCount = builder
                    .minValue(0)
                    .maxValue(200)
                    .title(" ") // Set the text for the title  
                    .titleColor(Color.WHITE) // Define the color for the title text       
                    // Related to Sub Title Text  
                    .unit("Veículos")
                    .unitColor(Color.WHITE)
                    .valueColor(Color.WHITE)
                    .build();
        parkingCount.setValue(0);
        parkingCount.setBarColor(Color.rgb(39,174,96));  
        parkingCount.setBarBackgroundColor(Color.rgb(24,24,24));  
        parkingCount.setAnimated(true);        
        parkingCount.getStyleClass().add("parking");
        
        VBox vBox = new VBox(l, bar, parkingCount);  
        vBox.setSpacing(3);  
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,0,10));
        
        countVehicles.prefWidth(370); countVehicles.prefHeight(175);
        meanValueVehicles.prefWidth(370); meanValueVehicles.prefHeight(175);
        
        information.add(countVehicles, 0, 0);
        information.add(meanValueVehicles, 0, 1);
        information.add(vBox, 1, 0);
    }
    
    public void update(CountByHoursInDayStatistics observable, Object o) {
        Platform.runLater(() -> {
                        
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            
            if(d != null)
                c1.setTime(d);            
            
            if(d == null || c1.get(Calendar.HOUR_OF_DAY) < c2.get(Calendar.HOUR_OF_DAY)){
                d = new Date();
                //Charts
                XYChart.Series seriesCount = new XYChart.Series();
                XYChart.Series seriesValues = new XYChart.Series();

                countVehicles.getData().clear();

                List<Integer> vehicleByHour = (ArrayList<Integer>) o;
                
                for(int i = 0; i < vehicleByHour.size(); ++i){
                    seriesCount.getData().add(new XYChart.Data(i, vehicleByHour.get(i)));
                }
                
                countVehicles.getData().add(seriesCount);
            }
        });
    }

    public void update(VehicleInProcessor observable, Object o) {
        Platform.runLater(() -> {
            numberVehicles++;
            parkingCount.setValue( numberVehicles );
            
            Entity e = ((IORecord)o).getEntity();
            
            if(vehicles.isEmpty() || e.getIdentifier().compareTo( vehicles.get(0).getIdentifier() ) != 0){
                if(vehicles.size() == 4){
                    vehicles.remove(3);
                    vehicles.add(0, e);
                }else{
                    vehicles.add(0, e);
                }
            }
        });
    }
    
    public void update(VehicleOutProcessor observable, Object o) {
        Platform.runLater(() -> {
            if(numberVehicles > 0) numberVehicles--; 
            parkingCount.setValue( numberVehicles );
        });
    }

    public void initListView(){
        // Last vehicles panel
        vehicles = FXCollections.observableArrayList();
        listViewVehicle = new ListView<>(vehicles);
        listViewVehicle.getStyleClass().add("list-plates");
                
        Label l = new Label("Últimos Veículos");
        l.getStyleClass().add("titles-information");
        Rectangle bar = new Rectangle(200, 2);  
        bar.setArcWidth(6);  
        bar.setArcHeight(6);  
        bar.setFill(Color.rgb(142,68,173));
        
        VBox vBox = new VBox(l, bar, listViewVehicle);  
        vBox.setSpacing(1);  
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,0,10));
        
        listViewVehicle.setCellFactory((ListView<Entity> p) -> {
            ListCell<Entity> cell = new ListCell<Entity>(){
                @Override
                protected void updateItem(Entity t, boolean bln) {
                    super.updateItem(t, bln);
                    if (t != null ) {
                        setText(t.getIdentifier());
                        if(t.getAttrs().get("situation") != null){
                            if(((Boolean)t.getAttrs().get("situation").value)){
                                getStyleClass().remove("ilegal");
                                getStyleClass().remove("legal");
                                getStyleClass().add("legal");
                            }else{
                                getStyleClass().remove("ilegal");
                                getStyleClass().remove("legal");
                                getStyleClass().add("ilegal");
                            }
                        }else{
                            getStyleClass().remove("ilegal");
                            getStyleClass().remove("legal");
                            getStyleClass().add("legal");
                        }
                        
                        
                    } else {
                        setText("");
                    }
                }
            };
            return cell;
        });
        
        information.add(vBox, 1, 1);
    }
}
