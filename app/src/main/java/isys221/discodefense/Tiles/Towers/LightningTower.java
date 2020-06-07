package isys221.discodefense.Tiles.Towers;

import isys221.discodefense.Images;
import isys221.discodefense.Tiles.Tile;

public class LightningTower extends TowerTile {

    public LightningTower() {
        super(new UpgradeData[] { new UpgradeData(25, 50, 150, .25f, 0), new UpgradeData(50, 60, 200, .20f, 200), new UpgradeData(75, 75, 225, .15f, 300), new UpgradeData(100, 90, 250, .1f, 400) }, 1000, 400, 9, Images.lightningTower);
    }

    public LightningTower(Tile background) {
        this();
        backgroundTile = background.getInstance();
    }

    @Override
    public Tile getInstance(Tile background) {
        return new LightningTower(background);
    }
}
