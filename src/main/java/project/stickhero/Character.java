
package project.stickhero;
import java.util.*;

/**
 * 
 */
public abstract class Character implements  Moveable {

    /**
     * Default constructor
     */
    public Character() {
    }

    /**
     * 
     */
    private Stick stick;

    /**
     * 
     */
    private int numberOfCherries;

    /**
     * 
     */
    private boolean isAlive;

    /**
     * 
     */
    private boolean isMoving;

    /**
     * 
     */
    private double currentPosition;

    /**
     * 
     */
    private Score score;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private boolean isUpsideDown;

    /**
     * 
     */
    private int numberOfRevives;

    /**
     * 
     */
    private ProgressInfo progress;

    /**
     * 
     */
    private int cost;

    /**
     * @param howMuch  
     * @return
     */
    public void extendStick(int howMuch ) {
        // TODO implement here
    }

    /**
     * 
     */


    /**
     * 
     */
    public void Operation1() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void stopMoving() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void collectCherry() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void hang() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void perish() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void revive() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void showProgressInfo() {
        // TODO implement here
    }

    /**
     * @return
     */
    public double getCurrentPosition() {
        // TODO implement here
        return 0.0d;
    }

    /**
     * @return
     */
    public Score getScore() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public boolean getUpsideDownStatus() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public int getCost() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public ProgressInfo getProgress() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getName() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public int getNumberOfRevives() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public boolean getAliveStatus() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
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