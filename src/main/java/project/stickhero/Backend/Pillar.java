
package project.stickhero.Backend;


import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Pillar implements PathObstacles {

    private double midPoint;
    private Rectangle base;
    private AnchorPane screen;
    private TranslateTransition pillarTransition;
    private final double MINIMUM_WIDTH = 15;
    public static final double MAXIMUM_WIDTH = 80;


    public Pillar( AnchorPane root ) {
        // Constructor
        this.screen = root;
    }
    public void setRectangle( Random random, Rectangle src ){
        System.out.println("src.getMin: "+ src.getBoundsInParent().getMinX());
        System.out.println("src.getMax: "+ src.getBoundsInParent().getMaxX());
        double prevFrame = screen.getWidth() - src.getBoundsInParent().getMinX();
        double availableLength = screen.getWidth() - prevFrame;
        double width = random.nextDouble(MINIMUM_WIDTH,MAXIMUM_WIDTH);

        double abyss = random.nextDouble( availableLength - width );
        double setX = 385;
        this.base = new Rectangle();
        screen.getChildren().add( this.base );
        this.base.setFill(Color.BLACK);
        this.base.setX( setX );
        this.base.setWidth( 65);
        this.base.setY( 505-201);
        this.base.setHeight( 201);
        this.base.setVisible(true);


    }



//    private void mark( double x, double y, Color color ){
//        Rectangle mark = new Rectangle( 5, 5 );
//        mark.setFill( color );
//        mark.setLayoutX( x );
//        mark.setLayoutY( y );
//        screen.getChildren().add( mark );
//    }
//
//    double MIN_DISTANCE=30;
//    double START = 50;
//    double END = 281;
//    public void setRectangle( Random random, Rectangle pillar){
//        random.setSeed(System.currentTimeMillis());
//        pillar.setFill(Color.PURPLE);
//        double sceneMinX = pillar.localToScene(pillar.getBoundsInLocal()).getMaxX();
//        double sceneMaxX = pillar.getParent().localToScene(pillar.getParent().getBoundsInLocal()).getMaxX();
//        System.out.println("pillar.getParent().getLayoutBounds().getWidth() = " + pillar.getParent().getBoundsInLocal().getWidth());
//        double thickness = random.nextDouble(START, END);
//        thickness = Math.min(thickness, MAXIMUM_WIDTH);
//        thickness = Math.max(thickness, MINIMUM_WIDTH);
//        sceneMaxX = sceneMaxX - thickness*1.5;
//        double position = random.nextDouble(START + MIN_DISTANCE, END - thickness - START);
//        System.out.println("position = " + position);
//        this.base = new Rectangle(thickness, 201);
//        screen.getChildren().add(this.base);
//        this.base.setLayoutX(screen.sceneToLocal(sceneMinX + position, 0).getX());
//        mark( sceneMinX, pillar.localToScene(pillar.getBoundsInLocal()).getMinY(), Color.GREEN );
//        mark( sceneMaxX, pillar.localToScene(pillar.getBoundsInLocal()).getMinY(), Color.BLUE );
//        mark( position, pillar.localToScene(pillar.getBoundsInLocal()).getMinY(), Color.YELLOW );
//        AnchorPane.setBottomAnchor(this.base, 0D);
//
////        System.out.println("pillar.getParent().localToScene(pillar.getParent().getBoundsInLocal()) = " + pillar.getParent().localToScene(pillar.getParent().getBoundsInLocal()));
////        double availableLength = pillar.getBoundsInParent().getMinX();
////
////        double width = random.nextDouble( availableLength );
////
////        if ( width < MINIMUM_WIDTH ){
////            width = MINIMUM_WIDTH;
////        }if ( width > MAXIMUM_WIDTH ){
////            width = MAXIMUM_WIDTH;
////        }
////        double abyss =  random.nextDouble(availableLength - width );
////        this.base = new Rectangle(50,305,48,201);
////        this.base.setLayoutX( screen.getWidth() - availableLength + abyss );
////        System.out.println(this.base.getLayoutX());
////        System.out.println("available Length: " + availableLength);
////        System.out.println("abyss: "+ abyss);
////        System.out.println("width: "+ width);
////        System.out.println("X : " + (285 - availableLength  + abyss));
////        System.out.println( this.base.getBoundsInLocal().getMinX());
//
//
//        this.base.setFill( Color.BLACK);
//
//        }



    public void assignRectangle( Rectangle first ){
        this.base = first;
    }

    public Rectangle getBase() {
        return base;
    }
    public double getMidPoint(){
        this.midPoint = this.base.getLayoutX() + (this.base.getWidth()/2) ;
        return midPoint;
    }

    @Override
    public TranslateTransition startTransition( double milliseconds ) {
        if ( pillarTransition == null || pillarTransition.getDuration().toMillis() != milliseconds ){
            pillarTransition = new TranslateTransition(Duration.millis( milliseconds ));
            pillarTransition.setNode( getBase() );
            pillarTransition.setOnFinished( event -> screen.getChildren().remove( getBase() ));
        }
        return pillarTransition;
    }


}