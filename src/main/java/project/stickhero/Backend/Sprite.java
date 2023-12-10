
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite implements  Invertable{


    private int numberOfCherries = 50;
    private ImageView image ;
    private TranslateTransition spriteNode;
    private int score;
    private String spriteName;
    private boolean isUpsideDown;
    private boolean canCollect = true;
    private boolean revived = false;

    public Sprite(String name , ImageView spritePhoto) {
        this.spriteName = name;
        this.image = spritePhoto;
        this.isUpsideDown = false;
    }
    public void setProgressInfo( ProgressInfo info ){
        this.setScore(info.getCurrentScore());
        this.setNumberOfCherries( info.getTotalCherries());
    }

    public boolean canCollect() {
        return canCollect;
    }

    public void cannotCollect(boolean canCollect) {
        this.canCollect = canCollect;
    }

    public ImageView getImage() {
        return image;
    }

    public int getScore() {
        return score;
    }
    public void usedRevive(){
        this.revived = true;
        this.numberOfCherries -= 10;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfCherries() {
        return numberOfCherries;
    }

    public void setNumberOfCherries(int numberOfCherries) {
        this.numberOfCherries = numberOfCherries;
    }

    public TranslateTransition getSpriteTransition( double milliseconds ){
        if ( spriteNode == null || spriteNode.getDuration().toMillis() != milliseconds ){
            System.out.println("Sprite transition set");
            spriteNode = new TranslateTransition();
            spriteNode.setNode( image );
            spriteNode.setDuration(Duration.millis(milliseconds));
        }
        return spriteNode;
    }
    public void fallIntoAbyss() {
        TranslateTransition fallingSprite = getSpriteTransition(1000);;
        fallingSprite.setByY(+500);
        fallingSprite.play();
    }
    public void lostCherry(){
        this.numberOfCherries --;
    }


    public String getName() {
        return spriteName;
    }
    @Override
    public boolean isUpsideDown() {
        return isUpsideDown;
    }
    @Override
    public void setUpsideDown(boolean upsideDown) {
        isUpsideDown = upsideDown;
    }
}
