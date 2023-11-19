package project.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("HomeScreen.fxml"));
            AnchorPane root = ( AnchorPane ) fxmlLoader.load();
            Scene scene = new Scene( root );
            stage.setScene( scene );
            stage.show();
        } catch ( IOException e ){
            System.out.println("FXML file not found");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}