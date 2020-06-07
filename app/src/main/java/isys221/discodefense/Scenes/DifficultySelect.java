package isys221.discodefense.Scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import isys221.discodefense.Game;
import isys221.discodefense.Images;

public class DifficultySelect implements Scene {


    private Rect spButton1 = new Rect((int)(Game.screenWidth * .33f), (int)(Game.screenHeight * .31f), (int)(Game.screenWidth * .46f), (int)(Game.screenHeight * .51f));
    private Rect easyButton = new Rect((int)(Game.screenWidth * .46f), (int)(Game.screenHeight * .31f), (int)(Game.screenWidth * .66f), (int)(Game.screenHeight * .51f));
    private Rect spButton2 = new Rect((int)(Game.screenWidth * .33f), (int)(Game.screenHeight * .61f), (int)(Game.screenWidth * .46f), (int)(Game.screenHeight * .81f));
    private Rect hardButton = new Rect((int)(Game.screenWidth * .46f), (int)(Game.screenHeight * .61f), (int)(Game.screenWidth * .66f), (int)(Game.screenHeight * .81f));
    private Rect backButton = new Rect(0,0,(int)(Game.screenWidth * .17f), (int)(Game.screenHeight * .2f));

    public void update(Canvas canvas) {
        /*
        textSize(20);
        fill(0);
        textAlign(CENTER);
        text("Back", 45, 40);
        fill(255);

        textSize(38);
        text("Choose a Mode", 500, 60);
        image(Images.multiButton, 440, 150);
        textSize(20);
        text("Multiplayer", 500, 130);
        image(Images.singleButton, 340, 280);
        image(Images.easyButton, 460, 280);
        text("Singleplayer", 500, 260);
        image(Images.singleButton, 340, 380);
        image(Images.hardButton, 460, 380);
        textSize(12);*/

        canvas.drawText("Singleplayer", Game.screenWidth * .5f, (int)(Game.screenHeight * .21f), Game.white);
        canvas.drawBitmap(Images.singleButton, null, spButton1, null);
        canvas.drawBitmap(Images.easyButton, null, easyButton, null);
        canvas.drawBitmap(Images.singleButton, null, spButton2, null);
        canvas.drawBitmap(Images.hardButton, null, hardButton, null);
    }

    public void onSceneEnter() {
    }

    public void onSceneExit() {
    }

    public void onMousePressed(MotionEvent event) {
        int mouseX = (int) event.getX();
        int mouseY = (int) event.getY();
        //if(mouseX >= 440 && mouseX <= 560 && mouseY >= 150 && mouseY <= 230)
            //Game.changeScene(new GameSceneMultiplayer());
        if(easyButton.contains(mouseX, mouseY) || spButton1.contains(mouseX, mouseY))
            Game.changeScene(new GameSceneSingleplayer(1));
        else if(hardButton.contains(mouseX, mouseY) || spButton2.contains(mouseX, mouseY))
            Game.changeScene(new GameSceneSingleplayer(2));
        else if(backButton.contains(mouseX, mouseY))
            Game.changeScene(new MainMenu());
    }

    public void onKeyPressed() {

    }
}
