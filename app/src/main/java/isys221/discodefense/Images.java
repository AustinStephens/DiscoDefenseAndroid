package isys221.discodefense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

public class Images {

    public static Bitmap standardTower;
    public static Bitmap bruiserTower;
    public static Bitmap glacierTower;
    public static Bitmap lightningTower;
    public static Bitmap wall;

    public static Bitmap djStand1;
    public static Bitmap djStand2;

    public static Bitmap creepNormal1;
    public static Bitmap creepUpgraded1;

    public static Bitmap creepNormal2;
    public static Bitmap creepUpgraded2;

    public static Bitmap orangeTile;
    public static Bitmap yellowTile;
    public static Bitmap redTile;
    public static Bitmap spawnTile;

    public static Bitmap healthbar;
    public static Bitmap healthbarBackground;

    public static Bitmap damageIcon;
    public static Bitmap healthIcon;
    public static Bitmap fireSpeedIcon;
    public static Bitmap rangeIcon;
    public static Bitmap deleteIcon;

    public static Bitmap minionSpeed;
    public static Bitmap minionSpawn;

    public static Bitmap bigCoin;
    public static Bitmap smallCoin;

    public static Bitmap towerAttack;

    public static Bitmap playButton;
    public static Bitmap singleButton;
    public static Bitmap multiButton;
    public static Bitmap creditsButton;
    public static Bitmap instructionsButton;
    public static Bitmap easyButton;
    public static Bitmap hardButton;

    public static void init(Resources res) {
        playButton = BitmapFactory.decodeResource(res, R.drawable.button_play);
        creditsButton = BitmapFactory.decodeResource(res, R.drawable.button_credits);
        instructionsButton = BitmapFactory.decodeResource(res, R.drawable.button_controls);
        singleButton = BitmapFactory.decodeResource(res, R.drawable.button_singleplayer);
        easyButton = BitmapFactory.decodeResource(res, R.drawable.button_easy);
        hardButton = BitmapFactory.decodeResource(res, R.drawable.button_hard);

        towerAttack = BitmapFactory.decodeResource(res, R.drawable.tower_attack);
        bigCoin = BitmapFactory.decodeResource(res, R.drawable.coin_big);
        smallCoin = BitmapFactory.decodeResource(res, R.drawable.coin_small);

        minionSpawn = BitmapFactory.decodeResource(res, R.drawable.minion_stats_spawn);
        minionSpeed = BitmapFactory.decodeResource(res, R.drawable.minion_stats_speed);

        orangeTile = BitmapFactory.decodeResource(res, R.drawable.floor_tiles_orange);
        yellowTile = BitmapFactory.decodeResource(res, R.drawable.floor_tiles_yellow);
        redTile = BitmapFactory.decodeResource(res, R.drawable.floor_tiles_red);
        spawnTile = BitmapFactory.decodeResource(res, R.drawable.floor_tiles_purple);

        standardTower = BitmapFactory.decodeResource(res, R.drawable.tower_1_standard);
        bruiserTower = BitmapFactory.decodeResource(res, R.drawable.tower_2_bruiser);
        glacierTower = BitmapFactory.decodeResource(res, R.drawable.tower_3_glacier);
        lightningTower = BitmapFactory.decodeResource(res, R.drawable.tower_4_lightning);
        wall = BitmapFactory.decodeResource(res, R.drawable.tower_5_wall);

        djStand1 = BitmapFactory.decodeResource(res, R.drawable.dj_stand_player1);
        //djStand1 = tintBitmap(djStand1, 0x64C8FF);
        //djStand1 = tintBitmap(BitmapFactory.decodeResource(res, R.drawable.dj_stand_player1), 0x64C8FF);
        djStand2 = BitmapFactory.decodeResource(res, R.drawable.dj_stand_player2);

        creepNormal1 = BitmapFactory.decodeResource(res, R.drawable.creep_player1_normal);
        creepUpgraded1 = BitmapFactory.decodeResource(res, R.drawable.creep_player1_upgraded);
        creepNormal2 = BitmapFactory.decodeResource(res, R.drawable.creep_player2_normal);
        creepUpgraded2 = BitmapFactory.decodeResource(res, R.drawable.creep_player2_upgraded);

        healthbar = BitmapFactory.decodeResource(res, R.drawable.health_bar);
        healthbarBackground = BitmapFactory.decodeResource(res, R.drawable.health_bar_empty);
        healthIcon = BitmapFactory.decodeResource(res, R.drawable.stat_icons_health);
        damageIcon = BitmapFactory.decodeResource(res, R.drawable.stat_icons_damage);
        fireSpeedIcon = BitmapFactory.decodeResource(res, R.drawable.stat_icons_firerate);
        deleteIcon = BitmapFactory.decodeResource(res, R.drawable.destroy_tower);
        rangeIcon = BitmapFactory.decodeResource(res, R.drawable.stat_icons_range);
    }

    public static Bitmap tintBitmap(Bitmap src, int color) {
        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        result.isMutable();
        Canvas c = new Canvas(djStand1);
        Paint tint = new Paint();
        tint.setColorFilter(new LightingColorFilter(color, 0));
        c.drawBitmap(src, 0, 0, tint);
        return result;
    }
}
