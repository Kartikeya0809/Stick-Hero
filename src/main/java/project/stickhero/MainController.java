package project.stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    private Button playButton;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        // Not Required Right now
    }
    @FXML
    private void handlePlayButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InGameScene.fxml"));
            AnchorPane gameplay = fxmlLoader.load();



            Scene nextScene = new Scene( gameplay );
            Stage currentStage = ( Stage )  playButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( IOException e ){
//            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }

}