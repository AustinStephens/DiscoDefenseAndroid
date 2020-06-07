package isys221.discodefense.Tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import isys221.discodefense.Game;
import isys221.discodefense.Scenes.GameSceneClasses.Player;
import isys221.discodefense.Vector2f;

public abstract class Tile {

    public Tile backgroundTile = null;
    public int cost = 1;
    public int fanCost;
    public Vector2f pos;
    public int index;
    public Bitmap img;
    public RectF rect;// = new RectF(pos.x, pos.y, pos.x + Game.tileWidth, pos.y + Game.tileHeight);

    public void update(Player enemy) {
    }

    public void display(Canvas canvas) {
        rect = new RectF(pos.x, pos.y, pos.x + Game.tileWidth, pos.y + Game.tileHeight);
        canvas.drawBitmap(img, null, rect, null);
    }

    public Tile getInstance() { return null;}
    public Tile getInstance(Tile background) { return null;}

    public void setBackgroundTile(int index) {
        backgroundTile = Game.tiles.get(index).getInstance();
        backgroundTile.pos = pos;
    }
}
