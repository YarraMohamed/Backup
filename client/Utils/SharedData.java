package Utils;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public class SharedData {
    private static SharedData instance;
    private String serverIp;
    private int playerID = 0;
    private String userName;
    private int score;

    public Map<String, Integer> getAvailablePlayers() {
        return availablePlayers;
    }

    public void setAvailablePlayers(Map<String, Integer> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }
    private Map<String, Integer> availablePlayers = new HashMap<>();


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    private SharedData() {
    }
    
    public static SharedData getInstance() {
    if (instance == null) {
        instance = new SharedData();
    }
    return instance;
}

    public String getServerIp() {
        return serverIp;
    }
    
    public static boolean isValidIP(String ip) {
    return ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
   }
    
    public void setServerIp(String serverIp) {
        if(isValidIP(serverIp)){
            this.serverIp = serverIp;
            System.out.println(serverIp);
        }
    } 
}

   

