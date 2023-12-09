
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Stick implements PathObstacles{

    private final AnchorPane screen;
    private Line line;
    private boolean isExtended = false;
    private boolean isFallen = false;
    private boolean isShort = false;
    private TranslateTransition transition;
    private int length;

    public Stick( AnchorPane root ) {
        this.screen = root;
    }
    @Override
    public TranslateTransition startTransition( double milliseconds ) {
        if ( transition == null || transition.getDuration().toMillis() != milliseconds ){
            transition = new TranslateTransition();
            transition.setDuration(Duration.millis(milliseconds ));
        }
        transition.setNode( this.line );
        return transition;
    }
    public void isLengthEnough( Rectangle source, Rectangle destination ){

        Bounds srcBounds = source.localToScene(source.getBoundsInLocal());
        Bounds destBounds = destination.localToScene(destination.getBoundsInLocal());

        if (length < destBounds.getMinX() - srcBounds.getMaxX() ||
                length > destBounds.getMinX() - srcBounds.getMaxX() + destBounds.getWidth()) {
            isShort = true;
        }

    }

    public void newLine( Rectangle tower, ImageView sprite ){

        Line line = new Line(243 ,505-201, 243  , 505-201);
        screen.getChildren().add(line);
        line.toFront();
        line.setVisible(false);
        line.setStrokeWidth(2);
        this.line = line;

    }

    public boolean isShort() {
        return isShort;
    }

    public void setLine(Line line ){
        this.line = line ;
    }

    public Line getLine() {
        return line;
    }

    public boolean isExtended() {
        return isExtended;
    }

    public boolean isFallen() {
        return isFallen;
    }

    public void setExtended(boolean extended) {
        this.isExtended = extended;
    }

    public void incrementLength() {
        this.length+=1;
    }

    public void setFallen(boolean fallen) {
        isFallen = fallen;
    }

    public int getLength() {
        return length;
    }
}
