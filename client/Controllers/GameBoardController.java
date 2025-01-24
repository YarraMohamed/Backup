package Controllers;

import Modes.Easy;
import Modes.Hard;
import Modes.Medium;
import Modes.Mode;
import Utils.Navigation;
import Utils.SharedData;
import com.sun.rowset.internal.Row;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

 

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class GameBoardController implements Initializable {
    
    private Mode pc;
    private String card;
    private Button buttonPressed;
    private int p1Score;
    private int p2Score;
    private int tieScore;
    private boolean isP1Win;
    private boolean isP2Win;
    private boolean winner;
    private Line line;
    private Stage playAgainStage;
    private Stage recordAgainStage;
    private Stage winStage; 

    private Stage stage;
    private Scene scene;
    private Parent root;

    
   
    
    private int movesMade;
    private GameRecorder gameRecorder;
    
       
    @FXML
    private AnchorPane anchorPane;
    
    ///////////////buttons
    
    @FXML
    private Button recordButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Button resetButton;
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
    
    private Navigation nav ;
    
     private Button[][] board;
    
    ///////////////text
    @FXML
    private Text p1Text;
    @FXML
    private Text tieText;
    @FXML
    private Text p2Text;
    
    private String mode ="";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        sqOneXo.setText("");
        sqTwoXo.setText("");
        sqThreeXo.setText("");
        sqFourXo.setText("");
        sqFiveXo.setText("");
        sqSixXo.setText("");
        sqSevenXo.setText("");
        sqEightXo.setText("");
        sqNineXo.setText("");
        
        board = new Button[3][3];
        board[0][0] = sqOneXo;
        board[0][1] = sqTwoXo;
        board[0][2] = sqThreeXo;
        board[1][0] = sqFourXo;
        board[1][1] = sqFiveXo;
        board[1][2] = sqSixXo;
        board[2][0] = sqSevenXo;
        board[2][1] = sqEightXo;
        board[2][2] = sqNineXo;

        card = "X";
        p1Score = 0;
        p2Score = 0;
        tieScore = 0;
        isP1Win = false;
        isP2Win = false;
        winner = false;
        line = null;
        movesMade = 0;
        gameRecorder = null;
        nav = new Navigation();
    } 
        
    public void setMode(String m){
        this.mode = m;
    }
    

    public void resetButtonAction(ActionEvent e){    
        resetGame();
        
    } 
    
    public void resetGame(){
    
        sqOneXo.setText("");
        sqTwoXo.setText("");
        sqThreeXo.setText("");
        sqFourXo.setText("");
        
        sqFiveXo.setText("");
        sqSixXo.setText("");
        sqSevenXo.setText("");
        sqEightXo.setText("");
        sqNineXo.setText("");

        if(line != null){
            anchorPane.getChildren().remove(line);
            line = null;
        }
        card = "X";
    }
    
    public void recordButtonAction(ActionEvent recordGameAction){
                
        if (gameRecorder == null) {
            gameRecorder = new GameRecorder();    
            gameRecorder.prepareRecordingFile();
        }
        recordButton.setDisable(true);
    }
       
   public void gamePlayAction(ActionEvent e) {
    if (!winner) {
        buttonPressed = (Button) e.getSource();

        if (buttonPressed != null && buttonPressed.getText().equals("")) {
            // Save movement if game recorder is initialized
            if (gameRecorder != null) {
                gameRecorder.saveMovement(buttonPressed.getId(), card);
            }

            // Mark the button with the current player's card
            buttonPressed.setText(card);
             movesMade++;
             if(movesMade > 0 && gameRecorder == null){
                recordButton.setDisable(true);
             }
            
            updateButtonStyle(buttonPressed);

            // Switch turns between players
            card = (card.equals("X")) ? "O" : "X";
            checkState();
        } else {
            System.out.println("Error: Button is either null or already marked.");
        }
    }

    // If no winner yet, process AI's move
    if (!winner) {
        Button choosenBtn = null;
        switch (mode) {
            case "pc_Easy":
                pc = new Easy(board, 'O', 'X');
                break;
            case "pc_Medium":
                pc = new Medium(board, 'O', 'X');
                break;
            case "pc_Hard":
                pc = new Hard(board, 'O', 'X');
                break;
        }

        // Get AI move and update the board
        if (pc != null) {
            int[] move = pc.getMove();
            int row = move[0];
            int col = move[1];
            choosenBtn = clikedButton(row, col);

            if (choosenBtn != null) {
                choosenBtn.setText(card);
                updateButtonStyle(choosenBtn);

                // Switch turns between players
                card = (card.equals("X")) ? "O" : "X";
                checkState();
            } else {
                System.out.println("Error: Button at position (" + row + "," + col + ") is not valid.");
            }
        } else {
            System.out.println("Error: PC mode is not set correctly.");
        }
    }
    if(winner){
        movesMade = 0;
        resetButton.setDisable(true);
        leaveButton.setDisable(true);
        }
}

// Method to update button style based on card
private void updateButtonStyle(Button button) {
    button.setStyle((card.equals("X")) ? "-fx-text-fill: Black;" : "-fx-text-fill: #FFA500;");
}
    
    /*
    public void gamePlayAction(ActionEvent e){
        if(!winner){ 
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){

                if (gameRecorder != null) {
                    gameRecorder.saveMovement(buttonPressed.getId(), card);
                }
                
                buttonPressed.setText(card);
                movesMade++;
                
                if(movesMade > 0 && gameRecorder == null){
                
                    recordButton.setDisable(true);
                    
                }
                
                if(card.equals("X")){
                    buttonPressed.setStyle("-fx-text-fill: Black;");
                    
                    card = "O";
                    
                }else{ 
                    buttonPressed.setStyle("-fx-text-fill: #FFA500;");
                    card = "X";
                    
                    
                }
               checkState();     
            }
        }
        if(!winner){
            Button choosenBtn;
            switch(mode){
                case "pc_Easy":
                {
                     pc = new Easy(board,'O','X');
                     int[] move = pc.getMove();
                    int row = move[0];
                    int col = move[1];
                    choosenBtn=clikedButton(row, col);
                    choosenBtn.setText(card);
                    choosenBtn.setStyle((card.equals("X"))?"-fx-text-fill: Black;":"-fx-text-fill: #FFA500;");
                    card=(card.equals("X"))?"O":"X";

                    break;
                }
                    case "pc_Medium":
                    {
                     pc = new Medium(board,'O','X');
                     int[] move = pc.getMove();
                    int row = move[0];
                    int col = move[1];
                    choosenBtn=clikedButton(row, col);
                    choosenBtn.setText(card);
                    choosenBtn.setStyle((card.equals("X"))?"-fx-text-fill: Black;":"-fx-text-fill: #FFA500;");
                    card=(card.equals("X"))?"O":"X";

                    break;
                    }
                    case "pc_Hard":
                    {
                     pc = new Hard(board,'O','X');
                     int[] move = pc.getMove();
                    int row = move[0];
                    int col = move[1];
                    choosenBtn=clikedButton(row, col);
                    choosenBtn.setText(card);
                    choosenBtn.setStyle((card.equals("X"))?"-fx-text-fill: Black;":"-fx-text-fill: #FFA500;");
                    card=(card.equals("X"))?"O":"X";

                    }
                    
            checkState();
        }
        
        if(winner){
        movesMade = 0;
        resetButton.setDisable(true);
        leaveButton.setDisable(true);
        }
    }
    */
    private Button clikedButton(int row,int col){
        if(row == 0){
                     switch(col){
                         case 0:{
                             return sqOneXo;
                             
                         }
                         case 1:{
                             return sqTwoXo;
                             
                         }
                         case 2:{
                             return sqThreeXo;
                         }
                     }
                 }else if(row==1){
                     switch(col){
                         case 0:{
                            return sqFourXo;
                            
                         }
                         case 1:{
                             return sqFiveXo;
                         }
                         case 2:{
                            return sqSixXo;
                         }
                     }
                 }else if(row==2){
                     switch(col){
                         case 0:{
                             return sqSevenXo;
                         }
                         case 1:{
                            return sqEightXo;
                         }
                         case 2:{
                            return sqNineXo;
                         }
                     }
                 }
        return null;
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
    
        if(sqOneXo.getText().equals(sqTwoXo.getText()) && sqTwoXo.getText().equals(sqThreeXo.getText()) && !sqOneXo.getText().equals("")){
           if(line == null){
                drawLine(sqOneXo,sqThreeXo);
                
                if(sqOneXo.getText().equals("X")){
            
                isP1Win = true;
                p1Score += 1;
                p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
            }else{
            
                isP2Win = true;
                p2Score += 1;
                p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
            }
           }
            
            winner = true;
        
        }else if(sqFourXo.getText().equals(sqFiveXo.getText()) && sqFiveXo.getText().equals(sqSixXo.getText()) && !sqFourXo.getText().equals("")){
          
            if(line == null){
                drawLine(sqFourXo,sqSixXo);
                
                if(sqFourXo.getText().equals("X")){
            
                isP1Win = true;
                p1Score += 1;
                p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
             }else{
            
                isP2Win = true;
                p2Score += 1;
                p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
             } 
           }
            
            winner = true;
        }else if(sqSevenXo.getText().equals(sqEightXo.getText()) && sqEightXo.getText().equals(sqNineXo.getText())&& !sqSevenXo.getText().equals("")){
            if(line == null){
                drawLine(sqSevenXo,sqNineXo);
                
                if(sqSevenXo.getText().equals("X")){
            
                isP1Win = true;
                p1Score += 1;
                p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
            }else{
            
                isP2Win = true;
                p2Score += 1;
                p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
            }
           }
            
            winner = true;
        
        }
    
    }
    
    private void checkColumns(){
    
        if(sqOneXo.getText().equals(sqFourXo.getText()) && sqFourXo.getText().equals(sqSevenXo.getText()) && !sqOneXo.getText().equals("")){
           if(line == null){
                drawLine(sqOneXo,sqSevenXo);
            
                if(sqOneXo.getText().equals("X")){
            
                isP1Win = true;
                p1Score += 1;
                p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
            }else{
            
                isP2Win = true;
                p2Score += 1;
                p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
            }
           }
           
            winner = true;
 
        }else if(sqTwoXo.getText().equals(sqFiveXo.getText()) && sqFiveXo.getText().equals(sqEightXo.getText()) && !sqTwoXo.getText().equals("")){
            if(line == null){
            drawLine(sqTwoXo,sqEightXo);
            }
            if(sqTwoXo.getText().equals("X")){
            
                isP1Win = true;
                p1Score += 1;
                p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
            }else{
            
                isP2Win = true;
                p2Score += 1;
                p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
            }
            winner = true;
        
        }else if(sqThreeXo.getText().equals(sqSixXo.getText()) && sqSixXo.getText().equals(sqNineXo.getText())&& !sqThreeXo.getText().equals("")){
            if(line == null){
            drawLine(sqThreeXo,sqNineXo);
            }
            if(sqThreeXo.getText().equals("X")){
            
                isP1Win = true;
                p1Score += 1;
                p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
            }else{
            
                isP2Win = true;
                p2Score += 1;
                p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
            }
            winner = true;
        }
    
    }
    
    private void checkDiagonals(){
    
        if(sqOneXo.getText().equals(sqFiveXo.getText()) && sqFiveXo.getText().equals(sqNineXo.getText()) && !sqOneXo.getText().equals("")){
            if(line == null){
                    drawLine(sqOneXo,sqNineXo);
                    if(sqOneXo.getText().equals("X")){
            
                        isP1Win = true;
                         p1Score += 1;
                        p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
                }else{
            
                        isP2Win = true;
                        p2Score += 1;
                        p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
                }
            
            }
                        
            winner = true;
        }else if(sqThreeXo.getText().equals(sqFiveXo.getText()) && sqFiveXo.getText().equals(sqSevenXo.getText()) && !sqThreeXo.getText().equals("")){
            if(line == null){
                drawLine(sqThreeXo,sqSevenXo);
                
                if(sqThreeXo.getText().equals("X")){
            
                  isP1Win = true;
                    p1Score += 1;
                    p1Text.setText("Player 1 : " + String.valueOf(p1Score));
            
             }else{
            
                  isP2Win = true;
                    p2Score += 1;
                    p2Text.setText("Player 2 : " + String.valueOf(p2Score));
                
                }
            }
            
            winner = true;
        }
    
    }
    
    public boolean isFull(){
        
        if(!sqOneXo.getText().equals("")&&!sqTwoXo.getText().equals("")&&!sqThreeXo.getText().equals("")&&!sqFourXo.getText().equals("")
                &&!sqFiveXo.getText().equals("")&&!sqSixXo.getText().equals("")&&!sqSevenXo.getText().equals("")&&!sqEightXo.getText().equals("")
                &&!sqNineXo.getText().equals("")){
        
            return true;
            
        }else{
        
            return false;
        
        }
    
    }
    
    public void winAnimation() {
    String winMessage = null;
    String winVideo = null;

    winStage = new Stage();
    winStage.setTitle("Game Over");

    // Ensure the necessary objects are not null
    if (isP1Win) {
        winMessage = "Player One Wins!!";
        winVideo = "/Resources/player1Wins.mp4";
    } else if (pc != null && pc.isComputerPlayerWon()) {
        winMessage = "Computer Wins!!";  // Adjusted message
        winVideo = "/Resources/loser.mp4";  // No video for computer win
        playAgainWindow();
        playAgainStage.close();

    } else {
        winMessage = "Player Two Wins!!";
        winVideo = "/Resources/player2Wins.mp4";
    }

    // Make sure winVideo is not null before attempting to play the media
    if (winVideo != null) {
        try {
            Media media = new Media(getClass().getResource(winVideo).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            mediaPlayer.setAutoPlay(true);

            Text winText = new Text(winMessage);
            winText.setFont(Font.font("Chewy", FontWeight.BOLD, 35));
            winText.setFill(Color.RED);

            StackPane root = new StackPane(mediaView, winText);
            StackPane.setAlignment(winText, Pos.TOP_CENTER);
            Scene scene = new Scene(root, 800, 450);
            winStage.setScene(scene);
            winStage.show();

            winStage.setOnCloseRequest(event -> {
                mediaPlayer.stop();
                playAgainWindow();
            });
        } catch (Exception e) {
            // Handle potential errors in loading the video or media file
            System.err.println("Error loading media: " + e.getMessage());
            // You could also show a default message or video in case of an error
        }
    } else {
        // If no win video is available, just show the message
        Text winText = new Text(winMessage);
        winText.setFont(Font.font("Chewy", FontWeight.BOLD, 50));
        winText.setFill(Color.RED);

        StackPane root = new StackPane(winText);
        StackPane.setAlignment(winText, Pos.TOP_CENTER);
        Scene scene = new Scene(root, 800, 450);
        winStage.setScene(scene);
        winStage.show();

        winStage.setOnCloseRequest(event -> {
            playAgainWindow();
        });
    }
}

/*
        
    public void winAnimation(){
       
        String winMessage;
        String winVideo;
        
        winStage = new Stage();
        winStage.setTitle("Game Over");
        
         if(isP1Win){
            winMessage = "Player One Wins!!";
            winVideo = "/Resources/player1Wins.mp4";
        }else if (pc != null && pc.isComputerPlayerWon()) {
        
            winMessage = "Computer Wins!!";  // Adjusted message
            winVideo = null;  // No video for computer win
            playAgainWindow();

        }else {        
             
            winMessage = "Player Two Wins!!"; 
            winVideo = "/Resources/player2Wins.mp4";
        }
        
        Media media = new Media(getClass().getResource(winVideo).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);        
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        
        Text winText = new Text(winMessage);
        winText.setFont(Font.font("Chewy",FontWeight.BOLD,50));
        winText.setFill(Color.RED);
        
        
        StackPane root = new StackPane(mediaView,winText);
        StackPane.setAlignment(winText,Pos.TOP_CENTER);
        Scene scene = new Scene(root,800,450);
        winStage.setScene(scene);
        winStage.show();
        winStage.setOnCloseRequest(event -> {
    
            mediaPlayer.stop();
            playAgainWindow();
        });  
    }
    */
    
    public void recordAgainWindow() {
        
        recordAgainStage = new Stage();
        recordAgainStage.setTitle("Message From Recorder");
        
        recordAgainStage.initStyle(StageStyle.UNDECORATED);

        Text recordAgainText = new Text("Continue Recording?");
        recordAgainText.setFont(Font.font("Chewy", FontWeight.BOLD, 50));
        recordAgainText.setFill(Color.WHITE);

        Button sureButton = new Button("Sure!");
        Button nopeButton = new Button("Nope");
        
        sureButton.setOnAction(e->recordAgainAction(e));
        nopeButton.setOnAction(e->recordAgainAction(e));
        
        sureButton.setFont(Font.font("Chewy", FontWeight.BOLD, 30));
        sureButton.setStyle("-fx-background-color: linear-gradient(to bottom,#1F60C1,#8D9CB3); -fx-background-radius: 30; -fx-text-fill:#ffffff;");

        nopeButton.setFont(Font.font("Chewy", FontWeight.BOLD, 30));
        nopeButton.setStyle("-fx-background-color: linear-gradient(to bottom,#1F60C1,#8D9CB3); -fx-background-radius: 30; -fx-text-fill:#ffffff;");
 
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(recordAgainText);

        HBox buttonBox = new HBox(30);
        buttonBox.getChildren().addAll(sureButton, nopeButton);
        buttonBox.setAlignment(Pos.CENTER); 

        borderPane.setBottom(buttonBox);
        
        borderPane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #86AEE9, #09316D);");

        Scene scene = new Scene(borderPane, 450, 250);

        recordAgainStage.setScene(scene);
        recordAgainStage.show();
    }
    
    
    public void recordAgainAction(ActionEvent e) {
    
        buttonPressed = (Button) e.getSource();
    
            if (buttonPressed.getText().equals("Sure!")) {
            
                gameRecorder.prepareRecordingFile();
                recordAgainStage.close();
            
            } else if (buttonPressed.getText().equals("Nope")) {
            
                gameRecorder = null;
                recordButton.setDisable(true);
                recordAgainStage.close();
            
            }
    }
    
    
    public void playAgainWindow() {
        
        playAgainStage = new Stage();
        playAgainStage.setTitle("Run it Back");
        playAgainStage.initStyle(StageStyle.UNDECORATED);


        Text againText = new Text("Another Game?");
        againText.setFont(Font.font("Chewy", FontWeight.BOLD, 50));
        againText.setFill(Color.WHITE);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        
        yesButton.setOnAction(e -> {
                againAction(e);

                if (gameRecorder != null) {
                    recordAgainWindow();
                }
                            
        });
        
        noButton.setOnAction(e->againAction(e));
        
        yesButton.setFont(Font.font("Chewy", FontWeight.BOLD, 30));
        yesButton.setStyle("-fx-background-color: linear-gradient(to bottom,#1F60C1,#8D9CB3); -fx-background-radius: 30; -fx-text-fill:#ffffff;");

        noButton.setFont(Font.font("Chewy", FontWeight.BOLD, 30));
        noButton.setStyle("-fx-background-color: linear-gradient(to bottom,#1F60C1,#8D9CB3); -fx-background-radius: 30; -fx-text-fill:#ffffff;");
 
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(againText);

        HBox buttonBox = new HBox(30);
        buttonBox.getChildren().addAll(yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER); 

        borderPane.setBottom(buttonBox);
        
        borderPane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #86AEE9, #09316D);");

        Scene scene = new Scene(borderPane, 400, 225);
        
        playAgainStage.setScene(scene);
        playAgainStage.show();
    }
    
    
    public void againAction(ActionEvent e){
    
        buttonPressed = (Button) e.getSource();
        
        if(buttonPressed.getText().equals("Yes")){
                
            resetGame();
            winner = false;
            isP1Win = false;
            isP2Win = false;
            playAgainStage.close();
            if(gameRecorder == null){recordButton.setDisable(false);}   
            resetButton.setDisable(false);
            leaveButton.setDisable(false);
                    
        }else if(buttonPressed.getText().equals("No")){
            leaveButton.setDisable(false);
            playAgainStage.close();

           playAgainStage.close();  
        }
    }
    
    
    public void checkState(){
            
        checkRows();
        checkColumns();
        checkDiagonals();
        if(isP1Win || isP2Win){
           PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
           pause.setOnFinished(e->winAnimation());           
           pause.play();
        }else if(isFull()){
            tieScore += 1;
            tieText.setText("Tie : " + String.valueOf(tieScore));
            resetButton.setDisable(true);
            leaveButton.setDisable(true);
            PauseTransition pauseAgain = new PauseTransition(Duration.seconds(1));
            pauseAgain.setOnFinished(e->playAgainWindow());
            pauseAgain.play();
            
        }
    }

    
    public void exitGame(ActionEvent event) {
    Stage confirmExitStage = new Stage();
    confirmExitStage.initStyle(StageStyle.UNDECORATED);
    confirmExitStage.setTitle("Confirm Exit");

    Text confirmText = new Text("Are you sure you want to exit?");
    confirmText.setFont(Font.font("Chewy", FontWeight.BOLD, 40));
    confirmText.setFill(Color.WHITE);

    Button yesButton = new Button("Yes");
    Button noButton = new Button("No");

    yesButton.setOnAction(e -> {
        confirmExitStage.close();
        try {
            if(SharedData.getInstance().getPlayerID()!=0){
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/ModePage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/DifficultyPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });

    noButton.setOnAction(e -> confirmExitStage.close());

    yesButton.setFont(Font.font("Chewy", FontWeight.BOLD, 30));
    yesButton.setStyle("-fx-background-color: linear-gradient(to bottom,#1F60C1,#8D9CB3); -fx-background-radius: 30; -fx-text-fill:#ffffff;");
    noButton.setFont(Font.font("Chewy", FontWeight.BOLD, 30));
    noButton.setStyle("-fx-background-color: linear-gradient(to bottom,#1F60C1,#8D9CB3); -fx-background-radius: 30; -fx-text-fill:#ffffff;");

    HBox buttonBox = new HBox(30, yesButton, noButton);
    buttonBox.setAlignment(Pos.CENTER);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(confirmText);
    borderPane.setBottom(buttonBox);
    borderPane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #86AEE9, #09316D);");

    Scene scene = new Scene(borderPane, 500, 250);
    confirmExitStage.setScene(scene);
    confirmExitStage.showAndWait(); 
}


}
        

