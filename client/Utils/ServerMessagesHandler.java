package Utils;

import Utils.Navigation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerMessagesHandler {
    
    private Navigation nav = new Navigation();
    

    public void respondToGameRequest(JSONObject jsonReceived) {
        
        try {
            String requestingPlayerUsername = jsonReceived.getString("requestingPlayerUsername");
        System.out.println("Parsed requestingPlayerUsername: " + requestingPlayerUsername); // log message
        Platform.runLater( () -> {
                System.out.println("Showing game request notification for: " + requestingPlayerUsername);
                nav.showGameRequestNotification(requestingPlayerUsername);
        });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error handling game request.");
        }    
        
        
    }
    
    public void printResponse(String response) {
        System.out.println("Response to client request: " + response);
    }
    
     public void Auth(int player_id) throws IOException{
        SharedData.getInstance().setPlayerID(player_id);
        Platform.runLater(() -> {
            try {
                Navigation.goToPage("ModePage");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error navigating to ModePage.");
            }
        });
    }
     
     public void failedMessage(){
         Platform.runLater(() -> {
            try {
                nav.ShowAlerts("InvalidMessage");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error navigating to ModePage.");
            }
        });
     }
     
     public void viewProfile( String userName,int score){
         SharedData.getInstance().setUserName(userName);
         SharedData.getInstance().setScore(score);
         Platform.runLater(() -> {
            try {
                Navigation.goToPage("ProfilePageFXML");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error navigating to ModePage.");
            }
        });
     }
     
     public void avaliablePlayers(JSONObject jsonReceived ){
         
         Map<String, Integer> playersMap = new HashMap<>();
         if (jsonReceived == null || jsonReceived.isEmpty()) {
            System.out.println("Error: Server returned an empty response.");
            SharedData.getInstance().setAvailablePlayers(playersMap);
        }
        
        JSONArray playersArray = jsonReceived.getJSONArray("players");
        for (int i = 0; i < playersArray.length(); i++) {
                JSONObject playerObject = playersArray.getJSONObject(i);
                String playerName = playerObject.getString("NAME");
                Integer playerId = playerObject.getInt("ID");

                playersMap.put(playerName, playerId);
                
            }
        SharedData.getInstance().setAvailablePlayers(playersMap);
        Platform.runLater(() -> {
            try {
                Navigation.goToPage("AvailablePlayers");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error navigating to ModePage.");
            }
        });
     }
     
       
}
