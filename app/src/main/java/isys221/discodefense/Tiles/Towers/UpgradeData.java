package isys221.discodefense.Tiles.Towers;

public class UpgradeData {

    public int health;
    public int damage;
    public int range;
    public float fireSpeed;
    public int fanCost;

    public UpgradeData(int health, int damage, int range, float fireSpeed, int fanCost) {
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.fireSpeed = fireSpeed;
        this.fanCost = fanCost;
    }

    public UpgradeData(int health, int fanCost) {
        this.health = health;
        this.fanCost = fanCost;
    }
}
