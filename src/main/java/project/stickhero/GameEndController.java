package project.stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameEndController {
    @FXML
    private Button backToHomeButton;
    public Button getBackToHomeButton() {
        return backToHomeButton;
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        // Not Required Right now
    }
    @FXML
    private void handleBackToHomeButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  backToHomeButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
//            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }


}
