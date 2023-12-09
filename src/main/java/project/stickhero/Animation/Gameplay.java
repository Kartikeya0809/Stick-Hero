    package project.stickhero.Animation;
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


    import java.util.*;

    public class Gameplay {



        @FXML private Button pauseButton;
        @FXML private Line stick;
        @FXML private ImageView sprite;
        @FXML private AnchorPane screen;
        @FXML private Rectangle pillar1;
        @FXML private Rectangle pillar2;
        @FXML private Rectangle midPoint;
        @FXML private Label scoreLabel;
        @FXML private ImageView cherry;
        @FXML private Label cherryCounter;

        private final CourseFactory Factory;
        private Stick currentStick;
        private ArrayList<Stick> Sticks;
        private Sprite stickHero;
        private int cherriesInPath = 1;
        private Cherry currentCherry;

        public Gameplay() {
            // Can be instantiated more than once
            this.Factory = CourseFactory.getInstanceOf();
            this.currentStick = null;
            this.currentCherry = null;
            this.delay = new PauseTransition( Duration.millis(400));
            this.Sticks = new ArrayList<>();
        }

        private ArrayList<Rectangle> pillars = new ArrayList<>();
        private PauseTransition delay ;

        private Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/150),e->grow()));
        private AnimationTimer game;
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





        @FXML // This method is called by the FXMLLoader when initialization is complete
        public void initialize() {
            stick.setVisible(false);
            currentStick = (Stick) Factory.getObject(screen, "Stick");
            currentStick.setLine(stick);
            Sticks.add(currentStick);

            scoreLabel.setText("0");
            cherryCounter.setText("0");
            currentCherry = (Cherry) Factory.getObject(screen, "cherry");
            currentCherry.setImage( cherry );

            stickHero = new Sprite( "Hero No.1",sprite );

            pillars.add(pillar1);
            pillars.add(pillar2);

            Pillar firstPillar = (Pillar) Factory.getObject(screen,"Pillar");
            Pillar secondPillar = (Pillar) Factory.getObject(screen,"Pillar");
            firstPillar.assignRectangle(pillar1);
            secondPillar.assignRectangle(pillar2);
//            secondPillar.assignMidBox(midPoint);


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
                loop.play();
            }
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

                    currentStick.isLengthEnough( pillars.get(0), pillars.get(1));
                    System.out.println(currentStick.isShort());
                    System.out.println(currentStick.getLength());
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
                System.out.println("in move sprite");
                TranslateTransition movement = stickHero.getSpriteTransition(500);

                if( !currentStick.isShort() )
                {
                    movement.setByX( pillars.get(1).getLayoutX() + 2);
                    movement.setOnFinished(e->{
                        reachedNextPillar = true;
                        if (stickHero.getImage().getLayoutX() < 0 ){
                            stickHero.getImage().setLayoutX(1);
                        }
                    });
                }
                else{
                    movement.setByX((int)(currentStick.getLength() + stickHero.getImage().getFitWidth()+8));
                    movement.setOnFinished(e->{
                        stickHero.fallIntoAbyss();
                        if ( currentCherry.isCollected() ){
                            stickHero.lostCherry();
                        }
                        delay.setOnFinished(exiting->Exit());
                        delay.play();

                    });
                }
                movement.play();

            }
        }

        private void grow()
        {
            currentStick.getLine().setVisible(true);
            currentStick.getLine().setEndY(currentStick.getLine().getEndY()-1);
            currentStick.incrementLength();
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
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/stickhero/GameOver.fxml"));
                AnchorPane gameOver = fxmlLoader.load();
                Scene nextScene = new Scene( gameOver );
                Stage currentStage = ( Stage )  pauseButton.getScene().getWindow();
                currentStage.setScene( nextScene );
                currentStage.show();
            } catch ( Exception e ){
                System.out.println("FXML file not found");
            }

        }

        private void createPillar( )
        {
            Pillar pillar = null;
            PathObstacles object = Factory.getObject(screen,"pillar");
            if ( object != null && object.getClass() == Pillar.class ){
                pillar = (Pillar) object;
                pillar.setRectangle(getRandom(), pillars.get(1) );
                pillars.add( pillar.getBase() );
                pillar.getBase().toFront();

            }
        }

        private void cherryCollected()
        {
            cherryCounter.setText(Integer.toString(stickHero.getNumberOfCherries()+1));
            stickHero.setNumberOfCherries(stickHero.getNumberOfCherries()+1);
        }



        private void update() {
                if(!currentCherry.isCollected() && stickHero.getImage().getBoundsInParent().intersects(currentCherry.getImage().getBoundsInParent())) {
                    currentCherry.getImage().setVisible(false);
                    cherryCollected();
                    currentCherry.setCollected(true);

                }else{
                    //cherry will remain visible
                    //will need to invoke its transition
                }

                if ( reachedNextPillar && currentStick.isFallen()){

                    System.out.println("reached next pillar");
                    currentStick.setFallen(false);
                    reachedNextPillar = false;
                    scoreLabel.setText(Integer.toString(stickHero.getScore()+1));
                    stickHero.setScore(stickHero.getScore()+1);

                    if ( pillars.size() < 3 ){
                        System.out.println("new pillar created");
                        createPillar();
                    }
                    System.out.println( "layoutX: "+ pillars.get(1).getLayoutX());

                    // Transition Objects for source Pillar, destination pillar and incoming pillar
                    TranslateTransition paneTransition =  new TranslateTransition( Duration.millis(1000),screen);
                    double shift = screen.getWidth()  - 80;

                    paneTransition.setByX(-shift);
                    paneTransition.setOnFinished(e->{
                        System.out.println( "layoutX: "+ pillars.get(1).getLayoutX());

                        screen.getChildren().remove(pillars.get(0));
                        pillars.remove(0);
                        resetSticks();
                    });

                    paneTransition.play();




                }
        }

        private void resetSticks(){

            screen.getChildren().remove(Sticks.get(0).getLine());
            Sticks.add(0,currentStick);
            currentStick = null;

            //Sticks.get(0) is on the second pillar
            currentStick = (Stick) Factory.getObject( screen, "stick");
            currentStick.newLine( pillars.get(0), stickHero.getImage());
            Sticks.add(1,currentStick);

        }

    }




