package isys221.discodefense;

public class Time {
    public static float deltaTime = 0;
    public static float timeScale = 1;
    public static float timeSinceScene = 0;
    private static float prevTime = 0;

    public static void update(float milis) {
        float currentTime = milis;
        deltaTime = toSeconds(currentTime - prevTime);
        prevTime = currentTime;

        timeSinceScene += deltaTime;
    }

    public static float toSeconds(float milis) {
        return milis / 1000 * timeScale;
    }

    public static void newScene() {
        timeSinceScene = 0;
    }
}
