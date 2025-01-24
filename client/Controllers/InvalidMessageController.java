package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class InvalidMessageController {

    @FXML
    private Button cancelButton;
    
    public void closeWindow(){
        cancelButton.getScene().getWindow().hide();
    }
}
