
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

    public void setRectangle( Random random, Rectangle pillar ){
        double availableLength = pillar.getBoundsInParent().getMinX();
        double endOfPillar2 = pillar.getLayoutX() + pillar.getWidth();

        double width = random.nextDouble( availableLength );

        if ( width < MINIMUM_WIDTH ){
            width = MINIMUM_WIDTH;
        }if ( width > MAXIMUM_WIDTH ){
            width = MAXIMUM_WIDTH;
        }
        double abyss =  random.nextDouble(availableLength - width );

        this.base = new Rectangle(pillar.getLayoutX() + pillar.getWidth() + 100,304,65,201);
        screen.getChildren().add( this.base );
//        this.base.setWidth(width);
//        double X = endOfPillar2 + abyss;
//        this.base.setX(X);
//        this.base.setY(505-201);
//        this.base.setHeight(201);
////        this.base.setX(pillar.)
        this.base.setFill( Color.BLACK );
        this.base.setVisible(true);
    }


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