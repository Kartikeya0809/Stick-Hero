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
    import javafx.scene.control.Label;

    import java.util.*;

    public class Gameplay {


        @FXML private Button pauseButton;
        @FXML private Line stick;
        @FXML private ImageView sprite;
        @FXML private AnchorPane screen;
        @FXML private Rectangle pillar1;
        @FXML private Rectangle pillar2;
        @FXML private Rectangle midPoint;
        @FXML private Label counter;
        @FXML private ImageView cherry;
        @FXML private Label cherryCounter;

        private CourseFactory Factory;
        private Stick currentStick;
        private Sprite hero;
        private int incomplete = 0;
        private int flag=0;

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
            counter.setText("0");
            cherryCounter.setText("0");
            hero = new Sprite();
            //hero = new Sprite();

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
                PauseTransition delay = new PauseTransition( Duration.millis(400));
                delay.setOnFinished(e-> {
                    if(currentStick.getLength()<pillar2.getLayoutX()-(pillar1.getLayoutX()+pillar1.getWidth())||(currentStick.getLength()>((pillar2.getLayoutX()-(pillar1.getLayoutX()+pillar1.getWidth()))+pillar2.getWidth())))
                    {
                        incomplete=1;
                    }
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
            /*if ( currentStick.isFallen() ){
                System.out.println(currentStick.getLength());
                Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/300),e->move()));
                System.out.printf("incomplete: %d\n",incomplete);
                if(incomplete==0)
                {
                    loop.setCycleCount((int)(sprite.getFitWidth()+pillar2.getX()-pillar1.getX()+(pillar1.getWidth()-pillar2.getWidth())));
                }
                else{
                loop.setCycleCount((int)(currentStick.getLength() + sprite.getFitWidth()-5));}
                loop.setOnFinished(e->{
                    reachedNextPillar = true;
                    if (sprite.getLayoutX() < 0 ){
                        sprite.setLayoutX(1);
                    }
                });
                loop.play();*/
    //            TODO : must change acc to stick length
            if(currentStick.isFallen())
            {
                TranslateTransition movement = new TranslateTransition();
                movement.setNode(sprite);
                movement.setDuration(Duration.millis(450.0));
                if(incomplete==0)
                {
                    movement.setByX(pillar2.getLayoutX()-5);
                    movement.setOnFinished(e->{
                        reachedNextPillar = true;
                        if (sprite.getLayoutX() < 0 ){
                            sprite.setLayoutX(1);
                        }
                    });
                }
                else{
                    movement.setByX((int)(currentStick.getLength() + sprite.getFitWidth()+8));
                    movement.setOnFinished(e->fall());
                }


                movement.play();

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

        private void fall()
        {
            TranslateTransition f = new TranslateTransition();
            f.setNode(sprite);
            f.setDuration(Duration.millis(1000.0));
            f.setByY(+500);
            f.play();
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

        private void incrementCherries()
        {
            cherryCounter.setText(Integer.toString(hero.getNumberOfCherries()+1));
            hero.setNumberOfCherries(hero.getNumberOfCherries()+1);
        }


        private void upsideDown()
        {

        }

        private void update()

        {
                if(sprite.getBoundsInParent().intersects(cherry.getBoundsInParent()) && flag==0)
                {
                    cherry.setVisible(false);
                    incrementCherries();
                    flag++;

                }
                if ( reachedNextPillar && currentStick.isFallen()){
                    Pillar newPillar = null;
                    System.out.println("reached next pillar");
                    currentStick.setFallen(false);
                    reachedNextPillar = false;
                    counter.setText(Integer.toString(hero.getScore()+1));
                    hero.setScore(hero.getScore()+1);

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
                    currentStick.startTransition().setByX(-second.getNode().getLayoutX());

                    first.setByX( -2*first.getNode().getLayoutX() );
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
                    third.setOnFinished(e->{
                        reachedNextPillar = false;
                        relocate();
                    });


                    first.setOnFinished(event -> {
                        screen.getChildren().remove(first.getNode());
//                        if ( pillars.get(0) == pillar2 ){
//                            midPoint.setVisible(false);
//                        }
                        pillars.remove(0);
                        second.play();
                        getSpriteNode().play();
                        currentStick.startTransition().play();
                        System.out.println("second played");

                    });
                    second.setOnFinished( e -> {
                        third.play();
                        System.out.println("third played");

                    });
                    first.play();

                }
        }

        private void relocate()
        {
            //stick.setVisible(false);
            stick.setLayoutX(100);
            stick.setLayoutY(100);
            //stick.setStartY(100);
            //stick.setEndY(19);
            //stick.setStartX(-100);
            //stick.setEndX(-99);
            //stick.setVisible(true);
        }

    }




