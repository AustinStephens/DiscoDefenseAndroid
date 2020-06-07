package isys221.discodefense;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;

import isys221.discodefense.Scenes.*;
import isys221.discodefense.Tiles.*;
import isys221.discodefense.Tiles.Towers.*;


public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    public static HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();
    private static Scene scene;
    public static MediaPlayer[] miniGameSongs;
    public static MediaPlayer[] miniGameSongsDummy;

    public static MediaPlayer[] backgroundMusic;
    public static MediaPlayer currentMusic;
    private static int currentMusicIndex = 0;
    public static boolean musicPaused = false;
    //public static BeatDetect beat;

    public static float scaleX;
    public static float scaleY;

    public static int screenWidth;
    public static int screenHeight;

    public static float tileHeight;
    public static float tileWidth;

    public static final int worldRows = 10;
    public static final int worldCols = 20;

    public static int GRID_START_X;
    public static int GRID_START_Y;

    public static Paint white = new Paint();
    public static Paint whiteAlpha = new Paint();
    public static Paint black = new Paint();
    public static Paint tint = new Paint();
    public static Resources res;

    public static Vector2f tileToPoint(Vector2f p) {
        return new Vector2f((p.x + 0.5f) * tileWidth + GRID_START_X, (p.y + 0.5f) * tileHeight + GRID_START_Y);
    }

    public static Vector2f tileToCorner(Vector2f p) {
        return new Vector2f(p.x * tileWidth + GRID_START_X, p.y * tileHeight + GRID_START_Y);
    }

    public static Vector2f pointToTile(Vector2f p) {
        return new Vector2f((int)((p.x - GRID_START_X) / tileWidth), (int)((p.y - GRID_START_Y) / tileHeight));
    }

    public static int fireSpeedConvert(float fireSpeed) {
        if(fireSpeed == 0)
            return 0;
        return (int)(1 / (fireSpeed * 4) * 100);
    }

    public static void changeScene(Scene newScene) {
        scene.onSceneExit();
        scene = newScene;
        scene.onSceneEnter();
        Time.newScene();
    }

    public static int randomInt(float num1, float num2) {
        return (int)(Math.random() * (num1 - num2 + 1) + num1);
    }

    public Game(Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        scaleX = screenWidth / 1000f;
        scaleY = screenHeight / 500f;

        tileWidth = .8f * screenWidth / (float)worldCols;
        tileHeight = .7f * screenHeight / (float)worldRows;

        GRID_START_X = (int)(.1f * screenWidth);
        GRID_START_Y = (int)(.3f * screenHeight);

        tiles.put(0, new RedTile());
        tiles.put(1, new OrangeTile());
        tiles.put(2, new YellowTile());
        tiles.put(5, new SpawnTile());
        tiles.put(6, new StandardTower());
        tiles.put(7, new BruiserTower());
        tiles.put(8, new GlacierTower());
        tiles.put(9, new LightningTower());
        tiles.put(10, new WallTower());

        res = getResources();
        white.setColor(res.getColor(R.color.white, null));
        whiteAlpha.setColor(res.getColor(R.color.white, null));
        tint.setColor(res.getColor(R.color.white, null));
        black.setColor(res.getColor(R.color.black, null));
        tint.setAlpha(50);
        whiteAlpha.setAlpha(40);
        Images.init(getResources());

        miniGameSongs = new MediaPlayer[] {};

        scene = new MainMenu();
        scene.onSceneEnter();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scene.onMousePressed(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        scene.update(canvas);
    }

    public void draw(Canvas canvas, long time) {
        Time.update(time / 1000000);
        this.draw(canvas);
    }
}
