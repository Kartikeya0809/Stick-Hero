package project.stickhero.Animation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.stickhero.Backend.ProgressInfo;

import java.io.*;

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
    private void serializeProgress( ProgressInfo progress ) throws IOException {
        ObjectOutputStream out = null ;
        try{
            out = new ObjectOutputStream( new FileOutputStream( "SavedProgress.txt"));
            out.writeObject( progress );
        }finally{
            if ( out != null ){
                out.close();
            }
        }
    }
    private ProgressInfo deserializeProgress() throws IOException, ClassNotFoundException{
        ObjectInputStream in = null ;
        ProgressInfo prevInfo;
        try{
            in = new ObjectInputStream( new FileInputStream( "SavedProgress.txt"));
            prevInfo = ( ProgressInfo ) in.readObject();
        }finally{
            if ( in != null ){
                in.close();
            }
        }
        return prevInfo;
    }
    @FXML
    private void handleleaveButton(){
        try{
            // Serialize and Deserialize
//            System.out.println("serialized: " + MainApplication.getPi().getCurrentScore());
//            System.out.println("serialized cherry: " + MainApplication.getPi().getTotalCherries());

            serializeProgress( MainApplication.getPi() );
            ProgressInfo lastPlayedGame = deserializeProgress();
            if ( lastPlayedGame != null ){
                // serialization successful
                //writing to MainApplication
                MainApplication.setSavedProgress( lastPlayedGame );
//                System.out.println("serialized: " + MainApplication.getSavedProgress().getCurrentScore());

            }
        }catch ( IOException e ){
            System.out.println(e.getMessage());
            System.out.println("\nFailed to Save Progress");
        }catch( ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

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
