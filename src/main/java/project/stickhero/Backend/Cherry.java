
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;

public class Cherry implements  PathObstacles{


    private final AnchorPane scene;

    public Cherry(AnchorPane root ) {
        this.scene = root;
    }

    @Override
    public TranslateTransition startTransition() {
        return null;
    }


    private double position;


    private boolean collected;

    public boolean getStatus() {
        // TODO implement here
        return false;
    }

    public double getPosition() {
        // TODO implement here
        return 0.0d;
    }


    public void setPosition(double n) {
        // TODO implement here
    }

}