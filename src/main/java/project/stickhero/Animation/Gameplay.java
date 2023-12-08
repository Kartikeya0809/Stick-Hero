    package project.stickhero.Animation;
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

    import java.util.*;

    public class Gameplay {


        @FXML private Button pauseButton;
        @FXML private Line stick;
        @FXML private ImageView sprite;
        @FXML private AnchorPane screen;
        @FXML private Rectangle pillar1;
        @FXML private Rectangle pillar2;
        @FXML private Rectangle midPoint;

        private CourseFactory Factory;
        private Stick currentStick;

        public Gameplay() {
            // Can be instantiated more than once
            this.Factory = CourseFactory.getInstanceOf();
            this.currentStick = null;
        }

        private LinkedList<Rectangle> pillars = new LinkedList<>();

        private Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/150),e->grow()));
        private AnimationTimer game;
        private ArrayList<TranslateTransition> pillarShifts = new ArrayList<>();

        private boolean reachedNextPillar = false;
        private TranslateTransition previousStick, spriteNode ;
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



        public TranslateTransition getSpriteNode(){
            if ( spriteNode == null ){
                spriteNode = new TranslateTransition();
                spriteNode.setNode( sprite );
                spriteNode.setDuration(Duration.millis(1000.0));
            }
            return spriteNode;
        }

        @FXML // This method is called by the FXMLLoader when initialization is complete
        public void initialize() {
            stick.setVisible(false);
            currentStick = (Stick) Factory.getObject(screen, "Stick");
            currentStick.setLine(stick);

            pillars.addFirst(pillar1);
            pillars.addFirst(pillar2);

            Pillar firstPillar = (Pillar) Factory.getObject(screen,"Pillar");
            Pillar secondPillar = (Pillar) Factory.getObject(screen,"Pillar");
            firstPillar.assignRectangle(pillar1);
            secondPillar.assignRectangle(pillar2);
            secondPillar.assignMidBox(midPoint);

            pillarShifts.add( firstPillar.startTransition() );
            pillarShifts.add( secondPillar.startTransition() );

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
            if( !currentStick.isStickExtended() ){
                loop.setCycleCount(Animation.INDEFINITE);
                loop.play();}
        }

        @FXML
        private void stopGrowth()
        {
            if( !currentStick.isStickExtended() ){
                currentStick.setStickExtended(true);
                loop.stop();
                animateBridge();
            }

        }


        private void animateBridge()
        {
            if ( currentStick.isStickExtended() && !currentStick.isFallen() ){
                Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/250),e-> turn()));
                loop.setCycleCount(90);
                loop.play();
                PauseTransition delay = new PauseTransition( Duration.millis(1000));
                delay.setOnFinished(e-> {
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
            if (currentStick.isFallen() ){
                System.out.println(currentStick.getLength());
                Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/100),e->move()));
                loop.setCycleCount((int)(currentStick.getLength() + sprite.getFitWidth()));
                loop.setOnFinished(e->{
                    reachedNextPillar = true;
                    if (sprite.getLayoutX() < 0 ){
                        sprite.setLayoutX(1);
                    }
                });
                loop.play();
    //            TODO : must change acc to stick length
            }
        }

        private void move()
        {
            sprite.setLayoutX( sprite.getLayoutX()+1);
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

        @FXML
        private void handleExitButton(){
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

        private Pillar createPillar( )
        {
            Pillar pillar = null;
            PathObstacles object = Factory.getObject(screen,"pillar");
            if ( object != null && object.getClass() == Pillar.class ){
                pillar = (Pillar) object;
                pillar.setRectangle(); pillar.setRedBox(screen.getWidth());
                pillars.addFirst( pillar.getBase() );
                screen.getChildren().add( pillar.getBase());


            }
            return pillar;
        }




        private void update()

        {
                if ( reachedNextPillar && currentStick.isFallen()){
                    Pillar newPillar = null;
                    System.out.println("reached next pillar");
                    currentStick.setFallen(false);
                    reachedNextPillar = false;

                    if ( pillarShifts.size() < 3 ){
                        newPillar = createPillar();
                        pillarShifts.add(  newPillar.startTransition() );
                    }

                    System.out.println(pillarShifts.size());

                    // Transition Objects for source Pillar, destination pillar and incoming pillar
                    TranslateTransition first = pillarShifts.remove(0);
                    TranslateTransition second = pillarShifts.get(0);
                    TranslateTransition third = pillarShifts.get(1);

                    // For Stick
                    currentStick.getTransition().setByX(-second.getNode().getLayoutX());

                    first.setByX( -first.getNode().getLayoutX() );
                    second.setByX( -second.getNode().getLayoutX() );
                    double shift = getRandom().nextDouble(275-pillars.get(0).getWidth());
                    if ( shift < pillars.get(1).getWidth()){
                        shift = pillars.get(1).getWidth() + 1;
                    }
                    third.setByX(-shift);
                    newPillar.setRedBox(third.getNode().getLayoutX());
                    newPillar.getRedBox().setVisible(true);
                    screen.getChildren().add( newPillar.getRedBox());



                    getSpriteNode().setByX(-second.getNode().getLayoutX());
                    third.setOnFinished(e->reachedNextPillar = false);


                    first.setOnFinished(event -> {
                        screen.getChildren().remove(first.getNode());
//                        if ( pillars.get(0) == pillar2 ){
//                            midPoint.setVisible(false);
//                        }
                        pillars.remove(0);
                        second.play();
                        getSpriteNode().play();
                        currentStick.getTransition().play();
                        System.out.println("second played");

                    });
                    second.setOnFinished( e -> {
                        third.play();
                        System.out.println("third played");

                    });
                    first.play();

                }
        }

    }


