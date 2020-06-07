package isys221.discodefense.Tiles.Towers;

import isys221.discodefense.Images;
import isys221.discodefense.Tiles.Tile;

public class BruiserTower extends TowerTile {

    public BruiserTower() {
        super(new UpgradeData[] { new UpgradeData(25, 25, 150, .25f, 0), new UpgradeData(50, 40, 200, .20f, 100), new UpgradeData(75, 50, 225, .15f, 200), new UpgradeData(100, 65, 250, .1f, 350) }, 1000, 200, 7, Images.bruiserTower);
    }

    public BruiserTower(Tile background) {
        this();
        backgroundTile = background.getInstance();
    }

    @Override
    public Tile getInstance(Tile background) {
        return new BruiserTower(background);
    }
}
