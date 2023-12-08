package project.stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
import java.util.ArrayList;

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
    ArrayList<Rectangle> pillars = new ArrayList<Rectangle>();

    @FXML
    private AnchorPane screen;
    @FXML
    private Rectangle pill1;
    @FXML
    private Rectangle pill2;
    int moved=0;
    int count=-1;
    int point =0 ;
    int len=0;

    AnimationTimer game;
    int time=0;
    int t=0;

    Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/150),e->grow()));

    public Button getPauseButton() {
        return pauseButton;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        st.setVisible(false);
        pillars.add(pill1);
        pillars.add(pill2);
        game = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //createPillars();
                update();
            }
        };
        game.start();
        // Not Required Right now
    }

    @FXML
    private void handleGrowth()
    {
        if(t==0){
        //Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/60),e->grow()));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();}
    }

    @FXML
    private void stopGrowth()
    {
        if(t==0){
        loop.stop();
        rot();
        moved=0;
        count=0;
        point++;
        System.out.println(point);
        moveSprite();
        t++;}
        count=0;
        moved=0;
    }


    @FXML
    private void rot()
    {
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/250),e->turn()));
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
    private void moveSprite()
    {
        int x = (int) sprite.getLayoutX();
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/100),e->move()));
        loop.setCycleCount(len+17);
        loop.play();
        /*AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(sprite.getLayoutX()!=st.getLayoutX()+len){
                sprite.setLayoutX(sprite.getLayoutX()+1);}
            }
        };
        at.start();*/



        /*TranslateTransition tt = new TranslateTransition();
        tt.setNode(sprite);
        tt.setToX(st.getLayoutX());
        tt.play();*/


    }

    @FXML
    private void move()
    {
        sprite.setLayoutX(sprite.getLayoutX()+1);
    }



    @FXML
    private void grow()
    {
        st.setVisible(true);
        st.setEndY(st.getEndY()-1);
        len+=1;
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

    @FXML
    private void createPillars()
    {
        Rectangle vb = new Rectangle(285,305,40,200);
        pillars.add(vb);
        screen.getChildren().add(vb);
    }

    @FXML
    private void movePillars()
    {
        ArrayList<Rectangle> out = new ArrayList<Rectangle>();
        for(int i=point-1;i<pillars.size();i++)
        {
            Rectangle pillar = pillars.get(i);
            pillar.setLayoutX(pillar.getLayoutX()-1);

            if(pillar.getLayoutX()==0)
            {
                out.add(pillar);
            }
            if(i==point && pillar.getLayoutX()+pillar.getWidth()==65)
            {
                moved=1;
            }
        }
        sprite.setLayoutX(sprite.getLayoutX()-1);
        //pillars.removeAll(out);
        screen.getChildren().removeAll(out);
        out.clear();

    }

    @FXML
    private void update()
    {
        /*if(count==0)
        {
            createPillars();
            count++;
            //movePillars();
        }
        if(count>0 && moved!=1)
        {
            movePillars();
        }*/

    }






}
