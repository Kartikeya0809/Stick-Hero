
package project.stickhero.Backend;

public abstract class Sprite {

    public Sprite() {
    }

    private int numberOfCherries;

    private boolean isAlive;


    private boolean isMoving;

    private double currentPosition;


//    private Score score;

    private String name;


    private boolean isUpsideDown;


    private int numberOfRevives;

//    private ProgressInfo progress;


    private int cost;


    public void extendStick(int howMuch ) {
        // TODO implement here
    }

    public void stopMoving() {
        // TODO implement here
    }

    public void collectCherry() {
        // TODO implement here
    }

    public void hang() {
        // TODO implement here
    }


    public void perish() {
        // TODO implement here
    }


    public void revive() {
        // TODO implement here
    }


    public void showProgressInfo() {
        // TODO implement here
    }

    public double getCurrentPosition() {
        // TODO implement here
        return 0.0d;
    }


//    public Score getScore() {
//        // TODO implement here
//        return null;
//    }

    public boolean getUpsideDownStatus() {
        // TODO implement here
        return false;
    }


    public int getCost() {
        // TODO implement here
        return 0;
    }

//    public ProgressInfo getProgress() {
//        // TODO implement here
//        return null;
//    }


    public String getName() {
        // TODO implement here
        return "";
    }

    public int getNumberOfRevives() {
        // TODO implement here
        return 0;
    }

    public boolean getAliveStatus() {
        // TODO implement here
        return false;
    }

    public boolean getMovingStatus() {
        // TODO implement here
        return false;
    }

    public void moveUpright() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void moveUpsideDown() {
        // TODO implement here
    }

}