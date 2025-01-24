package Controllers;

import Utils.Navigation;
import Utils.SharedData;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServerIPController {

    @FXML
    private TextField ipTextField;
    @FXML
    private Button enterButton;
    private String ipAddress;
    private Navigation nav = new Navigation();

    @FXML
    private void ShowDialog(ActionEvent event) throws IOException{
        ipAddress = ipTextField.getText();
        boolean result = SharedData.isValidIP(ipAddress);
        if(result){
           enterButton.getScene().getWindow().hide();
        } else {
           nav.ShowAlerts("InvalidMessage");
        }
    }

    public String getIpAddress() {
        return ipAddress;
    } 
    
}
