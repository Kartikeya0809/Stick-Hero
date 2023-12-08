
package project.stickhero.Backend;


import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Pillar implements PathObstacles {

    private Rectangle redRectangle;
    private double midPoint;
    private Rectangle base;
    private AnchorPane screen;


    public Pillar( AnchorPane root ) {
        // Constructor
        this.screen = root;
        this.redRectangle = null;
    }

    public void setRectangle(){
        int width = 40; //TODO: randomize
        this.base = new Rectangle(285,305,width,201);
        this.base.setFill( Color.BLACK );
        this.base.setVisible(true);

    }
    public void setRedBox( double start ){
        start += this.base.getWidth()/4;
        this.redRectangle = new Rectangle(start, 305,this.getMidPoint(), 5);

        this.redRectangle.setFill(Color.ORANGERED);
        this.redRectangle.toFront();
        this.redRectangle.setVisible(false);
    }

    public void assignRectangle( Rectangle first ){
        this.base = first;
    }
    public void assignMidBox( Rectangle redRectangle ){
        this.redRectangle = redRectangle;
    }

    public Rectangle getBase() {
        return base;
    }
    public double getMidPoint(){
        this.midPoint = this.base.getLayoutX() + (this.base.getWidth()/2) ;
        return midPoint;
    }

    public Rectangle getRedBox() {
        return redRectangle;
    }
    public Group getGroup(){
        Group parentNode = new Group( this.base, this.redRectangle );
        return parentNode;
    }

    @Override
    public TranslateTransition startTransition() {
        TranslateTransition pillarTransition = new TranslateTransition(Duration.millis(600));
        pillarTransition.setNode( getBase() );
        pillarTransition.setOnFinished( event -> screen.getChildren().remove( getBase() ));
        return pillarTransition;
    }

}