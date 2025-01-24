
package Utils;

import Controllers.GameRequestNotificationController;
import Controllers.InvalidMessageController;
import Controllers.ServerIPController;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Navigation {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private static Stage primaryStage;
    
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    
     public static void goToPage(String path) throws IOException {
        if (primaryStage == null) {
            throw new IllegalStateException("Primary stage is not set!");
        }

        Parent root = FXMLLoader.load(Navigation.class.getResource("/FXML/" + path + ".fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }
    
    public void ShowAlerts(String path) throws IOException{
        FXMLLoader alertLoader = new FXMLLoader(getClass().getResource("/FXML/"+path+".fxml"));
        Parent alertRoot = alertLoader.load();

        InvalidMessageController errorAlertController = alertLoader.getController();

        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setScene(new Scene(alertRoot));
        alertStage.setTitle("Invalid inputs");
 
  
        alertStage.showAndWait(); 
        
    }
    
    public String ShowServerDialog(ActionEvent event) throws IOException{
        FXMLLoader dialogLoader = new FXMLLoader(getClass().getResource("/FXML/ServerIP.fxml"));
        Parent alertRoot = dialogLoader.load();

        ServerIPController dialogController = dialogLoader.getController();

        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        alertStage.setScene(new Scene(alertRoot));
        alertStage.setTitle("Enter Server IP");
        alertStage.showAndWait();
        
        return dialogController.getIpAddress();
        
    }
    
     public void goToPage(String Path,ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/FXML/"+Path+".fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Tic Tac Toe game");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }  
     
     public void showGameRequestNotification(String requestingPlayerUsername) {
        System.out.println("Showing game request notification for: " + requestingPlayerUsername); // log message
        try {
            FXMLLoader gameRequestLoader = new FXMLLoader(getClass().getResource("/FXML/GameRequestNotification.fxml"));
            System.out.println("FXMLLoader initialized"); // log message
            Parent gameRequestRoot = gameRequestLoader.load();
            System.out.println("FXML loaded successfully"); // log message
            
            GameRequestNotificationController gameRequestController = gameRequestLoader.getController();
            gameRequestController.setRequestingPlayerUsername(requestingPlayerUsername);
            
            Stage gameRequestStage = new Stage();
            gameRequestStage.setScene(new Scene(gameRequestRoot));
            gameRequestStage.setTitle("Game Request");
            gameRequestStage.show();
            
        } catch (IOException e) {
           e.printStackTrace();
           System.out.println("Error while trying to show the game request notification.");
        } 
   
    }
}
