package project.stickhero.Animation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.stickhero.Backend.ProgressInfo;

import java.io.IOException;

public class MainApplication extends Application {

    private static ProgressInfo pi;
    @Override
    public void start(Stage stage) {
        try{
            pi = ProgressInfo.getInstance();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/project/stickhero/HomeScreen.fxml"));
            AnchorPane root = ( AnchorPane ) fxmlLoader.load();
            Scene scene = new Scene( root );
            stage.setScene( scene );
            stage.show();
        } catch ( IOException e ){
            System.out.println(e.getMessage());
            System.out.println("FXML file not found");
        }
    }

    public static ProgressInfo getPi() {
        return pi;
    }

    public static void setPi(ProgressInfo pi) {
        MainApplication.pi = pi;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
