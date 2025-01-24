/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Utils.Navigation;
import Utils.ServerConnection;
import Utils.SharedData;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Maryam Muhammad
 */
public class HomePageController implements Initializable {
    
 
    @FXML
    private Button playButton;
    
    private Button signInButton;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Navigation nav = new Navigation();
   
    @FXML
    public void clickPlayButton(ActionEvent event) throws IOException {
        try {
            /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/FXML/DifficultyPage.fxml"));
            Parent root= loader.load();
            DifficultyPageController difficultyPageController  =loader.getController();
            Scene scene = new Scene(root);
            stage= (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();*/
            nav.goToPage("DifficultyPage", event);
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    @FXML
      private void clickSignInButton(ActionEvent event) throws IOException {
        try {
           
            String serverIP = nav.ShowServerDialog(event);
        
        if (serverIP != null && !serverIP.isEmpty() && SharedData.isValidIP(serverIP)) {
            boolean result = ServerConnection.getInstance().checkServerAvailibily(serverIP);
            System.out.println(result);
            if(result){
                SharedData.getInstance().setServerIp(serverIP);
                nav.goToPage("SignIn", event);
            } else {
                nav.ShowAlerts("ErrorAlert");
            }
            
        }
    } catch (IOException ex) {
        Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
    }  
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
}
