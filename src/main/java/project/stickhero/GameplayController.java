package project.stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.shape.Line;
import javafx.event.ActionEvent.*;

public class GameplayController {
    @FXML
    private Button pauseButton;
    @FXML
    private Rectangle stick;
    @FXML
    private Line st;
    @FXML
    private ImageView sprite;
    int fallen =0;

    Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/60),e->grow()));



    public Button getPauseButton() {
        return pauseButton;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        st.setVisible(false);
        // Not Required Right now
    }

    @FXML
    private void handleGrowth()
    {
        //Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/60),e->grow()));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }

    @FXML
    private void stopGrowth()
    {
        loop.stop();
        rot();
        moveSprite();


    }

    @FXML
    private void fall()
    {

        RotateTransition rt = new RotateTransition();
        rt.setNode(stick);
        rt.setDuration(Duration.millis(2000));
        rt.setByAngle(90);
        rt.setAutoReverse(false);
        rt.play();
    }

    @FXML
    private void rot()
    {
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/100),e->turn()));
        loop.setCycleCount(90);
        loop.play();

    }
    @FXML
    private void turn()
    {
        Rotate r = new Rotate();
        r.pivotXProperty().bind(st.startXProperty());
        r.pivotYProperty().bind(st.startYProperty());
        //r.setPivotX(57);
        //r.setPivotY(124);
        r.setAngle(1);
        fallen+=1;
        st.getTransforms().add(r);
        //fallen+=1;
        //movePivot();
    }
    @FXML
    private void movePivot()
    {
        Translate tr  = new Translate();
        tr.setX(57);
        tr.setY(124);
        stick.getTransforms().add(tr);
        //stick.setTranslateX(20); stick.setTranslateY(20);
    }

    @FXML
    private void moveSprite()
    {
        /*Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/60),e->move()));
        loop.setCycleCount((int)st.getEndX());
        loop.play();*/
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long l) {
                sprite.setLayoutX(sprite.getLayoutX()+1);
            }
        };
        at.start();


        /*TranslateTransition tt = new TranslateTransition(new Duration(Animation.INDEFINITE),sprite);
        tt.setToX(st.getEndX());
        if(sprite.getX()==st.getEndX())
        {
            tt.stop();
        }*/

    }

    @FXML
    private void move()
    {
        Translate tr = new Translate();
        sprite.setLayoutX(sprite.getLayoutX()+1);
        //sprite.getTransforms().add(tr);
    }
    

    @FXML
    private void grow()
    {
        st.setVisible(true);
        st.setEndY(st.getEndY()-1);
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
