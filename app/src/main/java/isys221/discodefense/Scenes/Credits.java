package isys221.discodefense.Scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import static isys221.discodefense.Game.changeScene;

public class Credits implements Scene {

    private Rect backButton = new Rect(0,0,200, 200);

    public void update(Canvas canvas) {
        /*if(mouseX >= 0 && mouseX < 90 && mouseY >= 0 && mouseY < 70) {
            noStroke();
            fill(200, 0, 0, 150);
            rect(0, 0, 90, 70);
        }

        textSize(20);
        fill(0);
        textAlign(CENTER);
        text("Back", 45, 40);
        fill(255);
        textSize(12);
        textAlign(LEFT);*/
    }

    public void onSceneEnter() {
    }

    public void onSceneExit() {
    }

    public void onMousePressed(MotionEvent event) {
        int mouseX = (int) event.getX();
        int mouseY = (int) event.getY();
        if(backButton.contains(mouseX, mouseY))
          changeScene(new MainMenu());
    }

    public void onKeyPressed() {
    }
}
