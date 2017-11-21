/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import java.util.Objects;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pso.secondphase.iox9.business.notification.NotifierChainSingleton;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.SimpleNotificationType;

/**
 *
 * @author vinihcampos
 */
public class NotificationPanel extends Observer{
    
    //Grid
    private GridPane notification;
    
    //Observable lists
    private ObservableList<Notification> notificationRecords;
    
    //ListViews
    private ListView<Notification> listViewNotifications;
    
    public NotificationPanel(){
        //Initiate grid
        initGrid();
        
        //Initiate list
        initList();
    }
    
    public GridPane getPanel(){
        return this.notification;
    }
    
    public void initGrid(){
        notification = new GridPane();
        notification.setPrefSize(390, 350);
        notification.getStyleClass().add("gridpane-notification");
    }
    
    public void initList(){
        // Notifications panel
        notificationRecords = FXCollections.observableArrayList();
        listViewNotifications = new ListView<>(notificationRecords);
        listViewNotifications.getStyleClass().add("list-notifications");
        
        Label ln = new Label("Últimas notificações");
        ln.getStyleClass().add("titles-information");
        Rectangle barn = new Rectangle(350, 2);  
        barn.setArcWidth(6);  
        barn.setArcHeight(10);  
        barn.setFill(Color.rgb(142,68,173));
        
        VBox vboxn = new VBox(ln, barn, listViewNotifications);
        vboxn.setSpacing(1);  
        vboxn.setAlignment(Pos.CENTER);
        vboxn.setPadding(new Insets(10,10,0,10));
        
        listViewNotifications.setCellFactory((ListView<Notification> n) -> {
            ListCell<Notification> cell = new ListCell<Notification>(){
                @Override
                protected void updateItem(Notification n, boolean bln){
                    super.updateItem(n, bln);
                    if (n != null) {
                        setText(n.getMessage());
                        String type = n.getType().getNotificationType();
                        if (type.compareTo(SimpleNotificationType.INFO.getNotificationType()) == 0) {
                            getStyleClass().remove("notification_achievement");
                            getStyleClass().remove("notification_stolen");
                            getStyleClass().remove("notification_capacity");
                            getStyleClass().add("notification_achievement");
                        } else if (type.compareTo(SimpleNotificationType.CAUTION.getNotificationType()) == 0) {
                            getStyleClass().remove("notification_achievement");
                            getStyleClass().remove("notification_stolen");
                            getStyleClass().remove("notification_capacity");
                            getStyleClass().add("notification_stolen");
                        } else if (type.compareTo(SimpleNotificationType.WARNING.getNotificationType()) == 0) {
                            getStyleClass().remove("notification_achievement");
                            getStyleClass().remove("notification_stolen");
                            getStyleClass().remove("notification_capacity");
                            getStyleClass().add("notification_capacity");
                        }
                        
                    } else {
                        setText("");
                    }
                };
            };
            return cell;
        });
        
        notification.add(vboxn, 1, 1);
    }
    
    public void update(NotifierChainSingleton notifier, Object o) {
        System.out.println(((Notification)o).getMessage());
        
        Platform.runLater(() -> {
            // Add notification (show only some notification)
            //if (!notificationRecords.isEmpty()) {
            //    notificationRecords.clear();
            //}

            //notificationRecords.addAll(s.getNotifications().subList(Math.max(s.getNotifications().size() - 6, 0), s.getNotifications().size()));
            
            if(notificationRecords.size() == 9){
                notificationRecords.remove(8);
                notificationRecords.add(0, ((Notification)o));
            }else{
                notificationRecords.add(0, ((Notification)o));
            }
        });
        
        
    }
}
