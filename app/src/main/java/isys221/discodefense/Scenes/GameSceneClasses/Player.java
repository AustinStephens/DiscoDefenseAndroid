package isys221.discodefense.Scenes.GameSceneClasses;

import java.util.ArrayList;

import isys221.discodefense.Vector2f;

public class Player {

    private final int MAX_HEALTH = 200;

    public ArrayList<Creep> creeps = new ArrayList<Creep>();

    public int health;
    public int fans;

    public int selectedTileX;
    public int selectedTileY;
    public Vector2f selectedBuildTile;

    public float creepHealthMultiplyer = 1;
    public float creepSpeedMultiplyer = 1;
    public float creepSpawnMultiplyer = 1;

    public Player(Vector2f selected) {
        health = MAX_HEALTH;
        fans = 200;
        selectedBuildTile = selected;
    }

    public float healthPercent() {
        return (float)health / MAX_HEALTH;
    }

}
