package isys221.discodefense.Scenes.GameSceneClasses;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import isys221.discodefense.Game;
import isys221.discodefense.R;
import isys221.discodefense.Vector2f;

public class RhythmGameSingle {

    /*public AudioPlayer player;
    private AudioPlayer dummy;
    private BeatDetect beat;
    private BeatListener b1;*/


    private ArrayList<Note> notes = new ArrayList<Note>();
    private ArrayList<Particle> particles = new ArrayList<Particle>();

    private Paint whitePaint = new Paint();
    private Paint blackPaint = new Paint();

    private boolean start = false;
    private boolean test = true;
    private boolean pulse;

    private final float MAX_BEAT_TIME = .25f;
    private float beatTime = MAX_BEAT_TIME;

    private float player1StartTime = 1;
    private float player2StartTime = 1;

    public int good1 = 0;
    public int perfect1 = 0;
    public int miss1 = 0;

    public Player player1;

    private int wait = 0;
    private int wait2 = 0;
    private int wait3 = 0;
    private int wait4= 0;

    private int alpha = 200;
    private int hue = 0;

    private float length;
    private float creepHealthMult;

    public RhythmGameSingle(Player player1, int songIndex, float length, float creepHealthMult) {
        this.player1 = player1;
        this.length = length * 1000;
        this.creepHealthMult = creepHealthMult;

        whitePaint.setColor(Game.res.getColor(R.color.white, null));
        whitePaint.setColor(Game.res.getColor(R.color.black, null));

        /*player = miniGameSongs[songIndex];
        dummy = miniGameSongsDummy[songIndex];

        beat = new BeatDetect(dummy.bufferSize(), dummy.sampleRate());
        beat.setSensitivity(1000);
        b1 = new BeatListener(beat, dummy);

        dummy.rewind();
        dummy.play();
        dummy.setGain(-80);*/
    }

    public void draw(Canvas canvas) {
        //background(0);
        //beat.detect(dummy.mix);
        /*if (dummy.position() >= 3700 && !start) {
            player.rewind();
            player.play();
            start = true;
        } else if (dummy.position() > length - 3900) {
            dummy.pause();
        }

        if (beat.isHat() && wait4 == 0) {
            Note n = new Note(250, whitePaint, blackPaint);
            notes.add(n);
            wait4 +=10;
            //print("hat ");
        }
        pushStyle();
        //fill(255, 255, 20, alpha- 100);
        // ellipse(width/2, height/2, width, 50);*/

        //left rails
        //fill(255, 255, 255, alpha);
        canvas.drawRect(150, 0, 5, height);
        /*rect(350, 0, 5, height);
        rect(155, 0+20 , 195, 5);
        rect(155, height/4+20, 195, 5);
        rect(155, height/4 + height/8+20 , 195, 5);
        rect(155, height/2+20 , 195, 5);
        rect(155, height/2 + height/8+20, 195, 5);
        rect(155, height-height/4 +20, 195, 5);
        rect(155, height/8 +20, 195, 5);

        //Perfect line
        //fill(0, 255, 0);
        colorMode(HSB);
        fill(hue, 255, 255, alpha-50);
        rect(0, 450, 1000, 20);
        fill(hue, 255, 255, alpha);
        rect(0, 455, 1000, 10);
        if (hue<=255) hue ++;
        if (hue> 255) hue = 0;

        colorMode(RGB);



        fill(255);
        textSize(15);
        textAlign(LEFT);
        text("GOOD: " + good1, 10, 15 );
        text("PERFECT: " + perfect1, 10, 30);
        text("MISS: "+ miss1, 10, 45);
        popStyle();

        for (int i = 0; i <= particles.size() - 1; i++) {
            Particle p = particles.get(i);
            p.draw(canvas);
            if (p.alpha <= 0) {
                particles.remove(i);
            }
        }

        for (int i = 0; i <= notes.size() - 1; i++) {

            Note n = notes.get(i);
            n.draw(canvas);
            if (n.y > 480) {
                miss1++;
                Particle p = new Particle(0, n.x, n.y);
                particles.add(p);
                notes.remove(i);
            }
        }

        if (pulse) {
            alpha --;
        } else alpha ++;

        if (alpha < 150) pulse = false;
        if(alpha > 200) pulse = true;


        if (wait >0) wait --;
        if (wait2 >0) wait2 --;
        if (wait3 >0) wait3 --;
        if (wait4 >0) wait4 --;*/
    }

    public void addPlayer1Creep(boolean isPowered) {
        Creep c = new Creep(isPowered, true, creepHealthMult * player1.creepHealthMultiplyer);
        c.speedMult = player1.creepSpeedMultiplyer;
        c.spawnMult = player1.creepSpawnMultiplyer;
        c.timeToLeave = player1StartTime;
        player1StartTime += .8;
        int rand = Game.randomInt(3f, 7f);
        c.pos = new Vector2f(Game.GRID_START_X + Game.tileWidth / 2, Game.GRID_START_Y + ((rand + 0.5f) * Game.tileHeight));
        c.start = new Vector2f(0, rand);
        c.targetPos = new Vector2f(19, rand);
        player1.creeps.add(c);
    }

    public void onKeyPressed() {
        ////////////////////////////////////////////
        //player 1
        ////////////////////////////////////////////

        for (int i = 0; i <= notes.size() - 1; i++) {
            Note n = notes.get(i);
            // if ( n.x == 200 && n.y >=400) {
            if (n.x == 250 && n.y >=400) {
                if (n.y >= 430 && n.y < 450 || n.y >= 470 && n.y <= 480) {
                    addPlayer1Creep(false);
                    good1 ++;
                    Particle p = new Particle(1, n.x, n.y);
                    particles.add(p);
                } else if (n.y >= 450 && n.y <470) {
                    addPlayer1Creep(true);
                    perfect1++;
                    Particle p = new Particle(2, n.x, n.y);
                    particles.add(p);
                } else {
                    miss1++;
                    Particle p = new Particle(0, n.x, n.y);
                    particles.add(p);
                }

                notes.remove(i);
                break;
            }
        }
    }
}
