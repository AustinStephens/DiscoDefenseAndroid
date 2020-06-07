package isys221.discodefense.Tiles;

import isys221.discodefense.Images;

public class RedTile extends Tile {

    public RedTile() {
        index = 0;
        img = Images.redTile;
    }

    public Tile getInstance() { return new RedTile();}
}
