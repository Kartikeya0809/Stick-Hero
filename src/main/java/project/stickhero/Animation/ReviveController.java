package project.stickhero.Animation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReviveController {
    @FXML
    private Button buyRevive;
    @FXML private Button HomeButton;
    public Button getHomeButton() {
        return HomeButton;
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        // Not Required Right now
    }

    @FXML
    private void handleHomeButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/HomeScreen.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  HomeButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
            System.out.println("FXML file not found");
        }

    }
    @FXML
    private void buyAnotherChance(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/InGameScene.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  buyRevive.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
            System.out.println("FXML file not found");
        }
    }
}
