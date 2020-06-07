package isys221.discodefense.Scenes.GameSceneClasses;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Queue;

import isys221.discodefense.Game;
import isys221.discodefense.Images;
import isys221.discodefense.Pathfinding;
import isys221.discodefense.Time;
import isys221.discodefense.Vector2f;

public class Creep {

    public Vector2f targetPos;
    private Queue<Vector2f> path;
    public Vector2f nextTile;
    public Vector2f pos;
    public Vector2f start;
    public boolean isDead = false;

    public float health;
    public boolean isPowered;

    public float speedMult;
    public float spawnMult;

    public float timeToLeave = 0;
    private float speed = 50;
    private boolean isPlayer1;

    private float hoverCounter = 0f;
    private final float hoverHeight = 3f;
    private final float hoverSpeed = 6;

    public Creep(boolean isPowered, boolean isPlayer1, float healthMult) {
        this.isPowered = isPowered;
        this.isPlayer1 = isPlayer1;
        if(isPowered)
            health = 125 * healthMult;
        else
            health = 75 * healthMult;
    }

    public void update(Canvas canvas) {
        timeUpdate();
        hoverCounter += Time.deltaTime;
        Bitmap img;

        if(isPlayer1) {
            //tint(100, 200, 255);
            img = (isPowered ? Images.creepUpgraded1 : Images.creepNormal1);
        } else {
            img = (isPowered? Images.creepUpgraded2 : Images.creepNormal2);
        }

        float hover = (float)Math.sin(hoverCounter * hoverSpeed) * hoverHeight;
        if(isPowered) {
            canvas.drawBitmap(img, null, new RectF(pos.x - Game.tileWidth / 2, pos.y - Game.tileHeight / 2 + hover, pos.x + Game.tileWidth / 2, pos.y + Game.tileHeight / 2 - hover), null);
        } else {
            canvas.drawBitmap(img, null, new RectF(pos.x - Game.tileWidth / 2 + 2, 2 + pos.y - Game.tileHeight / 2 + hover, pos.x + Game.tileWidth / 2 - 2, pos.y + Game.tileHeight / 2 - hover - 2), null);
        }
        //noTint();
        if(nextTile != null) {
            if(Game.tileToPoint(nextTile).equals(pos)) {
                nextTile = path.poll();
            } else {
                Vector2f distance = Vector2f.sub(Game.tileToPoint(nextTile), pos);
                Vector2f dir = distance.copy().normalize();
                Vector2f travel = new Vector2f(speed * speedMult * Time.deltaTime * dir.x, speed * speedMult * Time.deltaTime * dir.y);
                if(travel.mag() > distance.mag()) {
                    if(nextTile.equals(targetPos) || (nextTile.x == targetPos.x && nextTile.y > 2 && nextTile.y < 7)) {
                        isDead = true;
                        targetPos = nextTile.copy();
                    } else
                        pos = Game.tileToPoint(nextTile.copy());
                } else
                    pos.add(travel);
            }
        }

        if(health < 0) isDead = true;
    }

    public void timeUpdate() {
        if(timeToLeave >= 0) {
            timeToLeave -= Time.deltaTime * spawnMult;
        }
    }

    public void findPath() {
        path = Pathfinding.findPath(start, targetPos);
        nextTile = path.poll();
        nextTile = path.poll();
    }
}
