package Controllers;


import org.json.JSONObject;

public class RequestRouter {
   
    public static String routeRequest(String request, GameClientHandler gameClient) {
        
        System.out.println("request in serverHandler "+request);
        JSONObject jsonReceived = new JSONObject(request);
        String requestType = jsonReceived.getString("requestType");

        String username = jsonReceived.optString("username");
        String password = jsonReceived.optString("password");
        String email = jsonReceived.optString("email");
        int playerID = jsonReceived.optInt("Player_ID");
        int requestingPlayer_ID = jsonReceived.optInt("requestingPlayer_ID");
        int requestedPlayer_ID = jsonReceived.optInt("requestedPlayer_ID");
        
        
         RequestHandler requestHandler = new RequestHandler();
      
        switch (requestType) {
            case "SIGN_IN":
                return requestHandler.signInHandle(username, password,gameClient); 
            case "SIGN_UP":
                return requestHandler.signUpHandle(username, email ,password,gameClient) ;
            case "SIGN_OUT":
                return requestHandler.signOutHandle(playerID);
                case "GET_AVAILABLE_PLAYERS":
                return requestHandler.getAvailablePlayersHandle(playerID);
               // case "GET_ONLINE_PLAYERS":
                //return requestHandler.getOnlinePlayersHandle(playerID);
            case "USER_NAME":
                return requestHandler.userNameHandle(playerID);
            case "GAME_REQUEST":
                System.out.println("gone here");
                return requestHandler.handleGameRequest(jsonReceived);
            default:
                return "Error: Invalid request type.";
        }
    }
    
}
