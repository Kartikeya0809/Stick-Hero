
package project.stickhero.Backend;



public class ProgressInfo {

    public ProgressInfo() {

    }

    private int currentScore;

    
    private int totalCherries;

    
    private int highScore;

    public int getCurrentScore() {
        return currentScore;
    }

    
    public int getHighScore() {
        return highScore;
    }

    
    public int getTotalCherries() {
        return totalCherries;
    }

    public void setCurrentScore(int current) {
        this.currentScore = current;
    }

    public void setTotalCherries(int totalCherries) {
        this.totalCherries = totalCherries;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
