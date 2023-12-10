package project.stickhero.Animation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import project.stickhero.Backend.ProgressInfo;

public class GameEndController {
    @FXML private Button backToHomeButton;
    @FXML private Button revive;
    @FXML private Label scoreDisplay;
    @FXML private Label highScoreDisp;
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
            Gameplay.setRevived(false);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/HomeScreen.fxml"));
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

    @FXML
    public void setData(ProgressInfo p)
    {
        scoreDisplay.setText(Integer.toString(p.getCurrentScore()));
        highScoreDisp.setText(Integer.toString(p.getHighScore()));
//        Gameplay.setRevived(false);

//        p.setCurrentScore(0);
    }
    private boolean isFirstEvent = true;
    @FXML
    private void buyAnotherChance(){
        try{
            System.out.println("Gameplay revived: " + Gameplay.isRevived());
            if ( isFirstEvent && !Gameplay.isRevived() ) {
                // only one revive available
                Gameplay.setRevived(true);
                MainApplication.getPi().setTotalCherries( MainApplication.getPi().getTotalCherries() - 10 );
                revive.setDisable(true);
                revive.setOpacity(1);
                revive.toBack();
                isFirstEvent = false;

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/Revive.fxml"));
                AnchorPane paused = fxmlLoader.load();
                Scene nextScene = new Scene(paused);
                Stage currentStage = (Stage) revive.getScene().getWindow();
                currentStage.setScene(nextScene);
                currentStage.show();
            }
        } catch ( Exception e ){
            System.out.println("FXML file not found");
        }
    }



}
