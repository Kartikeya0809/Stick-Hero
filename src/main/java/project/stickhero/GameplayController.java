package project.stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class GameplayController {
    @FXML
    private Button pauseButton;


    public Button getPauseButton() {
        return pauseButton;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        // Not Required Right now
    }
    @FXML
    private void handlePauseButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paused.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  pauseButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
//            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }

    @FXML
    private void handleExitButton(){
        // When pressed anywhere in blank space
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
            AnchorPane gameOver = fxmlLoader.load();
            Scene nextScene = new Scene( gameOver );
            Stage currentStage = ( Stage )  pauseButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
//            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }



}