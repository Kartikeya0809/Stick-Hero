package project.stickhero.Animation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
// music credit : Main Theme (Overture) | The Grand Score by Alexander Nakarada | https://creatorchords.com/
//Music promoted by https://www.chosic.com/free-music/all/
//Attribution 4.0 International (CC BY 4.0)
//https://creativecommons.org/licenses/by/4.0/

public class MainController {
    @FXML
    private Button playButton;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        //String path = "C:\\Users\\vijva\\Downloads\\Brendan_Kinsella_-_01_-_Bach_-_Aria_Variata_BWV_989_Variation_no1(chosic.com).mp3";
        String path = "src/main/resources/Music/Theme.mp3";
        Media med = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(med);
        mediaPlayer.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                mediaPlayer.seek(Duration.ZERO);
            }

        });
        mediaPlayer.setAutoPlay(true);
    }
    @FXML
    private void handlePlayButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/InGameScene.fxml"));
            AnchorPane gameplay = fxmlLoader.load();
            Scene nextScene = new Scene( gameplay );
            Stage currentStage = ( Stage )  playButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( IOException e ){
            e.printStackTrace();
            System.out.println("FXML file not found");
        }

    }

}
