package isys221.discodefense.Tiles.Towers;

import isys221.discodefense.Images;
import isys221.discodefense.Tiles.Tile;

public class GlacierTower extends TowerTile {

    public GlacierTower() {
        super(new UpgradeData[] { new UpgradeData(50, 50, 150, .5f, 0), new UpgradeData(75, 60, 200, .45f, 100), new UpgradeData(100, 75, 250, .35f, 200), new UpgradeData(100, 85, 275, .25f, 350) }, 1000, 200, 8, Images.lightningTower);
    }

    public GlacierTower(Tile background) {
        this();
        backgroundTile = background.getInstance();
    }

    @Override
    public Tile getInstance(Tile background) {
        return new GlacierTower(background);
    }
}
