package isys221.discodefense.Tiles.Towers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import isys221.discodefense.Scenes.GameSceneClasses.Creep;
import isys221.discodefense.Game;
import isys221.discodefense.Scenes.GameSceneClasses.Player;
import isys221.discodefense.Tiles.Tile;
import isys221.discodefense.Time;
import isys221.discodefense.Vector2f;

public abstract class TowerTile extends Tile {

    public int upgradeIndex;
    public int health;
    public UpgradeData[] upgrades;
    private float timeLeftToShoot;

    private Creep target;
    private ArrayList<Creep> inRange = new ArrayList<Creep>();
    private float showProjectile = 0;

    public TowerTile(UpgradeData[] upgrades, int cost, int fanCost, int index, Bitmap img) {
        upgradeIndex = 0;
        this.upgrades = upgrades;
        this.img = img;
        health = this.upgrades[0].health;
        timeLeftToShoot = 0;

        this.cost = cost;
        this.fanCost = fanCost;
        this.index = index;
    }

    @Override
    public void display(Canvas canvas) {
        backgroundTile.display(canvas);
        rect = new RectF(pos.x, pos.y, pos.x + Game.tileWidth, pos.y + Game.tileHeight);
        canvas.drawBitmap(img, null, rect, null);
    }

    public void displayRadius(Canvas canvas) {
        /*fill(255, 255, 255, 50);
        noStroke();
        ellipse(pos.x + tileWidth / 2, pos.y + tileHeight / 2, getRange(0) * 2, getRange(0) * 2);*/
        canvas.drawCircle(pos.x + Game.tileWidth / 2, pos.y + Game.tileHeight / 2, getRange(0), Game.whiteAlpha);
    }

    @Override
    public void update(Player enemy) {
        inRange.clear();
        ArrayList<Creep> creeps = enemy.creeps;

        for(int i = 0; i < creeps.size(); ++i) {
            Creep c = creeps.get(i);
            if(Vector2f.sub(new Vector2f(pos.x + Game.tileWidth / 2, pos.y + Game.tileHeight / 2), c.pos).mag() <= getRange(0)) inRange.add(c);
        }

        if(!inRange.isEmpty()) {
            target = inRange.get(0);

            timeLeftToShoot -= Time.deltaTime;
            if(timeLeftToShoot <= 0) {
                target.health -= getDamage(0);
                showProjectile = .05f;
                showProjectile();
                timeLeftToShoot = getFireSpeed(0);
            } else if(showProjectile > 0) {
                showProjectile -= Time.deltaTime;
                showProjectile();
            }
        }
    }

    public void showProjectile() {
        float px = pos.x + Game.tileWidth / 2;
        float py = pos.y + Game.tileHeight / 2;
        /*pushMatrix();
        translate(px, py);
        rotate(atan2(target.pos.y - py, target.pos.x - px));
        image(Images.towerAttack, 0, -7.5, PVector.sub(new PVector(px, py), target.pos).mag(), 15);
        //rect(0, -7.5, PVector.sub(new PVector(px, py), target.pos).mag(), 15);
        popMatrix();*/
    }

    public int getDamage(int index) {
        return upgrades[upgradeIndex + index].damage;
    }

    public float getFireSpeed(int index) {
        return upgrades[upgradeIndex + index].fireSpeed;
    }

    public int getRange(int index) {
        return upgrades[upgradeIndex + index].range;
    }

    public int getUpgradeFanCost(int index) {
        if(upgradeIndex + index < upgrades.length)
            return upgrades[upgradeIndex + index].fanCost;

        return -1;
    }

    public boolean upgrade() {
        if(upgradeIndex + 1 < upgrades.length) {
            upgradeIndex++;
            return true;
        }

        return false;
    }

    public void upgradeHealth() {
        health = upgrades[upgradeIndex].health;
    }
}
