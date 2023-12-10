package project.stickhero.Animation;
import javafx.scene.Parent;
import project.stickhero.Backend.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;


// music credit: Music: https://www.chosic.com/free-music/all/


import java.io.IOException;
import java.util.*;

public class Gameplay {



    @FXML private Button pauseButton;
    @FXML private Line stick;
    @FXML private ImageView sprite;
    @FXML private  AnchorPane screen;
    @FXML private Rectangle pillar1;
    @FXML private Rectangle pillar2;
    @FXML private Label scoreLabel;
    @FXML private ImageView cherry;
    @FXML private Label cherryCounter;

    private final CourseFactory Factory;
    private static boolean revived = false;
    private Stick currentStick;
    private Sprite stickHero;
    private int cherriesInPath = 1;
    private Cherry currentCherry;
    @FXML
    private AnchorPane Background;
    @FXML
    private AnchorPane labels;
    @FXML
    private ProgressInfo gameProgress;

    public Gameplay() {
        // Can be instantiated more than once
        this.Factory = CourseFactory.getInstanceOf();
        this.currentStick = null;
        this.delay = new PauseTransition( Duration.millis(400));
    }
    private PauseTransition delay ;
    private Pillar source, destination, nextPillar;
    private Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/150),e->grow()));
    private AnimationTimer game;
    private ArrayList<TranslateTransition> pillarShifts = new ArrayList<>();
    private boolean reachedNextPillar = false;
    private Random randomGen;

    public Button getPauseButton() {
        return pauseButton;
    }
    public  Random getRandom(){
        if ( randomGen == null ){
            randomGen = new Random();
        }
        return randomGen;
    }
    public static boolean isRevived() {
        return revived;
    }

    public static void setRevived(boolean revived) {
        Gameplay.revived = revived;
    }

    private void sendToOver() {
        FXMLLoader fml = new FXMLLoader();
        fml.setLocation(getClass().getResource("/project/stickhero/GameOver.fxml"));
        try {
            gameProgress.setCurrentScore(stickHero.getScore());
            Parent parent = fml.load();
            Scene sc = new Scene(parent);
            GameEndController controller = fml.getController();
            if (gameProgress.getCurrentScore() >= gameProgress.getHighScore()) {
                gameProgress.setHighScore(gameProgress.getCurrentScore());
            }
            controller.setData(gameProgress);
            Stage stag = new Stage();
            stag = (Stage) (scoreLabel.getScene().getWindow());
            stag.setScene(sc);
            stag.show();

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
//        System.out.println("Gameplay revived: " + Gameplay.isRevived());

        stick.setVisible(false);
        currentStick = (Stick) Factory.getObject(screen, "Stick");
        currentStick.setLine(stick);

        currentCherry = (Cherry) Factory.getObject(screen, "cherry");
        currentCherry.setImage( cherry );

        stickHero = new Sprite( "Hero No.1",sprite );
        source = (Pillar) Factory.getObject(screen,"Pillar");
        destination  = (Pillar) Factory.getObject(screen,"Pillar");
        source.assignRectangle(pillar1);
        destination.assignRectangle(pillar2);

        pillarShifts.add( source.startTransition(600) );
        pillarShifts.add( destination.startTransition(600) );

        gameProgress = MainApplication.getPi();

        if (!revived){
            gameProgress.setCurrentScore(0);
        }
        stickHero.setProgressInfo( gameProgress );
        scoreLabel.setText(String.valueOf(stickHero.getScore()));
        cherryCounter.setText(String.valueOf(stickHero.getNumberOfCherries()));

        game = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        game.start();
    }

    @FXML
    private void handleGrowth()
    {
        if( !currentStick.isExtended() ){
            loop.setCycleCount(Animation.INDEFINITE);
            loop.play();}
    }

    @FXML
    private void stopGrowth()
    {
        if( !currentStick.isExtended() ){
            currentStick.setExtended(true);
            loop.stop();
            animateBridge();
        }

    }


    private void animateBridge()
    {
        if ( currentStick.isExtended() && !currentStick.isFallen() ){
            Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/250),e-> turn()));
            loop.setCycleCount(90);
            loop.play();
            delay.setOnFinished(e-> {

                currentStick.isLengthEnough( source.getBase(), destination.getBase() );
                currentStick.setFallen(true);
                moveSprite();

            });
            delay.play();



        }

    }

    private void turn()
    {
        Rotate r = new Rotate();
        r.pivotXProperty().bind(currentStick.getLine().startXProperty());
        r.pivotYProperty().bind(currentStick.getLine().startYProperty());
        r.setAngle(1);
        currentStick.getLine().getTransforms().add(r);
    }

    private void moveSprite()
    {
        if(currentStick.isFallen()) {
            if(stickHero.isUpsideDown() && stickHero.getImage().getBoundsInParent().intersects(destination.getBase().getBoundsInParent()))
            {
                stickHero.fallIntoAbyss();
            }
            TranslateTransition movement = stickHero.getSpriteTransition(450);
//            System.out.println("stick has fallen");
//            System.out.println("Stick.isShort(): " + currentStick.isShort());


            if( !currentStick.isShort() )
            {
//                System.out.println("Sprite moves by : "+  currentStick.getLength() + stickHero.getImage().getFitWidth());
                movement.setByX(destination.getBase().getBoundsInParent().getMaxX() - source.getBase().getBoundsInParent().getMaxX());
                movement.setOnFinished(e->{
//                    System.out.println("movement COmplete?");
                    reachedNextPillar = true;
                    if (stickHero.getImage().getLayoutX() < 0 ){
                        stickHero.getImage().setLayoutX(1);
                    }
                    currentStick.getLine().setVisible(false);

                });
            }
            else{
                movement.setByX((int)(currentStick.getLength() + stickHero.getImage().getFitWidth()+8));
                stickHero.cannotCollect(false);
                movement.setOnFinished(e->{
                    stickHero.fallIntoAbyss();
//                    System.out.println(12345);
                    if ( currentCherry.isCollected() ){
                        stickHero.lostCherry();
                    }
                    delay.setOnFinished(exiting->Exit());
                    delay.play();

                });
            }
//            System.out.println("movement about to be played");
            movement.play();

        }
    }

    private void grow()
    {
        currentStick.getLine().setVisible(true);
        currentStick.getLine().setEndY(currentStick.getLine().getEndY()-1);
        currentStick.incrementLength();
//        System.out.println(currentStick.getLength());
    }


    @FXML
    private void handlePauseButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/paused.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  pauseButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
            System.out.println("FXML file not found");
        }

    }

    private void Exit(){
        // When pressed anywhere in blank space
        sendToOver();

    }

    private Pillar createPillar( )
    {
        Pillar pillar = null;
        PathObstacles object = Factory.getObject(screen,"pillar");
        if ( object != null && object.getClass() == Pillar.class ){
            pillar = (Pillar) object;
            pillar.setRectangle( getRandom() );

            screen.getChildren().add( pillar.getBase());
//            System.out.println("jfnofnj4394i: " + pillar.getBase().getLayoutX());

        }
        return pillar;
    }

    private void cherryCollected()
    {
        cherryCounter.setText(Integer.toString(stickHero.getNumberOfCherries()+1));
        stickHero.setNumberOfCherries(stickHero.getNumberOfCherries()+1);
        gameProgress.setTotalCherries(gameProgress.getTotalCherries()+1);
//        System.out.println("Cherries: "+ gameProgress.getTotalCherries());
    }

    private void update()

    {
        if(currentStick.isFallen() && stickHero.isUpsideDown() && stickHero.getImage().getBoundsInParent().intersects(destination.getBase().getBoundsInParent()))
        {
            stickHero.fallIntoAbyss();
            Exit();

        }
        if ( stickHero.canCollect() ){
            if(!currentCherry.isCollected() && stickHero.getImage().getBoundsInParent().intersects(currentCherry.getImage().getBoundsInParent())) {
                currentCherry.getImage().setVisible(false);
                cherryCollected();
                currentCherry.setCollected(true);

            }else{
                //cherry will remain visible
                //will need to invoke its transition
            }
        }

        if ( reachedNextPillar && currentStick.isFallen()){
//            source.getBase().setFill(Color.YELLOW);
//            System.out.println("reached next pillar");
            currentStick.setFallen(false);
            reachedNextPillar = false;
            scoreLabel.setText(Integer.toString(stickHero.getScore()+1));

            stickHero.setScore(stickHero.getScore()+1);
            gameProgress.setCurrentScore(stickHero.getScore());

            if ( pillarShifts.size() < 3 ){
                nextPillar = createPillar();
//                nextPillar.getBase().setFill(Color.BLUEVIOLET);
                pillarShifts.add(  nextPillar.startTransition(600) );
            }

//            System.out.println(pillarShifts.size());

            // Transition Objects for source Pillar, destination pillar and incoming pillar
            TranslateTransition first = pillarShifts.remove(0);
            TranslateTransition second = pillarShifts.get(0);
            TranslateTransition third = pillarShifts.get(1);

            // For Stick

            first.setByX( (- second.getNode().getLayoutX() ) );
            second.setByX( -(second.getNode().getBoundsInParent().getMinX() + second.getNode().getBoundsInParent().getWidth()- first.getNode().getBoundsInParent().getMaxX() ));
//            System.out.println("screen width : " +screen.getWidth() );
//            System.out.println("first max x: " +first.getNode().getBoundsInParent().getMaxX() );
//            System.out.println("second max x: " +second.getNode().getBoundsInParent().getMaxX() );

            double shift = getRandom().nextDouble(200-destination.getBase().getWidth());
            if ( shift < destination.getBase().getWidth()){
                shift = destination.getBase().getWidth() + 1;
            }
            third.setByX(-shift);




            stickHero.getSpriteTransition(600).setByX(-(second.getNode().getBoundsInParent().getMinX() + second.getNode().getBoundsInParent().getWidth()- first.getNode().getBoundsInParent().getMaxX() ));
            third.setOnFinished(e->{
                reachedNextPillar = false;
            });


            first.setOnFinished(event -> {
                // When first pillar has shifted back
                // remove the source Rectangle from AnchorPane
                screen.getChildren().remove(first.getNode());
//                System.out.println("after reoval: " +screen.getWidth());


                currentStick.getLine().setVisible(false);
                currentStick.setFallen(false);
                currentStick.setExtended(false);

                source = destination;
                destination = nextPillar;
                nextPillar = null;

                currentStick.newLine( source.getBase(), stickHero.getImage());
//                System.out.println("second played");

            });


            second.setOnFinished( e -> {
//                System.out.println("third played");
            });

            ParallelTransition allPillars = new ParallelTransition();
            allPillars.getChildren().addAll( first,second,third,stickHero.getSpriteTransition(600));
            allPillars.play();
        }

    }

    @FXML
    public void handleTap()
    {
        if(currentStick.isFallen() && !stickHero.isUpsideDown()) {
            stickHero.getImage().setTranslateY(30);
            stickHero.getImage().setScaleY(-1);
            stickHero.setUpsideDown(true);
        }
        else if(currentStick.isFallen()&& stickHero.isUpsideDown())
        {
            stickHero.getImage().setTranslateY(-0.25);
            stickHero.getImage().setScaleY(1);
            stickHero.setUpsideDown(false);
        }
    }



}

/*
package project.stickhero.Animation;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import project.stickhero.Backend.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;


// music credit: Music: https://www.chosic.com/free-music/all/


import java.io.IOException;
import java.util.*;

public class Gameplay {



    @FXML private Button pauseButton;
    @FXML private Line stick;
    @FXML private ImageView sprite;
    @FXML private  AnchorPane screen;
    @FXML private Rectangle pillar1;
    @FXML private Rectangle pillar2;
    @FXML private Label scoreLabel;
    @FXML private ImageView cherry;
    @FXML private Label cherryCounter;

    private final CourseFactory Factory;
    private Stick currentStick;
    private Sprite stickHero;
    private int cherriesInPath = 1;
    private Cherry currentCherry;
    @FXML
    private AnchorPane Background;
    @FXML
    private AnchorPane labels;
    @FXML
    private ProgressInfo pi;      // added
    TranslateTransition movement ;


    public Gameplay() {
        // Can be instantiated more than once
        this.Factory = CourseFactory.getInstanceOf();
        this.currentStick = null;
        this.delay = new PauseTransition( Duration.millis(400));
        //this.pi = ProgressInfo.getInstance();
    }

    private PauseTransition delay ;
    private Pillar source;
    private Pillar destination;
    private Pillar nextPillar ;
    private ArrayList<Stick> Sticks ;
    private int collided = 0;


    private Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/150),e->grow()));
    private AnimationTimer game;
    private ArrayList<TranslateTransition> pillarShifts = new ArrayList<>();

    private boolean reachedNextPillar = false;
    private Random randomGen;

    public Button getPauseButton() {
        return pauseButton;
    }
    public  Random getRandom(){
        if ( randomGen == null ){
            randomGen = new Random();
        }
        return randomGen;

    }


    private void sendToOver() {
        FXMLLoader fml = new FXMLLoader();
        fml.setLocation(getClass().getResource("/project/stickhero/GameOver.fxml"));
        try {
            pi.setCurrentScore(stickHero.getScore());
            Parent p = fml.load();
            Scene sc = new Scene(p);
            GameEndController gec = fml.getController();
            if (pi.getCurrentScore() >= pi.getHighScore()) {
                pi.setHighScore(pi.getCurrentScore());
            }
            gec.setData(pi);
            Stage stag = (Stage) (scoreLabel.getScene().getWindow());
            if(stag!=null){
            stag.setScene(sc);
            stag.show();}
        } catch (IOException e) {
            System.out.println("Exception");
        }

    }






    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        stick.setVisible(false);
        currentStick = (Stick) Factory.getObject(screen, "Stick");
        currentStick.setLine(stick);
        pi = MainApplication.getPi();
        scoreLabel.setText("0");
        cherryCounter.setText("0");
        currentCherry = (Cherry) Factory.getObject(screen, "cherry");
        currentCherry.setImage( cherry );

        stickHero = new Sprite( "Hero No.1",sprite );
        movement = stickHero.getSpriteTransition(450);

        source = (Pillar) Factory.getObject(screen,"Pillar");
        destination  = (Pillar) Factory.getObject(screen,"Pillar");
        source.assignRectangle(pillar1);
        destination.assignRectangle(pillar2);

        pillarShifts.add( source.startTransition(600) );
        pillarShifts.add( destination.startTransition(600) );

        game = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        game.start();
    }

    @FXML
    private void handleGrowth()
    {
        if( !currentStick.isExtended() ){
            loop.setCycleCount(Animation.INDEFINITE);
            loop.play();}
    }

    @FXML
    private void stopGrowth()
    {
        if( !currentStick.isExtended() ){
            currentStick.setExtended(true);
            loop.stop();
            animateBridge();
        }

    }


    private void animateBridge()
    {
        if ( currentStick.isExtended() && !currentStick.isFallen() ){
            Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/250),e-> turn()));
            loop.setCycleCount(90);
            loop.play();
            delay.setOnFinished(e-> {

                currentStick.isLengthEnough( source.getBase(), destination.getBase() );
                currentStick.setFallen(true);
                moveSprite();

            });
            delay.play();



        }

    }

    private void turn()
    {
        Rotate r = new Rotate();
        r.pivotXProperty().bind(currentStick.getLine().startXProperty());
        r.pivotYProperty().bind(currentStick.getLine().startYProperty());
        r.setAngle(1);
        currentStick.getLine().getTransforms().add(r);
    }

    private void moveSprite()
    {
        if(currentStick.isFallen()) {
            if(stickHero.isUpsideDown() && stickHero.getImage().getBoundsInParent().intersects(destination.getBase().getBoundsInParent()))
            {
                stickHero.fallIntoAbyss();
            }
            //TranslateTransition movement = stickHero.getSpriteTransition(450);
            System.out.println("stick has fallen");
            System.out.println("Stick.isShort(): " + currentStick.isShort());


            if( !currentStick.isShort() )
            {
                System.out.println("Sprite moves by : "+  currentStick.getLength() + stickHero.getImage().getFitWidth());
                movement.setByX(destination.getBase().getBoundsInParent().getMaxX() - source.getBase().getBoundsInParent().getMaxX());
                movement.setOnFinished(e->{
                    System.out.println("movement COmplete?");
                    reachedNextPillar = true;
                    if (stickHero.getImage().getLayoutX() < 0 ){
                        stickHero.getImage().setLayoutX(1);
                    }
                    currentStick.getLine().setVisible(false);

                });
            }
            else{
                movement.setByX((int)(currentStick.getLength() + stickHero.getImage().getFitWidth()+8));
                stickHero.cannotCollect(false);
                movement.setOnFinished(e->{
                    stickHero.fallIntoAbyss();
                    if ( currentCherry.isCollected() ){
                        stickHero.lostCherry();
                    }
                    delay.setOnFinished(exiting->Exit());
                    delay.play();

                });
            }
            System.out.println("movement about to be played");
            movement.play();

        }
    }

    private void grow()
    {
        currentStick.getLine().setVisible(true);
        currentStick.getLine().setEndY(currentStick.getLine().getEndY()-1);
        currentStick.incrementLength();
//        System.out.println(currentStick.getLength());
    }


    @FXML
    private void handlePauseButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/paused.fxml"));
            AnchorPane paused = fxmlLoader.load();
            Scene nextScene = new Scene( paused );
            Stage currentStage = ( Stage )  pauseButton.getScene().getWindow();
            currentStage.setScene( nextScene );
            currentStage.show();
        } catch ( Exception e ){
            System.out.println("FXML file not found");
        }

    }

    private void Exit(){
        // When pressed anywhere in blank space
        sendToOver();

    }

    private Pillar createPillar( )
    {
        Pillar pillar = null;
        PathObstacles object = Factory.getObject(screen,"pillar");
        if ( object != null && object.getClass() == Pillar.class ){
            pillar = (Pillar) object;
            pillar.setRectangle( getRandom() );

            screen.getChildren().add( pillar.getBase());
//            System.out.println("jfnofnj4394i: " + pillar.getBase().getLayoutX());

        }
        return pillar;
    }

    private void cherryCollected()
    {
        cherryCounter.setText(Integer.toString(stickHero.getNumberOfCherries()+1));
        stickHero.setNumberOfCherries(stickHero.getNumberOfCherries()+1);
        pi.setTotalCherries(pi.getTotalCherries()+1);
        System.out.println("Cherries: "+pi.getTotalCherries());
    }

    private void update()

    {
        if(currentStick.isFallen() && stickHero.isUpsideDown() && stickHero.getImage().getBoundsInParent().intersects(destination.getBase().getBoundsInParent()))
        {
            //System.out.println("image: "+stickHero.getImage().getBoundsInParent().getMaxX());
            //System.out.println("pillar: " + destination.getBase().getBoundsInParent().getMinX());
            movement.stop();
            stickHero.fallIntoAbyss();
            delay.setOnFinished(exiting->Exit());
            delay.play();

        }
        if ( stickHero.canCollect() ){
            if(!currentCherry.isCollected() && stickHero.getImage().getBoundsInParent().intersects(currentCherry.getImage().getBoundsInParent())) {
                currentCherry.getImage().setVisible(false);
                cherryCollected();
                currentCherry.setCollected(true);

            }else{
                //cherry will remain visible
                //will need to invoke its transition
            }
        }

        if ( reachedNextPillar && currentStick.isFallen() && !stickHero.isUpsideDown()){
//            source.getBase().setFill(Color.YELLOW);
            System.out.println("reached next pillar");
            currentStick.setFallen(false);
            reachedNextPillar = false;
            scoreLabel.setText(Integer.toString(stickHero.getScore()+1));

            stickHero.setScore(stickHero.getScore()+1);
            System.out.println("Hero score: "+stickHero.getScore());
            //pi.setCurrentScore(stickHero.getScore());
            //System.out.println("Score updated to:"+pi.getCurrentScore());

            if ( pillarShifts.size() < 3 ){
                nextPillar = createPillar();
//                nextPillar.getBase().setFill(Color.BLUEVIOLET);
                pillarShifts.add(  nextPillar.startTransition(600) );
            }

            System.out.println(pillarShifts.size());

            // Transition Objects for source Pillar, destination pillar and incoming pillar
            TranslateTransition first = pillarShifts.remove(0);
            TranslateTransition second = pillarShifts.get(0);
            TranslateTransition third = pillarShifts.get(1);

            // For Stick

            first.setByX( (- second.getNode().getLayoutX() ) );
            second.setByX( -(second.getNode().getBoundsInParent().getMinX() + second.getNode().getBoundsInParent().getWidth()- first.getNode().getBoundsInParent().getMaxX() ));
//            System.out.println("screen width : " +screen.getWidth() );
            System.out.println("first max x: " +first.getNode().getBoundsInParent().getMaxX() );
            System.out.println("second max x: " +second.getNode().getBoundsInParent().getMaxX() );

            double shift = getRandom().nextDouble(200-destination.getBase().getWidth());
            if ( shift < destination.getBase().getWidth()){
                shift = destination.getBase().getWidth() + 1;
            }
            third.setByX(-shift);




            stickHero.getSpriteTransition(600).setByX(-(second.getNode().getBoundsInParent().getMinX() + second.getNode().getBoundsInParent().getWidth()- first.getNode().getBoundsInParent().getMaxX() ));
            third.setOnFinished(e->{
                reachedNextPillar = false;
            });


            first.setOnFinished(event -> {
                // When first pillar has shifted back
                // remove the source Rectangle from AnchorPane
                screen.getChildren().remove(first.getNode());
                System.out.println("after reoval: " +screen.getWidth());


                currentStick.getLine().setVisible(false);
                currentStick.setFallen(false);
                currentStick.setExtended(false);

                source = destination;
                destination = nextPillar;
                nextPillar = null;

                currentStick.newLine( source.getBase(), stickHero.getImage());
                System.out.println("second played");

            });


            second.setOnFinished( e -> {
                System.out.println("third played");
            });

            ParallelTransition allPillars = new ParallelTransition();
            allPillars.getChildren().addAll( first,second,third,stickHero.getSpriteTransition(600));
            allPillars.play();
        }

    }

    @FXML
    public void handleTap()
    {
        if(currentStick.isFallen() && !stickHero.isUpsideDown()) {
            stickHero.getImage().setTranslateY(30);
            stickHero.getImage().setScaleY(-1);
            stickHero.setUpsideDown(true);
        }
        else if(currentStick.isFallen()&& stickHero.isUpsideDown())
        {
            stickHero.getImage().setTranslateY(-0.25);
            stickHero.getImage().setScaleY(1);
            stickHero.setUpsideDown(false);
        }
    }



}



*/



