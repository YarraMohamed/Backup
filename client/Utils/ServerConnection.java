package Utils;

import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerConnection {
    
    private Socket socket;
    private BufferedReader  in;
    private PrintStream out;
    private static ServerConnection instance;
    
    private Navigation nav = new Navigation();
    
    private ServerConnection(){
    }
    
     public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
        return instance;
    }
    
    public boolean checkServerAvailibily(String serverIP){
        try {
            socket = new Socket(serverIP,5005);
            socket.close();
            return true;
        } catch (IOException ex) {
           return false;
        }
    }
    
    public void openConnection() throws IOException{
        String serverIP = SharedData.getInstance().getServerIp();
        socket = new Socket(serverIP,5005);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());
        handlerServer();
    }
    

    private void handlerServer() {
        new Thread(() -> {
        try {
            String message;
            
            while (socket != null && !socket.isClosed() && instance.checkServerAvailibily(SharedData.getInstance().getServerIp())) {
                message = in.readLine();

                if (message == null) {
                    System.out.println("Received null message from server. Continuing to listen...");
                    continue;
                }
                ServerMessagesRouter.routeServerMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error while listening for messages from server: " + e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("Error parsing JSON message from server: " + e.getMessage());
            e.printStackTrace();
        } 
    }).start();
    } 
    
    
    public synchronized void sendRequest(String request) throws IOException {
        
        if (socket == null || socket.isClosed()) {
            return;
        }
        
        out.println(request);
        out.flush();
    }
    
            
   public void closeConnection() {
    try {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null && !socket.isClosed()) socket.close();
    } catch (IOException e) {
        System.err.println("Error closing client resources: " + e.getMessage());
    }
}
   
   
    
}
