package Utils;

import org.json.JSONObject;


public class ServerMessagesRouter {
    

    public static void routeServerMessage(String message) {
        
        try {
            System.out.println("message in routeServer "+message);
            JSONObject serverMessageJson = new JSONObject(message);
            ServerMessagesHandler serverMessagesHandler = new ServerMessagesHandler();
            
            if (serverMessageJson.has("requestType")) {
            String requestType = serverMessageJson.getString("requestType");
          
            switch (requestType) {
                case "GAME_REQUEST":
                    serverMessagesHandler.respondToGameRequest(serverMessageJson);
                    break;
                default:
                    System.out.println("Unhandled requestType: " + requestType);
                    break;
            }
        } else if (serverMessageJson.has("response")) {
            String response = serverMessageJson.getString("response");
            int playerid = serverMessageJson.optInt("Player_ID");
            String userName = serverMessageJson.optString("Name");
            int score = serverMessageJson.optInt("Score"); 
               
            
            switch(response){
                case "LOGGED_IN" :
                    serverMessagesHandler.Auth(playerid);
                    break;
                case "Profile":
                    serverMessagesHandler.viewProfile(userName, score);
                    break;
                case "Failed":
                    serverMessagesHandler.failedMessage();
                    break;    
                case "List_Of_Players" :
                    serverMessagesHandler.avaliablePlayers(serverMessageJson);
                    
            }
               
        } else {
            System.out.println("Invalid message format: " + message);
        }
    
        } catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("Error processing server message: " + message);
        }
        
    
    }
    
}
