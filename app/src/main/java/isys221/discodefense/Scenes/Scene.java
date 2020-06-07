package isys221.discodefense.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {

    public void update(Canvas canvas);

    public void onSceneEnter();
    public void onSceneExit();

    public void onMousePressed(MotionEvent event);
    public void onKeyPressed();
}
