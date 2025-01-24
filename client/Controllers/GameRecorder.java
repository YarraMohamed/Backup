package Controllers;

import Utils.SharedData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameRecorder {
    
    private String directoryPath;
    private String fileName;
    
    
    public GameRecorder() {
        this.directoryPath = System.getProperty( "user.home" ) + "/TicTacToeRecordings/";
    }
    
    
    // Create a directory if it does not exist
    private void checkDirectoryExists() {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    
    // Make a file for a game recording
    public void prepareRecordingFile() {        
        checkDirectoryExists();     
        this.fileName = directoryPath + "game_" + System.currentTimeMillis()+".txt";   
    }
    
    
    
    public void saveMovement(String buttonId, String movement) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { 
            writer.write(buttonId + "," + movement); 
            writer.newLine();
        } catch (IOException e) { 
            e.printStackTrace(); 
            System.out.println("Error while making the recording file.");
        } 
    }  
}
