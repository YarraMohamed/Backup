package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class ReplayBoardController {

    @FXML
    private Button sqOneXo;
    @FXML
    private Button sqTwoXo;
    @FXML
    private Button sqThreeXo;
    @FXML
    private Button sqFourXo;
    @FXML
    private Button sqFiveXo;
    @FXML
    private Button sqSixXo;
    @FXML
    private Button sqSevenXo;
    @FXML
    private Button sqEightXo;
    @FXML
    private Button sqNineXo;
    @FXML
    private AnchorPane anchorPane;
    private Line line;

    private String fileName;

    @FXML
    public void initialize() {
        sqOneXo.setText("");
        sqTwoXo.setText("");
        sqThreeXo.setText("");
        sqFourXo.setText("");
        sqFiveXo.setText("");
        sqSixXo.setText("");
        sqSevenXo.setText("");
        sqEightXo.setText("");
        sqNineXo.setText("");
        line=null;
    }

    public void setFileName(String fileName) {
        this.fileName = System.getProperty("user.home") + "/TicTacToeRecordings/" + fileName;
        replay();
    }

    private boolean checkFile(String path) {
        File temp = new File(this.fileName);
        if(temp.exists()) return true;
        else return false;
    }
    
    public void replay() {
          new Thread(() -> {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String buttonID = parts[0];
                    String buttonChar = parts[1];
                    Button button = getButtonById(buttonID);
                    if (button != null) {
                        if(buttonChar.equals("X")){
                            button.setStyle("-fx-text-fill: Black;");
                        }else{
                             button.setStyle("-fx-text-fill: Orange;");
                        }
                        
                        Platform.runLater( () -> {
                            button.setText(buttonChar);
                            checkDiagonals();
                            checkRows();
                            checkColumns();    
                        });
         
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReplayBoardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ReplayBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }).start();
    }

    private Button getButtonById(String buttonId) {
        switch (buttonId) {
            case "sqOneXo": return sqOneXo;
            case "sqTwoXo": return sqTwoXo;
            case "sqThreeXo": return sqThreeXo;
            case "sqFourXo": return sqFourXo;
            case "sqFiveXo": return sqFiveXo;
            case "sqSixXo": return sqSixXo;
            case "sqSevenXo": return sqSevenXo;
            case "sqEightXo": return sqEightXo;
            case "sqNineXo": return sqNineXo;
            default: return null;
        }
    }
    
   public void drawLine(Button startButton, Button endButton){
    
        Bounds bound1 = startButton.localToScene(startButton.getBoundsInLocal());
        Bounds bound2 = endButton.localToScene(endButton.getBoundsInLocal());
      
        double x1 = (bound1.getMaxX() + bound1.getMinX())/2;
        double y1 = (bound1.getMaxY() + bound1.getMinY())/2;
        double x2 = (bound2.getMaxX() + bound2.getMinX())/2;
        double y2 = (bound2.getMaxY() + bound2.getMinY())/2;
        line = new Line (x1,y1,x2,y2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(10);
        anchorPane.getChildren().add(line);
        
    }
       
    private void checkRows(){
        if(sqOneXo.getText().equals(sqTwoXo.getText()) 
            && sqTwoXo.getText().equals(sqThreeXo.getText()) 
            && !sqOneXo.getText().equals("")){
            if(line==null)drawLine(sqOneXo,sqThreeXo);
                  
        }else if(sqFourXo.getText().equals(sqFiveXo.getText()) 
                && sqFiveXo.getText().equals(sqSixXo.getText()) 
                && !sqFourXo.getText().equals("")){
            if(line==null)  drawLine(sqFourXo,sqSixXo);
                  
        } else if(sqSevenXo.getText().equals(sqEightXo.getText()) 
                && sqEightXo.getText().equals(sqNineXo.getText())
                && !sqSevenXo.getText().equals("")){
            if(line==null) drawLine(sqSevenXo,sqNineXo);
        }
    }
          
    private void checkColumns(){
        if(sqOneXo.getText().equals(sqFourXo.getText())
                && sqFourXo.getText().equals(sqSevenXo.getText()) 
                && !sqOneXo.getText().equals("")){
           if(line == null) drawLine(sqOneXo,sqSevenXo);
 
        }else if(sqTwoXo.getText().equals(sqFiveXo.getText()) 
                && sqFiveXo.getText().equals(sqEightXo.getText()) 
                && !sqTwoXo.getText().equals("")){
            if(line == null) drawLine(sqTwoXo,sqEightXo);
            
        }else if(sqThreeXo.getText().equals(sqSixXo.getText()) 
                && sqSixXo.getText().equals(sqNineXo.getText())
                && !sqThreeXo.getText().equals("")){
            if(line == null) drawLine(sqThreeXo,sqNineXo);
        }
    }
    
    private void checkDiagonals(){
    
        if(sqOneXo.getText().equals(sqFiveXo.getText()) 
                && sqFiveXo.getText().equals(sqNineXo.getText())
                && !sqOneXo.getText().equals("")){
            if(line == null) drawLine(sqOneXo,sqNineXo);
 
        }else if(sqThreeXo.getText().equals(sqFiveXo.getText())
                && sqFiveXo.getText().equals(sqSevenXo.getText()) 
                && !sqThreeXo.getText().equals("")){
            if(line == null) drawLine(sqThreeXo,sqSevenXo);
        }
    }
   
}

