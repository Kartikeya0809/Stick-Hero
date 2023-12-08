package project.stickhero.Animation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PausedController {
    @FXML
    private Button continueButton;
    @FXML
    private Button leaveButton;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        // Not Required Right now
    }
    @FXML
    private void handleContinueButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/InGameScene.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  continueButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
//            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }
    @FXML
    private void handleleaveButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/HomeScreen.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  leaveButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
//            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }
}
