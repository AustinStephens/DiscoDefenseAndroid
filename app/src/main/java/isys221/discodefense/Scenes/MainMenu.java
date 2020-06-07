package isys221.discodefense.Scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import isys221.discodefense.Game;
import isys221.discodefense.Images;

public class MainMenu implements Scene {

    private Rect playButtonAABB = new Rect((int)(Game.screenWidth * .4f), (int)(Game.screenHeight * .3f), (int)(Game.screenWidth * .6f), (int)(Game.screenHeight * .475f));
    private Rect controlsButtonAABB = new Rect((int)(Game.screenWidth * .3f), (int)(Game.screenHeight * .525f), (int)(Game.screenWidth * .7f), (int)(Game.screenHeight * .7f));
    private Rect creditsButtonAABB = new Rect((int)(Game.screenWidth * .33f), (int)(Game.screenHeight * .75f), (int)(Game.screenWidth * .66f), (int)(Game.screenHeight * .925f));

    public void update(Canvas canvas) {
        /*fill(100);
        noStroke();
        rect(300, 150, 400, 300);
        fill(255);*/
        /*textAlign(CENTER);
        textSize(60);
        text("RAVE", 500, 90);
        textSize(12);
        textAlign(LEFT);
        image(Images.playButton, 400, 160);
        image(Images.instructionsButton, 319, 260);
        image(Images.creditsButton, 340, 360);*/

        Game.white.setTextSize(140);
        Game.white.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("RAVE", Game.screenWidth * 0.5f, 90 * Game.scaleY, Game.white);
        canvas.drawBitmap(Images.playButton, null, playButtonAABB, null);
        canvas.drawBitmap(Images.instructionsButton, null, controlsButtonAABB, null);
        canvas.drawBitmap(Images.creditsButton, null, creditsButtonAABB, null);
    }

    public void onSceneEnter() {//start music
    }
    public void onSceneExit() {//end music
    }

    public void onMousePressed(MotionEvent event) {
        int mouseX = (int)event.getX();
        int mouseY = (int)event.getY();

        if(playButtonAABB.contains(mouseX, mouseY))
            Game.changeScene(new DifficultySelect());
        else if(controlsButtonAABB.contains(mouseX, mouseY))
            Game.changeScene(new Instructions());
        else if(creditsButtonAABB.contains(mouseX, mouseY))
            Game.changeScene(new Credits());
    }

    public void onKeyPressed() {}
}
