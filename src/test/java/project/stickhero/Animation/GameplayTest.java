package project.stickhero.Animation;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import project.stickhero.Backend.ProgressInfo;
import project.stickhero.Backend.Sprite;
import project.stickhero.Backend.Stick;

import static org.junit.jupiter.api.Assertions.*;

class GameplayTest {

    @FXML
    private Line stick;
    @FXML private ImageView sprite;
    @Test
    void GrowTest() {

        Gameplay gp = new Gameplay();
        //gp.setCurrentStick();
        AnchorPane r = new AnchorPane();
        Stick st = new Stick(r);
        st.newLine(new Rectangle(),sprite);
        gp.setCurrentStick(st);
        int prev = gp.getCurrentStick().getLength();
        gp.turn();
        gp.grow();
        assertEquals(prev+1,gp.getCurrentStick().getLength());

    }

    @Test
    void GrowTest2() {

        Gameplay gp = new Gameplay();
        //gp.setCurrentStick();
        AnchorPane r = new AnchorPane();
        Stick st = new Stick(r);
        st.newLine(new Rectangle(),sprite);
        gp.setCurrentStick(st);
        int prev = gp.getCurrentStick().getLength();
        gp.turn();
        gp.grow();
        gp.grow();
        assertEquals(prev+2,gp.getCurrentStick().getLength());

    }

    @Test
    void GrowTest3() {

        Gameplay gp = new Gameplay();
        //gp.setCurrentStick();
        AnchorPane r = new AnchorPane();
        Stick st = new Stick(r);
        st.newLine(new Rectangle(),sprite);
        gp.setCurrentStick(st);
        int prev = gp.getCurrentStick().getLength();
        gp.turn();
        gp.grow();
        gp.grow();
        gp.grow();
        assertEquals(prev+3,gp.getCurrentStick().getLength());

    }
}