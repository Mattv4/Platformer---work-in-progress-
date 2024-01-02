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

import static utilities.Constants.PlayerConstants.*;
import static utilities.Constants.Directions.*;


public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private double xDelta=100, yDelta=100;
    private BufferedImage img;
    private Random random = new Random();
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed=15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;


    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        for (int i = 0; i< animations.length; i++) {
            for (int j=0;j<animations[i].length;j++) {
                animations[i][j] = img.getSubimage(j*64,i*40,64,40);
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(animations[playerAction][aniIndex], (int) xDelta,(int) yDelta,256,160,null);
    }

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

    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick>=aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmounts(playerAction))
                aniIndex = 0;
        }
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }
}