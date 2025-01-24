package Controllers;

import database.Player;
import database.PlayerDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class RequestHandler {
    
     public String signInHandle(String name, String password, GameClientHandler gameClientHandler){
        try {
            
            Player player = new Player(name,password);
            
            String result = PlayerDAO.signIn(player);
            
            JSONObject signInJsonResponse = new JSONObject(result);
            String signInResponse = signInJsonResponse.optString("response"); // opt or get?
            
            if (signInResponse.equals("Success")) {
                int playerId = signInJsonResponse.optInt("Player_ID");
                gameClientHandler.mapPlayerIdToClient(playerId);
            }
            
            return result ;
        } catch (SQLException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return "Database Error";
        }  
    }
    
    public String signUpHandle(String name, String email,String password,GameClientHandler gameClientHandler){
        try {
            
            Player player = new Player(name,email,password);
            
            String result = PlayerDAO.signUp(player);
            
            JSONObject signInJsonResponse = new JSONObject(result);
            String signInResponse = signInJsonResponse.optString("response"); // opt or get?
            
            if (signInResponse.equals("Success")) {
                int playerId = signInJsonResponse.optInt("Player_ID");
                gameClientHandler.mapPlayerIdToClient(playerId);
            }
            return result ;
        } catch (SQLException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return "Database Error";
        }  
    }
    
    public String signOutHandle(int playerID){
        try {
           
            String result = PlayerDAO.signOut(playerID);
            return result ;
        } catch (SQLException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return "Database Error";
        }  
    }

    public String getAvailablePlayersHandle(int currentPlayerID) {
        try {
            // Call DAO method to fetch the list of players excluding the current player
            String result = PlayerDAO.getPlayersListExcludingCurrent(currentPlayerID);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return "Database Error";
        }
    }
    
//public String getOnlinePlayersHandle(int currentPlayerID) {
//    // Fetch only online players except the current player
//    List<Player> onlinePlayers = PlayerDAO.getOnlinePlayers(currentPlayerID);
//
//    // Prepare the response JSON
//    JSONObject response = new JSONObject();
//    JSONArray playersArray = new JSONArray();
//
//    for (Player player : onlinePlayers) {
//        JSONObject playerObj = new JSONObject();
//        playerObj.put("NAME", player.getName());
//        playersArray.put(playerObj);
//    }
//
//    response.put("onlinePlayers", playersArray);
//    return response.toString();
//}

    public String userNameHandle(int playerID){
        try {
           
            String result = PlayerDAO.userName(playerID);
            return result ;
        } catch (SQLException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return "Database Error";
        }  
    }
    
    public String handleGameRequest(JSONObject jsonReceived) {
        
        //GameClientHandler.printVector();
        
        JSONObject handlingGameRequestResponse = new JSONObject();
        handlingGameRequestResponse.put("response", "GAME_REQUEST_SUCCESS");

        int requestingPlayerId = jsonReceived.getInt("requestingPlayer_ID");
        int requestedPlayerId = jsonReceived.getInt("requestedPlayer_ID");
        
        System.out.println("Requesting Player ID: " + requestingPlayerId); // log message
        System.out.println("Requested Player ID: " + requestedPlayerId); // log message

        
        String requestingPlayerUsername = PlayerDAO.getPlayerUsernameById(requestingPlayerId);
        GameClientHandler requestedPlayer = GameClientHandler.getGameClient(requestedPlayerId);
        
        //System.out.println("Requested Player Handler: " + requestedPlayer); // log message
        System.out.println("Received JSON in GAME_REQUEST: " + jsonReceived.toString()); // log message

        if (requestedPlayer != null) {
            /* Should I check if the player's socket is connected? Or will all players be already online and connected to a socket? */
           requestedPlayer.sendGameRequest(requestingPlayerId, requestingPlayerUsername);
           return handlingGameRequestResponse.toString();
        } else {
            handlingGameRequestResponse.put("response", "GAME_REQUEST_FAILED");
            return handlingGameRequestResponse.toString();
        }
       
    }

}
