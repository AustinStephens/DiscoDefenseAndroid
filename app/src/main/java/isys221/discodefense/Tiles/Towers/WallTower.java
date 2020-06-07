package isys221.discodefense.Tiles.Towers;

import isys221.discodefense.Images;
import isys221.discodefense.Scenes.GameSceneClasses.Player;
import isys221.discodefense.Tiles.Tile;

public class WallTower extends TowerTile {

    public WallTower() {
        super(new UpgradeData[] { new UpgradeData(75, 0), new UpgradeData(100, 50), new UpgradeData(125, 75), new UpgradeData(150, 100) }, 1002, 50, 10, Images.wall);
    }

    public WallTower(Tile background) {
        this();
        backgroundTile = background.getInstance();
    }

    @Override
    public void update(Player enemy) {}

    @Override
    public Tile getInstance(Tile background) {
        return new WallTower(background);
    }
}
