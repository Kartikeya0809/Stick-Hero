
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Cherry implements  PathObstacles{


    private final AnchorPane scene;
    private TranslateTransition transition;
    private ImageView image ;
    private boolean collected = false;

    public Cherry( AnchorPane root ) {
        this.scene = root;
        setCollected(false);
    }
    public void setImage( ImageView Cherry ){
        this.image = Cherry;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected( boolean collected ) {
        this.collected = collected;
    }

    public ImageView getImage() {
        return image;
    }

    @Override
    public TranslateTransition startTransition(  double milliseconds ) {
        if ( transition == null || transition.getDuration().toMillis() != milliseconds ){
            transition = new TranslateTransition();
            transition.setNode( image );
            transition.setDuration(Duration.millis(milliseconds));
        }
        if ( !isCollected() ){
//            trans
        }
        return transition;
    }


}