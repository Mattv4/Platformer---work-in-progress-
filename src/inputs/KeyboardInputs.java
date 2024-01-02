package inputs;

import main.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// constants
import static utilities.Constants.Directions.*;

// for reading keyboard inputs
public class KeyboardInputs implements KeyListener {
    // KeyboardInputs must interact with the gamePanel
    private GamePanel gamePanel;

    // initializes gamePanel
    public KeyboardInputs(GamePanel g) {
        this.gamePanel = g;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    // wasd is for movement
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDirection(RIGHT);
                break;
        }
    }

    // stops movement when keys are released
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D:
                // Direction of -1 means no movement
                gamePanel.setDirection(-1);
                gamePanel.setMoving(false);
                break;
        }
    }
}
