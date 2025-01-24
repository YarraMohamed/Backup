/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import Utils.Navigation;
import Utils.SharedData;

/**
 *
 * @author Maryam Muhammad
 */
public class DifficultyPageController{
     private Stage stage;
    private Scene scene;
    private Parent root;
    private Navigation nav = new Navigation();

    
    
    public void onEasyMode(Event event){
         try {
//             root = FXMLLoader.load(getClass().getResource("/FXML/GameBoard.fxml"));
             FXMLLoader x = new FXMLLoader(getClass().getResource("/FXML/GameBoard.fxml"));
             root = x.load();
             GameBoardController c = x.getController();
             c.setMode("pc_Easy");
             stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
         } catch (IOException ex) {
             Logger.getLogger(DifficultyPageController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void onMediumMode(Event event){
         try {
             FXMLLoader x = new FXMLLoader(getClass().getResource("/FXML/GameBoard.fxml"));
             root = x.load();
             GameBoardController c = x.getController();
             c.setMode("pc_Medium");
             stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
         } catch (IOException ex) {
             Logger.getLogger(DifficultyPageController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void onHardMode(Event event){
         try {
             FXMLLoader x = new FXMLLoader(getClass().getResource("/FXML/GameBoard.fxml"));
             root = x.load();
             GameBoardController c = x.getController();
            c.setMode("pc_Hard");
             stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
         } catch (IOException ex) {
             Logger.getLogger(DifficultyPageController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void Back(ActionEvent event) throws IOException {
        if(SharedData.getInstance().getPlayerID()!=0){
            nav.goToPage("ModePage", event);
        }else{
             nav.goToPage("HomePage", event); 
        }  
    }
 
}
