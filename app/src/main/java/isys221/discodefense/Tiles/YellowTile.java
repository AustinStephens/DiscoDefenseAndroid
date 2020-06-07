package isys221.discodefense.Tiles;

import isys221.discodefense.Images;

public class YellowTile extends Tile {

    public YellowTile() {
        index = 2;
        img = Images.yellowTile;
    }

    public Tile getInstance() { return new YellowTile();}
}
