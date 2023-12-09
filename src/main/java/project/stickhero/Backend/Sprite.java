
package project.stickhero.Backend;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite {



    private int numberOfCherries;
    private ImageView image ;
    private TranslateTransition spriteNode;
    private int score;
    private String spriteName;
    private boolean isUpsideDown;
    private int numberOfRevives;
    private int cost;

    public Sprite(String name , ImageView spritePhoto) {
        this.spriteName = name;
        this.image = spritePhoto;
    }

    public ImageView getImage() {
        return image;
    }

    public int getScore() {
        return score;
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


    public void extendStick(int howMuch ) {
        // TODO implement here
    }
    public void stopMoving() {
        // TODO implement here
    }
    public void collectCherry() {
        // TODO implement here
    }
    public void revive() {
        // TODO implement here
    }

    public int getCost() {
        return cost;
    }
    public String getName() {
        return spriteName;
    }

    public int getNumberOfRevives() {
        return numberOfRevives;
    }

    public void moveUpright() {
        // TODO implement here
    }

    public void moveUpsideDown() {
        // TODO implement here
    }

    public boolean isUpsideDown() {
        return isUpsideDown;
    }

    public void setUpsideDown(boolean upsideDown) {
        isUpsideDown = upsideDown;
    }

}
