
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * 
 */
public class Stick implements PathObstacles{

     private final AnchorPane screen;
     private Line line;
     private boolean stickExtended = false;
     private boolean stickFallen = false;
     private TranslateTransition transition;

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

 public TranslateTransition getTransition() {
  return transition;
 }

 public void setLine(Line line ){
      this.line = line ;
     }

     public Line getLine() {
      return line;
     }

     public boolean isStickExtended() {
      return stickExtended;
     }

     public boolean isStickFallen() {
      return stickFallen;
     }

     public boolean isFallen() {
      return isFallen;
     }

     public void setStickExtended(boolean stickExtended) {
      this.stickExtended = stickExtended;
     }

     public void setStickFallen(boolean stickFallen) {
      this.stickFallen = stickFallen;
     }

     public void incrementLength() {
      this.length++;
     }

     public void setFallen(boolean fallen) {
      isFallen = fallen;
     }

 private double length;

     private boolean isFallen;

     public void extendStick() {
      // TODO implement here
     }

     /**
      * @return
      */
     public void fall() {
      // TODO implement here
     }


     public double getLength() {
      // TODO implement here
      return 0.0d;
     }

     /**
      * @return
      */
     public boolean getStatus() {
      // TODO implement here
      return false;
     }


}