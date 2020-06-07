package isys221.discodefense.Scenes.GameSceneClasses;

import android.graphics.Canvas;

public class Particle {

    public boolean good = false;
    public boolean perfect = false;
    public boolean miss = false;
    public int x;
    public int y;
    public int radius = 0;
    public int alpha = 255;

    public Particle(int bol, int X, int Y){
        if(bol ==1) good = true;
        if( bol == 2) perfect = true;
        if (bol == 0) miss = true;
        x= X;
        y = Y;

    }

    public void draw(Canvas canvas){
        /*pushStyle();
        if(good) fill(200,20,255,alpha);
        else if (perfect) fill(200,255,20,alpha);
        else if (miss) fill(255,255,255, alpha);
        ellipse(x, y, radius, radius);
        radius++;
        alpha -=5;
        popStyle();*/
    }
}

