package main;

public class Game implements Runnable{
    // fields
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    // sets the frames and updates per second
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    // for limiting and calculating fps
    long previousTime = System.nanoTime();
    int frames = 0;
    long lastCheck = System.currentTimeMillis();

    // constructor starts the game
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    // gameloop
    @Override
    public void run() {
        // for limiting update and frame rate
        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;

        // for calculating updates per second
        int updates = 0;

        // for limiting fps and ups
        long previousTime = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;

        while(true){
            long currentTime = System.nanoTime();

            /*  deltaU and deltaF should remain between 0 and 1.
                They are counters that track time in units of #updates or #frames.
                Frames and updates occur when the deltas become 1 at which point
                the deltas are decreased by one.*/
            deltaU += (currentTime-previousTime)/timePerUpdate;
            deltaF += (currentTime-previousTime)/timePerFrame;
            previousTime = currentTime;

            // updates logic and gameStates
            if(deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // updates frames
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // prints fps and ups every second and resets the counters
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS = "+frames+"\nUPS = "+updates+"\n");
                frames = 0;
                updates = 0;
            }
        }
    }

    // starts the thread
    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // updates the game - does logic and stuff
    public void update() {
        gamePanel.updateGame();
    }
}
