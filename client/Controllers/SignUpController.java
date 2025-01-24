package Controllers;

import Utils.Encapsulator;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utils.Navigation;
import Utils.ServerConnection;
import Utils.SharedData;
import org.json.JSONObject;

public class SignUpController  {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML TextField nameTextField;
    @FXML TextField passTextField;
    @FXML TextField emailTextField;
    private Navigation nav = new Navigation();
    private ServerConnection connection = ServerConnection.getInstance();
    
    
    public void goToModePage(ActionEvent event) throws IOException {
        
        if(!emailTextField.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            nav.ShowAlerts("InvalidMessage");
            return;
        }
        
        String message = Encapsulator.encapsulateSignUp(nameTextField.getText(), 
                                                         emailTextField.getText() ,passTextField.getText());
        boolean result = connection.checkServerAvailibily(SharedData.getInstance().getServerIp());
        
        if(result){
            connection.openConnection();
            connection.sendRequest(message);  
        }else{
            nav.ShowAlerts("ErrorAlert");
        }
    }
}
