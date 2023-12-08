
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class Stick implements PathObstacles{

    private final AnchorPane screen;
    private Line line;
    private boolean isExtended = false;
    private boolean isFallen = false;
    private TranslateTransition transition;
    private int length;

    public Stick( AnchorPane root ) {
        this.screen = root;
    }
    @Override
    public TranslateTransition startTransition() {
        if ( transition == null ){
            transition = new TranslateTransition();
            transition.setDuration(Duration.millis(1000.0));
        }
        transition.setNode( this.line );
        return transition;
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