package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Utils.Navigation;
import Utils.SharedData;

public class ProfilePageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text usernameText;
    @FXML
    private Text scoreText;
    @FXML
    private Text titleProfileText;
    @FXML
    private ListView<String> fileListView;

    private String path;
    private Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usernameText.setText(SharedData.getInstance().getUserName());
        scoreText.setText("Score : " + SharedData.getInstance().getScore());

        path = System.getProperty("user.home") + "/TicTacToeRecordings/";
        savedGames();

        fileListView.setCellFactory(listView -> new ListCell<String>() {

        private Button watchButton = new Button(" Watch ");
        
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                   setText(null);
                   setGraphic(null);
                } else {
                    setText(item);
                    setGraphic(watchButton);
                    watchButton.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ReplayBoard.fxml"));
                            Parent root = loader.load();
                            ReplayBoardController controller = loader.getController();
                            controller.setFileName(item);
                            Stage newStage = new Stage();
                            newStage.setTitle("Replay Game");
                            newStage.setScene(new Scene(root));
                            newStage.initModality(Modality.APPLICATION_MODAL);
                            newStage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ProfilePageController.class.getName()).log(Level.SEVERE, "Failed to load ReplayBoard.fxml", ex);
                        }
                    });
                }
            }
        });
    }

    public void savedGames() {
        File records = new File(path);
        File[] files = records.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileListView.getItems().add(file.getName());
                }
            }
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        nav.goToPage("ModePage", event);
    }
}
