package isys221.discodefense.Tiles;

import isys221.discodefense.Images;

public class SpawnTile extends Tile {

    public SpawnTile() {
        index = 5;
        img = Images.spawnTile;
    }

    public Tile getInstance() {
        return new SpawnTile();
    }
}
