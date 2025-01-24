package Utils;

import org.json.JSONObject;

public class Encapsulator {
    public static String encapsulateSignIn(String username, String password) {
        JSONObject json = new JSONObject();
        json.put("requestType", "SIGN_IN");
        json.put("username", username);
        json.put("password", password);
        return json.toString();
    }

    public static String encapsulateSignUp(String username, String email, String password) {
        JSONObject json = new JSONObject();
        json.put("requestType", "SIGN_UP");
        json.put("username", username);
        json.put("password", password);
        json.put("email", email);
        return json.toString();
    }
    
    public static String encapsulateID(String requestType,int PlayerID) {
        JSONObject json = new JSONObject();
        json.put("requestType", requestType); 
        json.put("Player_ID", PlayerID);
        return json.toString();
    }
    
     public static String encapsulateGameRequest(int requestingPlayerId, int requestedPlayerId) {
        JSONObject gameRequestRequest = new JSONObject();
        gameRequestRequest.put("requestType", "GAME_REQUEST");
        gameRequestRequest.put("requestingPlayer_ID", requestingPlayerId);
        gameRequestRequest.put("requestedPlayer_ID", requestedPlayerId);
       // System.out.println("Sending GAME_REQUEST: " + gameRequestRequest); // log message
        return gameRequestRequest.toString();
    }
    
//public static String encapsulateGetPlayers(int playerID) {
//    JSONObject json = new JSONObject();
//    json.put("requestType", "GET_AVAILABLE_PLAYERS");
//
//    if (playerID > 0) {
//        json.put("playerID", playerID);
//    } else {
//        System.err.println(" Warning: Player ID is invalid.");
//    }
//
//    return json.toString();
//}
    
//    public static String encapsulateGetOnlinePlayers(int playerID) {
//    JSONObject request = new JSONObject();
//    request.put("requestType", "GET_ONLINE_PLAYERS");
//    request.put("playerID", playerID);
//    return request.toString(); //⚠
//}
}

