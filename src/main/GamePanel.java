package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

// imports the constants from Constants class
import static utilities.Constants.PlayerConstants.*;
import static utilities.Constants.Directions.*;

// where all the rendering and most logic takes place
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs; // reads mouse inputs
    private double xDelta=100, yDelta=100; // position of the player
    private BufferedImage img; // stores sprites from resources folder
    private Random random = new Random();
    private BufferedImage[][] animations; // divides image into individual sprites
    private int aniTick, aniIndex, aniSpeed=15; // specifies which image to draw each frame
    private int playerAction = IDLE; // specifies which animation to play
    private int playerDir = -1; // the direction the player is moving
    private boolean moving = false; // specifies if the player is moving or not

    // initializes stuff
    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    // fills the animations array with subImages containing one sprite each
    private void loadAnimations() {
        animations = new BufferedImage[9][6]; // 1st index specifies animation type, 2nd specifies which sprite
        for (int i = 0; i< animations.length; i++) {
            for (int j=0;j<animations[i].length;j++) {
                animations[i][j] = img.getSubimage(j*64,i*40,64,40);
            }
        }
    }

    // sets dimensions of the window
    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    // draws images in the window. called through the repaint() function
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(animations[playerAction][aniIndex], (int) xDelta,(int) yDelta,256,160,null);
    }

    // updates the position of the player
    private void updatePosition() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta-=5;
                    break;
                case UP:
                    yDelta-=5;
                    break;
                case RIGHT:
                    xDelta+=5;
                    break;
                case DOWN:
                    yDelta+=5;
                    break;
            }
        }
    }

    // updates which type of animation to play
    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    // updates which image in the animation to play
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick>=aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmounts(playerAction))
                aniIndex = 0;
        }
    }

    // for changing the direction of the player. used in KeyboardInputs
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    // imports image filled with sprites
    private void importImg() {
        InputStream inputStream = getClass().getResourceAsStream("/player_sprites.png");
        try {
            img = ImageIO.read(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    // sets whether the player is moving or not
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    // updates the game
    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }
}