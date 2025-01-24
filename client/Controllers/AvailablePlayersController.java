package Controllers;


import Utils.Encapsulator;
import Utils.Navigation;
import Utils.ServerConnection;
import Utils.SharedData;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Ziad
 */

public class AvailablePlayersController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private VBox  userContainer;
    
    private ServerConnection connection = ServerConnection.getInstance();
    
    private Navigation nav;
    
    int currentPlayerID = SharedData.getInstance().getPlayerID();

    
    
    @FXML
    public void onNavBack(Event event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/ModePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AvailablePlayersController.class.getName()).log(Level.SEVERE, "Failed to load SignIn.fxml", ex);
    }
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nav = new Navigation();
        
        VBox.setVgrow(userContainer, Priority.ALWAYS);
        label.setText("Let’s Play");
        Map<String, Integer> availablePlayers = SharedData.getInstance().getAvailablePlayers();
        if (availablePlayers.isEmpty()) {
            System.out.println("No online players available.");
        } else {
            for (Map.Entry<String, Integer> player : availablePlayers.entrySet()) { 
                addCompoent(player);
            }
        }

    }
    
    
    private void addCompoent(Map.Entry<String, Integer> playerEntry){
        
        // Extract player ID and username
         int playerId = playerEntry.getValue();
         String username = playerEntry.getKey();
         
         // Create a box
        HBox box = new HBox();
        
        // Set max width and preferred height
        box.setMaxWidth(Double.MAX_VALUE); // Fills the available width
        box.setPrefHeight(40);

        box.setPadding(new Insets(10));
        box.setSpacing(10);

        BackgroundFill backgroundFill = new BackgroundFill(
            Color.web("#A6A6A6", 0.7), 
            new CornerRadii(15), 
            Insets.EMPTY
        );
        box.setBackground(new Background(backgroundFill));
        Label label = new Label(username);
        label.setStyle("-fx-text-fill: black;");
        box.getChildren().add(label);
        
        box.setOnMouseClicked(avaliablePlayerClicked -> {
           try {
               
               boolean result=connection.checkServerAvailibily(SharedData.getInstance().getServerIp());
               
               if(result){
                   String gameRequest = Encapsulator.encapsulateGameRequest(currentPlayerID, playerId);
                   ServerConnection.getInstance().openConnection();
                   ServerConnection.getInstance().sendRequest(gameRequest);
               }else{
                   System.out.println("error");
               }   
            } 
            catch(IOException e) {
                e.printStackTrace();
                System.out.println("Failed to send game request.");
            }
        });
                
        userContainer.getChildren().add(box);
        
    }
    

}
