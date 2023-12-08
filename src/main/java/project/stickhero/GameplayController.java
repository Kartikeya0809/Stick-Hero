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
    import javafx.animation.Timeline;
    import javafx.util.Duration;
    import javafx.animation.*;
    import javafx.scene.shape.Line;

    import java.util.ArrayList;
    import java.util.LinkedList;
    import java.util.Random;

    public class GameplayController {

        @FXML private Button pauseButton;
        @FXML private Line stick;
        @FXML private ImageView sprite;
        @FXML private AnchorPane screen;
        @FXML private Rectangle pill1;
        @FXML private Rectangle pill2;
        private LinkedList<Rectangle> pillars = new LinkedList<>();

        private int stickLength =0;
        private Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/150),e->grow()));
        private AnimationTimer game;
        private ArrayList<TranslateTransition> pillarShifts = new ArrayList<>();

        //Flags
        private boolean stickExtended = false;
        private boolean stickFallen = false;
        private boolean reachedNextPillar = false;
        private double abyssLength;
        private TranslateTransition previousStick, spriteNode ;
        private Random randomGen;

        public Button getPauseButton() {
            return pauseButton;
        }

        public TranslateTransition getPreviousStick() {
            if ( previousStick == null ){
                previousStick = new TranslateTransition();
                previousStick.setDuration(Duration.millis(1000.0));
            }
            return previousStick;
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
            pillars.addFirst(pill1);  pillars.addFirst(pill2);
            pillarShifts.add( startTransition( pill1 )); pillarShifts.add( startTransition( pill2 ));

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
            if( !stickExtended ){
                loop.setCycleCount(Animation.INDEFINITE);
                loop.play();}
        }

        @FXML
        private void stopGrowth()
        {
            if( !stickExtended ){
                stickExtended = true;
                loop.stop();
                animateBridge();
            }

        }


        private void animateBridge()
        {
            if ( stickExtended && !stickFallen ){
                Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/250),e-> turn()));
                loop.setCycleCount(90);
                loop.play();
                PauseTransition delay = new PauseTransition( Duration.millis(1000));
                delay.setOnFinished(e-> {
                    stickFallen = true;
                    moveSprite();

                });
                delay.play();



            }

        }

        private void turn()
        {
            Rotate r = new Rotate();
            r.pivotXProperty().bind(stick.startXProperty());
            r.pivotYProperty().bind(stick.startYProperty());
            r.setAngle(1);
            stick.getTransforms().add(r);
        }

        private void moveSprite()
        {
            if ( stickFallen ){
                System.out.println(stickLength);
                int x = (int) sprite.getLayoutX();
                Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/100),e->move()));
                loop.setCycleCount(stickLength + (int)sprite.getFitWidth());
                loop.setOnFinished(e->reachedNextPillar = true);
                loop.play();
    //            TODO : must change acc to stick length
    //            if ( loop.getStatus() == Animation.Status.STOPPED ){
    //                System.out.println("stopped");
    //                reachedNextPillar = true;
    //            }
            }
        }

        private void move()
        {
            sprite.setLayoutX( sprite.getLayoutX()+1);
        }

        private void grow()
        {
            stick.setVisible(true);
            stick.setEndY(stick.getEndY()-1);
            stickLength +=1;
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

        private Rectangle createPillar( )
        {
    //        double pillarStartAtX = pillars.getFirst().getLayoutX() + pillars.getFirst().getWidth() + abyssLength;
            Rectangle pillar = new Rectangle(285,305,40,200);
            pillars.addFirst(pillar);
            screen.getChildren().add(pillar);

            return pillar;
        }
        private TranslateTransition startTransition ( Rectangle pillar ){
            TranslateTransition pillarTransition = createTransition();
            pillarTransition.setNode(pillar);
            pillarTransition.setOnFinished( event -> screen.getChildren().remove(pillar));
            return pillarTransition;
        }
        private TranslateTransition createTransition(){
            TranslateTransition shift = new TranslateTransition(Duration.millis(1200));
            return shift;

        }


        private void update()

        {
                if ( reachedNextPillar && stickFallen ){
                    System.out.println("reached next pillar");
                    stickFallen = false;
                    reachedNextPillar = false;

                    if ( pillarShifts.size() < 3 ){
                        pillarShifts.add(  startTransition( createPillar() ) );
                    }

                    System.out.println(pillarShifts.size());

                    // Transition Objects for source Pillar, destination pillar and incoming pillar
                    TranslateTransition first = pillarShifts.remove(0);
                    TranslateTransition second = pillarShifts.get(0);
                    TranslateTransition third = pillarShifts.get(1);

                    // For Stick
                    getPreviousStick().setNode(stick);
                    getPreviousStick().setByX(-second.getNode().getLayoutX());

                    first.setByX( -first.getNode().getLayoutX() );
                    second.setByX( -second.getNode().getLayoutX() );
                    double shift = getRandom().nextDouble(275-pillars.get(1).getWidth());
                    third.setByX(-shift);

                    getSpriteNode().setByX(-second.getNode().getLayoutX());
                    third.setOnFinished(e->{reachedNextPillar = false;});


                    first.setOnFinished(event -> {
                        screen.getChildren().remove(first.getNode());
                        pillars.remove(0);
                        second.play();
                        System.out.println("second played");

                    });
                    second.setOnFinished( e -> {
                        third.play();
                        System.out.println("third played");
                        getSpriteNode().play();
                        getPreviousStick().play();

                    });
                    first.play();

//                    PauseTransition delay = new PauseTransition( Duration.millis(100));
//                    delay.play();
//                    delay.setOnFinished(e-> {
//                        first.play();
//                    });

                }


        }


        /*


            t3.setByX(-rand.nextDouble(285-p2.getWidth()));
            sp.setNode(sprite);
            sp.setDuration(Duration.millis(1000.0));
            sp.setByX(-p2.getLayoutX());
            pillars.remove(p1);
            t1.play();
            t2.play();
            s.play();
            t3.play();
            sp.play();
            t3.setOnFinished(e->setFinished(0));*/




    }


