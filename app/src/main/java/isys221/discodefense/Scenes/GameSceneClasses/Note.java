package isys221.discodefense.Scenes.GameSceneClasses;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Note {
    public int x;
    public int y;
    private int w = 37;
    private int h = 17;
    private int w2 = 30;
    private int h2 = 10;

    private Paint whitePaint;
    private Paint blackPaint;

    Note(int X, Paint blackPaint, Paint whitePaint) {
        x=X;
        y = 0;
        this.whitePaint = whitePaint;
        this.blackPaint = blackPaint;
    }


    void draw(Canvas canvas){
        canvas.drawRect(x - w/2,y-h/2,w,h, whitePaint);

        canvas.drawRect(x -w2/2, y-h2/2, w2, h2, blackPaint);
        y+=2;
    }
}
