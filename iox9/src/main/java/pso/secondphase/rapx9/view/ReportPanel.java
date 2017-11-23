/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pso.secondphase.iox9.business.services.ReportService;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.Report;

/**
 *
 * @author vinihcampos
 */
public class ReportPanel extends Application{
    
    private GridPane rootPane;
    private GridPane formPane;
    private GridPane resultPane;
    
    //Input fields
    private TextField plate;
    private DatePicker dateMin;
    private DatePicker dateMax;
    
    private LocalDate ldMin, ldMax;
    
    //Buttons
    private Button search;
    private Button clear;
    
    //Table
    private TableView results;
    
    //Observable lists
    ObservableList<Report> reports;
    
    //Service
    private ReportService reportService;
    
    @Override
    public void start(Stage stage) throws Exception {
        reportService = new ReportService(new JDBCEntityDAO());
        
        initGrid(stage);
        
        initForm(stage);
        
        initButton(stage);
        
        initTable(stage);
        
        //Creating a scene object 
        Scene scene = new Scene(rootPane);
        rootPane.requestFocus();
        
        //Defining stylesheet
        scene.getStylesheets().add(getClass().getResource("/styles/reportpanellayout.css").toExternalForm());
      
        //Setting title to the Stage 
        stage.setTitle("Report Panel"); 

        //Adding scene to the stage 
        stage.setScene(scene); 
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        
        //Displaying the contents of the stage 
        stage.show();
    }
    
    public void initGrid(Stage stage){
        rootPane = new GridPane();
        rootPane.getStyleClass().add("root-pane");         
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        formPane = new GridPane();
        formPane.setPrefWidth(bounds.getWidth());
        formPane.setPrefHeight(bounds.getHeight()*.12);
        formPane.getStyleClass().add("form-pane");
                
        resultPane = new GridPane();        
        resultPane.setPrefWidth(bounds.getWidth());
        resultPane.setPrefHeight(bounds.getHeight()*.88);
        resultPane.getStyleClass().add("result-pane");
        
        rootPane.add(formPane, 0, 0);
        rootPane.add(resultPane, 0, 1);        
    }
    
    public void initForm(Stage stage){
        plate = new TextField();
        plate.getStyleClass().add("input-field");
        dateMin = new DatePicker();
        dateMin.getStyleClass().add("input-field");
        dateMax = new DatePicker();
        dateMax.getStyleClass().add("input-field");
        
        plate.setPromptText("Placa");
        dateMin.setPromptText("Data Inicial");
        dateMax.setPromptText("Data Final");
        
        formPane.add(plate, 0, 0);
        formPane.add(dateMin, 1, 0);
        formPane.add(dateMax, 2, 0);
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        plate.setPrefWidth(bounds.getWidth()*.2);
        plate.setPrefHeight(bounds.getHeight()*.06);
                
        dateMin.setPrefWidth(bounds.getWidth()*.2);
        dateMin.setPrefHeight(bounds.getHeight()*.06);
        
        dateMax.setPrefWidth(bounds.getWidth()*.2);
        dateMax.setPrefHeight(bounds.getHeight()*.06);
        
        dateMin.setOnAction(event -> {
            ldMin = dateMin.getValue();
        });
        
        dateMax.setOnAction(event -> {
            ldMax = dateMax.getValue();
        });
    }
    
    public void initButton(Stage stage){
        search = new Button("Search");
        search.getStyleClass().add("button-s");
        clear = new Button("Clear");
        clear.getStyleClass().add("button-c");
        
        formPane.add(search, 6, 0);
        formPane.add(clear, 7, 0);
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        search.setPrefWidth(bounds.getWidth()*.2);
        search.setPrefHeight(bounds.getHeight()*.06);
        
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Entity> entities;
                String plateText = null;
                Date minDate = null, maxDate = null;
                
                if(!plate.getText().equals("")){
                    plateText = plate.getText();
                }
                
                System.out.println(ldMin + " " + ldMax);
                                
                if(ldMin != null && !ldMin.toString().equals("")){
                    Instant instant = Instant.from(ldMin.atStartOfDay(ZoneId.systemDefault()));
                    minDate = Date.from(instant);
                }
                
                if(ldMax != null && !ldMax.toString().equals("")){
                    Instant instant = Instant.from(ldMax.atStartOfDay(ZoneId.systemDefault()));
                    minDate = Date.from(instant);
                }
                
                entities = reportService.getEntities(plateText, maxDate, minDate);                
                
                System.out.println(entities.size());
                
                reports.clear();
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                for(Entity e : entities){
                    Report r = new Report();
                    if(e.getIdentifier() != null)
                        r.setPlate(e.getIdentifier());
                    if(e.getAttrs() != null){
                        if(e.getAttrs().get("brand") != null)
                            r.setBrand(((String)e.getAttrs().get("brand").value));
                        if(e.getAttrs().get("model") != null)
                            r.setModel(((String)e.getAttrs().get("model").value));
                        if(e.getAttrs().get("color") != null)
                            r.setColor(((String)e.getAttrs().get("color").value));
                        if(e.getAttrs().get("situationCode") != null)
                            r.setSituation(((String)e.getAttrs().get("situationCode").value));
                        if(e.getAttrs().get("frequency") != null){
                            r.setFrequency(((Integer)e.getAttrs().get("frequency").value));
                            System.out.println(r.getFrequency());
                        }if(e.getAttrs().get("price") != null)
                            r.setPrice(formatter.format((BigDecimal)e.getAttrs().get("price").value));
                    }
                    reports.add(r);
                }
                
                results.refresh();
            }
        });
        
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                plate.setText("");
                dateMin.getEditor().clear();
                dateMax.getEditor().clear();
                
                reports.clear();
            }
        });
        clear.setPrefWidth(bounds.getWidth()*.2);
        clear.setPrefHeight(bounds.getHeight()*.06);
        
    }
   
    public void initTable(Stage stage){
        results = new TableView();
        results.getStyleClass().add("table");
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        results.setPrefWidth(bounds.getWidth());
        results.setPrefHeight(bounds.getHeight());
        
        TableColumn plateCol = new TableColumn("Placa");
        plateCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        plateCol.setCellValueFactory(new PropertyValueFactory<Report, String>("plate"));
        
        TableColumn brandCol = new TableColumn("Marca");
        brandCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        brandCol.setCellValueFactory(new PropertyValueFactory<Report, String>("brand"));
        
        TableColumn modelCol = new TableColumn("Modelo");
        modelCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        modelCol.setCellValueFactory(new PropertyValueFactory<Report, String>("model"));
        
        TableColumn colorCol = new TableColumn("Cor");
        colorCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        colorCol.setCellValueFactory(new PropertyValueFactory<Report, String>("color"));
        
        TableColumn priceCol = new TableColumn("Preço");
        priceCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        priceCol.setCellValueFactory(new PropertyValueFactory<Report, Double>("price"));
        
        TableColumn situationCol = new TableColumn("Situação");
        situationCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        situationCol.setCellValueFactory(new PropertyValueFactory<Report, Boolean>("situation"));
        situationCol.setCellFactory((param) -> {
            return new TableCell<Report, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    TableRow<Report> currentRow = getTableRow();
                    currentRow.setPrefHeight((bounds.getHeight() * 0.05));
                    setText(item);
                    
                    if (item == null) {
                        currentRow.setStyle("-fx-background-color:#181818");
                    } else if(item.equals("Sem restrição")){
                        currentRow.setStyle("-fx-background-color:#27ae60");
                    } else{
                        currentRow.setStyle("-fx-background-color:#e57373");                        
                    }
                }
            };
        });
        
        TableColumn frequencyCol = new TableColumn("Frequência");
        frequencyCol.prefWidthProperty().bind(results.widthProperty().divide(7));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<Report, String>("frequency"));
                
        reports = FXCollections.observableArrayList();
        results.setItems(reports);
        results.getColumns().addAll(plateCol, brandCol, modelCol, colorCol, priceCol,situationCol,frequencyCol);
        resultPane.add(results, 0, 0);    
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
