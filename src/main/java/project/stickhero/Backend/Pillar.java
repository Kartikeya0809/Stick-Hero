
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


    public Pillar( AnchorPane root ) {
        // Constructor
        this.screen = root;
    }

    public void setRectangle( Random random){
        double width = random.nextDouble(30,75);
        this.base = new Rectangle(285,305,width,201);
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
            pillarTransition.setInterpolator(Interpolator.EASE_BOTH);
            pillarTransition.setOnFinished( event -> screen.getChildren().remove( getBase() ));
        }
        return pillarTransition;
    }

}
