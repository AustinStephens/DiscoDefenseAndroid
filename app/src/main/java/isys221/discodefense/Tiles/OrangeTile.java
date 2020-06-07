package isys221.discodefense.Tiles;

import isys221.discodefense.Images;

public class OrangeTile extends Tile {

    public OrangeTile() {
        index = 1;
        img = Images.orangeTile;
    }

    public Tile getInstance() { return new OrangeTile();}
}
