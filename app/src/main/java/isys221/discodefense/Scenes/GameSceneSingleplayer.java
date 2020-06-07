package isys221.discodefense.Scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import isys221.discodefense.Images;
import isys221.discodefense.MainThread;
import isys221.discodefense.R;
import isys221.discodefense.Scenes.GameSceneClasses.Creep;
import isys221.discodefense.Game;
import isys221.discodefense.Pathfinding;
import isys221.discodefense.Scenes.GameSceneClasses.Player;
import isys221.discodefense.Scenes.GameSceneClasses.RhythmGameSingle;
import isys221.discodefense.Tiles.Tile;
import isys221.discodefense.Tiles.Towers.TowerTile;
import isys221.discodefense.Time;
import isys221.discodefense.Vector2f;
import isys221.discodefense.Scenes.GameSceneClasses.*;

public class GameSceneSingleplayer implements Scene {

    private RhythmGameSingle miniGame = null;

    public Player player1 = new Player(new Vector2f(0,0));
    public Player player2 = new Player(new Vector2f(19, 0));
    public int difficulty;

    private boolean isBuildingState;
    private boolean isMiniGame = false;
    private boolean isGameOver = false;
    private boolean player1Won;
    private final float healthBarLength = 110 * Game.scaleX;

    private final float MAX_BUILD_TIME = 30;
    private float buildTimeLeft = MAX_BUILD_TIME;

    private float maxRhythmTime = 10;
    private float rhythmTimeLeft = maxRhythmTime;

    private final float MAX_AI_BUILD_TIME = .5f;
    private float aiBuildTimeLeft = MAX_AI_BUILD_TIME;
    public ArrayList<Integer> aiTowers = new ArrayList<Integer>();
    public ArrayList<Vector2f> aiPos = new ArrayList<Vector2f>();
    public ArrayList<Integer> aiUpgrades = new ArrayList<Integer>();
    private boolean computerDoneBuilding = false;

    private int startingColorIndex = 0;
    private int beats = 0;
    public int turns = 0;
    private float creepHealthMult = 1;
    private int creepSpeedCost = 100;
    private int creepHealthCost = 75;
    private int creepSpawnCost = 125;

    //Towers
    private TowerTile[] towers = {(TowerTile)Game.tiles.get(6), (TowerTile)Game.tiles.get(7), (TowerTile)Game.tiles.get(8), (TowerTile)Game.tiles.get(9), (TowerTile)Game.tiles.get(10)};

    // RECTS
    private RectF grid = new RectF(Game.GRID_START_X, Game.GRID_START_Y, .9f * Game.screenWidth, Game.screenHeight);
    private RectF djStand1 = new RectF(.01f * Game.screenWidth, .4f * Game.screenHeight, .1f * Game.screenWidth, .9f * Game.screenHeight);
    private RectF djStand2 = new RectF(.9f * Game.screenWidth, .4f * Game.screenHeight, .99f * Game.screenWidth, .9f * Game.screenHeight);
    private RectF coin1 = new RectF(.25f * Game.screenWidth, .03f * Game.screenHeight, .28f  * Game.screenWidth, .07f * Game.screenHeight);
    private RectF coin2 = new RectF(.61f * Game.screenWidth, .03f * Game.screenHeight, .64f  * Game.screenWidth, .07f * Game.screenHeight);
    private RectF hbb1 = new RectF(.085f * Game.screenWidth, .03f * Game.screenHeight, .085f * Game.screenWidth + healthBarLength + 4, .085f * Game.screenHeight);
    private RectF hbb2 = new RectF(.82f * Game.screenWidth, .03f * Game.screenHeight, .82f * Game.screenWidth + healthBarLength + 4, .085f * Game.screenHeight);
    //creep upgrades
    private RectF cr1sd = new RectF(.03f * Game.screenWidth, .12f * Game.screenHeight, .11f * Game.screenWidth, .24f * Game.screenHeight);
    private RectF cr1h = new RectF(.25f * Game.screenWidth, .12f * Game.screenHeight, .33f * Game.screenWidth, .24f * Game.screenHeight);
    private RectF cr1sw = new RectF(.6f * Game.screenWidth, .12f * Game.screenHeight, .68f * Game.screenWidth, .24f * Game.screenHeight);
    private RectF cr1sdIcon = new RectF(.09f * Game.screenWidth, .215f * Game.screenHeight, .12f * Game.screenWidth, .255f * Game.screenHeight);
    private RectF cr1hIcon = new RectF(.31f * Game.screenWidth, .215f * Game.screenHeight, .34f * Game.screenWidth, .255f * Game.screenHeight);
    private RectF cr1swIcon = new RectF(.65f * Game.screenWidth, .215f * Game.screenHeight, .68f * Game.screenWidth, .255f * Game.screenHeight);
    //building towers
    private RectF standardTower = new RectF(.015f * Game.screenWidth, .16f * Game.screenHeight, .09f * Game.screenWidth, .26f * Game.screenHeight);
    private RectF bruiserTower = new RectF(.15f * Game.screenWidth, .16f * Game.screenHeight, .225f * Game.screenWidth, .26f * Game.screenHeight);
    private RectF glacierTower = new RectF(.295f * Game.screenWidth, .16f * Game.screenHeight, .37f * Game.screenWidth, .26f * Game.screenHeight);
    private RectF lightningTower = new RectF(.6f * Game.screenWidth, .16f * Game.screenHeight, .675f * Game.screenWidth, .26f * Game.screenHeight);
    private RectF wallTower = new RectF(.8f * Game.screenWidth, .16f * Game.screenHeight, .875f * Game.screenWidth, .26f * Game.screenHeight);
    private RectF coinCost1 = new RectF(.035f * Game.screenWidth, .11f * Game.screenHeight, .055f * Game.screenWidth, .14f * Game.screenHeight);
    private RectF coinCost2 = new RectF(.17f * Game.screenWidth, .11f * Game.screenHeight, .19f * Game.screenWidth, .14f * Game.screenHeight);
    private RectF coinCost3 = new RectF(.315f * Game.screenWidth, .11f * Game.screenHeight, .335f * Game.screenWidth, .14f * Game.screenHeight);
    private RectF coinCost4 = new RectF(.62f * Game.screenWidth, .11f * Game.screenHeight, .64f * Game.screenWidth, .14f * Game.screenHeight);
    private RectF coinCost5 = new RectF(.82f * Game.screenWidth, .11f * Game.screenHeight, .84f * Game.screenWidth, .14f * Game.screenHeight);

    private RectF damage1 = new RectF(.09f * Game.screenWidth, .155f * Game.screenHeight, .11f * Game.screenWidth, .185f * Game.screenHeight);
    private RectF health1 = new RectF(.09f * Game.screenWidth, .2f * Game.screenHeight, .11f * Game.screenWidth, .23f * Game.screenHeight);
    private RectF rate1 = new RectF(.09f * Game.screenWidth, .245f * Game.screenHeight, .11f * Game.screenWidth, .275f * Game.screenHeight);
    private RectF damage2 = new RectF(.225f * Game.screenWidth, .155f * Game.screenHeight, .245f * Game.screenWidth, .185f * Game.screenHeight);
    private RectF health2 = new RectF(.225f * Game.screenWidth, .2f * Game.screenHeight, .245f * Game.screenWidth, .23f * Game.screenHeight);
    private RectF rate2 = new RectF(.225f * Game.screenWidth, .245f * Game.screenHeight, .245f * Game.screenWidth, .275f * Game.screenHeight);
    private RectF damage3 = new RectF(.37f * Game.screenWidth, .155f * Game.screenHeight, .39f * Game.screenWidth, .185f * Game.screenHeight);
    private RectF health3 = new RectF(.37f * Game.screenWidth, .2f * Game.screenHeight, .39f * Game.screenWidth, .23f * Game.screenHeight);
    private RectF rate3 = new RectF(.37f * Game.screenWidth, .245f * Game.screenHeight, .39f * Game.screenWidth, .275f * Game.screenHeight);
    private RectF damage4 = new RectF(.68f * Game.screenWidth, .155f * Game.screenHeight, .7f * Game.screenWidth, .185f * Game.screenHeight);
    private RectF health4 = new RectF(.68f * Game.screenWidth, .2f * Game.screenHeight, .7f * Game.screenWidth, .23f * Game.screenHeight);
    private RectF rate4 = new RectF(.68f * Game.screenWidth, .245f * Game.screenHeight, .7f * Game.screenWidth, .275f * Game.screenHeight);
    private RectF damage5 = new RectF(.88f * Game.screenWidth, .155f * Game.screenHeight, .9f * Game.screenWidth, .185f * Game.screenHeight);
    private RectF health5 = new RectF(.88f * Game.screenWidth, .2f * Game.screenHeight, .9f * Game.screenWidth, .23f * Game.screenHeight);
    private RectF rate5 = new RectF(.88f * Game.screenWidth, .245f * Game.screenHeight, .9f * Game.screenWidth, .275f * Game.screenHeight);

    // Upgrades
    private RectF normalTower = new RectF(.03f * Game.screenWidth, .15f * Game.screenHeight, .1f * Game.screenWidth, .25f * Game.screenHeight);
    private RectF normalHealth = new RectF(.11f * Game.screenWidth, .13f * Game.screenHeight, .13f * Game.screenWidth, .155f * Game.screenHeight);
    private RectF normalDamage = new RectF(.11f * Game.screenWidth, .17f * Game.screenHeight, .13f * Game.screenWidth, .195f * Game.screenHeight);
    private RectF normalRate = new RectF(.11f * Game.screenWidth, .21f * Game.screenHeight, .13f * Game.screenWidth, .235f * Game.screenHeight);
    private RectF normalRange = new RectF(.11f * Game.screenWidth, .25f * Game.screenHeight, .13f * Game.screenWidth, .275f * Game.screenHeight);
    private RectF deleteIcon = new RectF(.3f * Game.screenWidth, .15f * Game.screenHeight, .35f * Game.screenWidth, .23f * Game.screenHeight);
    private RectF upgradeRect = new RectF(.6f * Game.screenWidth, .15f * Game.screenHeight, .67f * Game.screenWidth, .25f * Game.screenHeight);
    private RectF upgradeHealth = new RectF(.68f * Game.screenWidth, .13f * Game.screenHeight, .7f * Game.screenWidth, .155f * Game.screenHeight);
    private RectF upgradeDamage = new RectF(.68f * Game.screenWidth, .18f * Game.screenHeight, .7f * Game.screenWidth, .205f * Game.screenHeight);
    private RectF upgradeRate = new RectF(.68f * Game.screenWidth, .23f * Game.screenHeight, .7f * Game.screenWidth, .255f * Game.screenHeight);
    private RectF upgradeRange = new RectF(.8f * Game.screenWidth, .13f * Game.screenHeight, .82f * Game.screenWidth, .155f * Game.screenHeight);
    private RectF upgradeCost = new RectF(.8f * Game.screenWidth, .18f * Game.screenHeight, .82f * Game.screenWidth, .205f * Game.screenHeight);

    private int[][] world = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Tile[][] worldTiles = new Tile[10][20];

    public GameSceneSingleplayer(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void update(Canvas canvas) {
        if (isMiniGame) {
            miniGame.draw(canvas);
            if (rhythmTimeLeft <= 0)
                onMiniGameEnd();
            else rhythmTimeLeft -= Time.deltaTime;
        } else {
            mapUpdate(canvas);
            if(isGameOver) {
                gameOverUpdate(canvas);
            } else if (isBuildingState)
                buildingUpdate(canvas);
            else
                attackUpdate(canvas);
        }
    }

    @Override
    public void onSceneEnter() {
        for (int i = 0; i < Game.worldRows; ++i) {
            for (int j = 0; j < Game.worldCols; ++j) {
                if ((i >= 3 && i <= 6) && (j == 0 || j == 19)) {
                    world[i][j] = 5;
                    instantiate(i,j);
                } else
                    instantiate(i,j);
            }
        }
        setDanceFloor();
        isBuildingState = true;
        AI.chooseComputerTowers(this);
    }

    @Override
    public void onSceneExit() {
        Time.timeScale = 1;
    }

    @Override
    public void onMousePressed(MotionEvent event) {
        float mouseX = event.getX();
        float mouseY = event.getY();
        if (isBuildingState) {
            Vector2f p = player1.selectedBuildTile;
            int tile = world[(int)p.y][(int)p.x];
            if(grid.contains(mouseX, mouseY)) {
                Vector2f ind = Game.pointToTile(new Vector2f(mouseX, mouseY));
                if(ind.x <= 8)
                    player1.selectedBuildTile = ind;
            } else if(mouseX >= .46f * Game.screenWidth && mouseY >= .08f * Game.screenHeight && mouseX <=.54f * Game.screenWidth && mouseY <= .25f * Game.screenHeight) {
                startMiniGame(MainThread.canvas);
            } else if (tile < 5) {
                if(standardTower.contains(mouseX, mouseY)) setTile(p, 6, true);
                else if(bruiserTower.contains(mouseX, mouseY)) setTile(p, 7, true);
                else if(glacierTower.contains(mouseX, mouseY)) setTile(p, 8, true);
                else if(lightningTower.contains(mouseX, mouseY)) setTile(p, 9, true);
                else if(wallTower.contains(mouseX, mouseY)) setTile(p, 10, true);
            } else if(tile == 5) {
                if(cr1sd.contains(mouseX, mouseY) && player1.fans - creepSpeedCost >= 0 && player1.creepSpeedMultiplyer < 3.5f) {
                    player1.creepSpeedMultiplyer += .25f;
                    player1.fans -= creepSpeedCost;
                } else if(cr1h.contains(mouseX, mouseY) && player1.fans - creepHealthCost >= 0) {
                    player1.creepHealthMultiplyer += .25f;
                    player1.fans -= creepHealthCost;
                } else if(cr1sw.contains(mouseX, mouseY) && player1.fans - creepSpawnCost >= 0 && player1.creepSpawnMultiplyer < 2.0f) {
                    player1.creepSpawnMultiplyer += .2f;
                    player1.fans -= creepSpawnCost;
                }
            } else if(tile > 5) {
                TowerTile tileObj = (TowerTile) worldTiles[(int)p.y][(int)p.x];
                if(upgradeRect.contains(mouseX, mouseY) && player1.fans - tileObj.getUpgradeFanCost(1) >= 0) {
                    if(tileObj.upgrade()) {
                        player1.fans -= tileObj.getUpgradeFanCost(0);
                        tileObj.upgradeHealth();
                    }
                } else if (deleteIcon.contains(mouseX, mouseY)) {
                    player1.fans += tileObj.fanCost / 2;
                    world[(int)p.y][(int)p.x] = tileObj.backgroundTile.index;
                    worldTiles[(int)p.y][(int)p.x] = tileObj.backgroundTile;
                }
            }
        } else if (isMiniGame) {
            miniGame.onKeyPressed();
        } else if (isGameOver) {
            if(mouseX >= 260 && mouseX <= 500 && mouseY >= 350 - Game.tileHeight * 2 && mouseY <= 350) {
                Game.changeScene(new MainMenu());
            } else if(mouseX > 500 && mouseX <= 740 && mouseY >= 350 - Game.tileHeight * 2 && mouseY <= 350) {
                Game.changeScene(new GameSceneSingleplayer(difficulty));
            }
        }
    }

    @Override
    public void onKeyPressed() {
        if (isMiniGame) {
            miniGame.onKeyPressed();
        }
    }

    private void setDanceFloor() {
        int index = startingColorIndex;
        for(int i = 0; i < Game.worldRows; ++i) {
            for(int j = i, k = 0; j >=0 && k < Game.worldCols; --j, ++k) {
                changeTile(j,k,index);
            }
            index = (index + 1) % 3;
        }

        for(int i = 1; i < Game.worldCols; ++i) {
            for(int j = 9, k = i; j >= 0 && k < Game.worldCols; --j, ++k) {
                changeTile(j,k,index);
            }
            index = (index + 1) % 3;
        }
    }

    private void setupScene(Canvas canvas) {
        //initializing fans and health
        Game.white.setTextAlign(Paint.Align.LEFT);
        //canvas.drawRect(0,0, Game.screenWidth, 120 * Game.scaleY, Game.black);
        //player 1 text
        Game.white.setTextSize(30);
        canvas.drawText("You: ", .04f * Game.screenWidth, .06f * Game.screenHeight, Game.white);
        //enemy text
        canvas.drawText("Enemy: ", .75f * Game.screenWidth, .06f * Game.screenHeight, Game.white);
        //player 1 health bar
        canvas.drawBitmap(Images.healthbarBackground, null, hbb1, null);
        RectF r = new RectF(.086f * Game.screenWidth, .031f * Game.screenHeight, .085f * Game.screenWidth + healthBarLength * player1.healthPercent(), .084f * Game.screenHeight);
        canvas.drawBitmap(Images.healthbar, null, r, null);
        //enemy health bar
        canvas.drawBitmap(Images.healthbarBackground, null, hbb2, null);
        r.left = 0.821f * Game.screenWidth;
        r.right = .82f * Game.screenWidth + healthBarLength * player2.healthPercent();
        canvas.drawBitmap(Images.healthbar, null, r, null);
        //fan icon placeholder
        canvas.drawBitmap(Images.bigCoin, null, coin1, null);
        canvas.drawBitmap(Images.bigCoin, null, coin2, null);
        //fan count
        canvas.drawText("" + player1.fans, .30f * Game.screenWidth, .06f * Game.screenHeight, Game.white); // p1
        canvas.drawText("" + player2.fans, .66f * Game.screenWidth, .06f * Game.screenHeight, Game.white); //p2
        //dj tower 1
        canvas.drawBitmap(Images.djStand1, null, djStand1, null);
        //dj tower 2
        canvas.drawBitmap(Images.djStand2, null, djStand2, null);
    }

    public void changeTile(int j, int k, int index) {
        if(world[j][k] > 5) {
            worldTiles[j][k].setBackgroundTile(index);
        } else {
            if (!(j >= 3 && j <= 6) || !(k == 0 || k == 19)) {
                world[j][k] = index;
                instantiate(j,k);
            }
        }
    }

    private void instantiate(int i, int j) {
        worldTiles[i][j] = Game.tiles.get(world[i][j]).getInstance();
        worldTiles[i][j].pos = Game.tileToCorner(new Vector2f(j, i));
    }

    private void instantiate(int i, int j, int index) {
        worldTiles[i][j] = Game.tiles.get(world[i][j]).getInstance(Game.tiles.get(index));
        Vector2f p = Game.tileToCorner(new Vector2f(j, i));
        worldTiles[i][j].pos = p;
        worldTiles[i][j].backgroundTile.pos = p;
    }

    private void setTile(Vector2f p, int num, boolean isPlayer1) {
        Player player = (isPlayer1 ? player1 : player2);

        if(player.fans - Game.tiles.get(num).fanCost >= 0) {
            int i = world[(int)p.y][(int)p.x];
            world[(int)p.y][(int)p.x] = num;
            instantiate((int)p.y, (int)p.x, i);
            player.fans -= Game.tiles.get(num).fanCost;
        }
    }

    private void gameOver(boolean player1Lost) {
        Time.timeScale = 0;
        isGameOver = true;
        player1Won = !player1Lost;
    }

    private void mapUpdate(Canvas canvas) {
        setupScene(canvas);
        //beat.detect(currentMusic.mix);
        /*if(beat.isOnset()) {
            beats++;
            if(beats % 3 == 0)
                startingColorIndex = (startingColorIndex + 1) % 3;
        }*/
        setDanceFloor();
        for (int i = 0; i < Game.worldRows; ++i)
        {
            for (int j = 0; j < Game.worldCols; ++j) {
                worldTiles[i][j].display(canvas);
            }
        }
    }

    private void gameOverUpdate(Canvas canvas) {
        /*fill(80, 100);
        rect(260, 150, 480, 200);
        if(mouseX >= 260 && mouseX <= 500 && mouseY >= 350 - tileHeight * 2 && mouseY <= 350) {
            fill(0, 255, 50, 120);
            rect(260, 350 - tileHeight * 2, 240, tileHeight * 2);
        } else if(mouseX > 500 && mouseX <= 740 && mouseY >= 350 - tileHeight * 2 && mouseY <= 350) {
            fill(0, 255, 50, 120);
            rect(500, 350 - tileHeight * 2, 240, tileHeight * 2);
        }
        fill(0);
        textSize(50);
        textAlign(CENTER);
        text("Game Over", 500, 200);
        text("Player " + (player1Won ? "1" : "2") + " won", 500, 260);
        //text("Click to restart", 500, 320);
        textAlign(LEFT);
        textSize(40);
        text("Main Menu", 275, 320);
        text("Restart", 550, 320);
        textSize(12);*/
    }

    private void attackUpdate(Canvas canvas) {
        for (int i = player1.creeps.size() - 1; i >= 0; --i) {
            creepLoop(true, i, canvas);
        }

        for (int i = player2.creeps.size() - 1; i >= 0; --i) {
            creepLoop(false, i, canvas);
        }

        for (int i = 0; i < Game.worldRows; ++i)
        {
            for (int j = 0; j < Game.worldCols; ++j) {
                worldTiles[i][j].update((j < 10 ? player2 : player1));
            }
        }

        if (player1.creeps.isEmpty() && player2.creeps.isEmpty()) {
            isBuildingState = true;
            computerDoneBuilding = false;
            AI.chooseComputerTowers(this);
        }
    }

    private void buildingUpdate(Canvas canvas) {
        //fill(255);
        Game.white.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Building Stage", .5f * Game.screenWidth, .05f * Game.screenHeight, Game.white);

        float startAngle = 270;
        //colorMode(HSB);
        Paint paint = new Paint();
        float percent = buildTimeLeft / MAX_BUILD_TIME;
        int color = (percent > .5 ? R.color.green : (percent > .25 ? R.color.orange : R.color.red));
        paint.setColor(Game.res.getColor(color, null));
        canvas.drawArc(.46f * Game.screenWidth, .08f * Game.screenHeight, .54f * Game.screenWidth, .25f * Game.screenHeight, startAngle, 360 * percent, true, paint);
        // highlighting player 1 selected tile

        Vector2f p = player1.selectedBuildTile;
        Game.black.setStyle(Paint.Style.STROKE);
        Vector2f t = worldTiles[(int)p.y][(int)p.x].pos;
        canvas.drawRect(t.x, t.y, t.x + Game.tileWidth, t.y + Game.tileHeight, Game.black);


        // player 1 ui options
        int tile = world[(int)p.y][(int)p.x];
        if(tile < 5) {
            // display tower options
            //towers
            canvas.drawBitmap(Images.standardTower, null, standardTower, null);
            canvas.drawBitmap(Images.bruiserTower, null, bruiserTower, null);
            canvas.drawBitmap(Images.glacierTower, null, glacierTower, null);
            canvas.drawBitmap(Images.lightningTower, null, lightningTower, null);
            canvas.drawBitmap(Images.wall, null, wallTower, null);

            //coins
            canvas.drawBitmap(Images.smallCoin, null, coinCost1,null);
            canvas.drawBitmap(Images.smallCoin, null, coinCost2,null);
            canvas.drawBitmap(Images.smallCoin, null, coinCost3,null);
            canvas.drawBitmap(Images.smallCoin, null, coinCost4,null);
            canvas.drawBitmap(Images.smallCoin, null, coinCost5,null);

            Game.white.setTextSize(30);
            canvas.drawText(towers[0].fanCost + "", .08f * Game.screenWidth, .135f * Game.screenHeight, Game.white);
            canvas.drawText(towers[1].fanCost + "", .215f * Game.screenWidth, .135f * Game.screenHeight, Game.white);
            canvas.drawText(towers[2].fanCost + "", .36f * Game.screenWidth, .135f * Game.screenHeight, Game.white);
            canvas.drawText(towers[3].fanCost + "", .665f * Game.screenWidth, .135f * Game.screenHeight, Game.white);
            canvas.drawText(towers[4].fanCost + "", .865f * Game.screenWidth, .135f * Game.screenHeight, Game.white);

            // stats
            canvas.drawBitmap(Images.damageIcon, null, damage1,null);
            canvas.drawBitmap(Images.healthIcon, null, health1,null);
            canvas.drawBitmap(Images.fireSpeedIcon, null, rate1,null);
            canvas.drawBitmap(Images.damageIcon, null, damage2,null);
            canvas.drawBitmap(Images.healthIcon, null, health2,null);
            canvas.drawBitmap(Images.fireSpeedIcon, null, rate2,null);
            canvas.drawBitmap(Images.damageIcon, null, damage3,null);
            canvas.drawBitmap(Images.healthIcon, null, health3,null);
            canvas.drawBitmap(Images.fireSpeedIcon, null, rate3,null);
            canvas.drawBitmap(Images.damageIcon, null, damage4,null);
            canvas.drawBitmap(Images.healthIcon, null, health4,null);
            canvas.drawBitmap(Images.fireSpeedIcon, null, rate4,null);
            canvas.drawBitmap(Images.damageIcon, null, damage5,null);
            canvas.drawBitmap(Images.healthIcon, null, health5,null);
            canvas.drawBitmap(Images.fireSpeedIcon, null, rate5,null);

            canvas.drawText(towers[0].getDamage(0) + "", .13f * Game.screenWidth, .175f * Game.screenHeight, Game.white);
            canvas.drawText(towers[0].health + "", .13f * Game.screenWidth, .225f * Game.screenHeight, Game.white);
            canvas.drawText(Game.fireSpeedConvert(towers[0].getFireSpeed(0)) + "", .13f * Game.screenWidth, .27f * Game.screenHeight, Game.white);
            canvas.drawText(towers[1].getDamage(0) + "", .265f * Game.screenWidth, .175f * Game.screenHeight, Game.white);
            canvas.drawText(towers[1].health + "", .265f * Game.screenWidth, .225f * Game.screenHeight, Game.white);
            canvas.drawText(Game.fireSpeedConvert(towers[1].getFireSpeed(0)) + "", .265f * Game.screenWidth, .27f * Game.screenHeight, Game.white);
            canvas.drawText(towers[2].getDamage(0) + "", .41f * Game.screenWidth, .175f * Game.screenHeight, Game.white);
            canvas.drawText(towers[2].health + "", .41f * Game.screenWidth, .225f * Game.screenHeight, Game.white);
            canvas.drawText(Game.fireSpeedConvert(towers[2].getFireSpeed(0)) + "", .41f * Game.screenWidth, .27f * Game.screenHeight, Game.white);
            canvas.drawText(towers[3].getDamage(0) + "", .72f * Game.screenWidth, .175f * Game.screenHeight, Game.white);
            canvas.drawText(towers[3].health + "", .72f * Game.screenWidth, .225f * Game.screenHeight, Game.white);
            canvas.drawText(Game.fireSpeedConvert(towers[3].getFireSpeed(0)) + "", .72f * Game.screenWidth, .27f * Game.screenHeight, Game.white);
            canvas.drawText(towers[4].getDamage(0) + "", .92f * Game.screenWidth, .175f * Game.screenHeight, Game.white);
            canvas.drawText(towers[4].health + "", .92f * Game.screenWidth, .225f * Game.screenHeight, Game.white);
            canvas.drawText(Game.fireSpeedConvert(towers[4].getFireSpeed(0)) + "", .92f * Game.screenWidth, .27f * Game.screenHeight, Game.white);

        } else if (tile == 5) {
            //display creep upgrade options
            //#1
            Game.white.setTextSize(40);
            Game.white.setTextAlign(Paint.Align.LEFT);
            //canvas.drawText("1:", .03f * Game.screenWidth, .2f * Game.screenHeight, Game.white);
            //tint(100, 200, 255);
            canvas.drawBitmap(Images.creepNormal1, null , cr1sd,null);
            canvas.drawBitmap(Images.minionSpeed, null, cr1sdIcon, null);
            canvas.drawText((int)(player1.creepSpeedMultiplyer * 100) + "%", .15f * Game.screenWidth, .189f * Game.screenHeight, Game.white);
            canvas.drawBitmap(Images.creepNormal1, null , cr1h,null);
            canvas.drawBitmap(Images.healthIcon, null, cr1hIcon, null);
            canvas.drawText((int)(player1.creepHealthMultiplyer * 100) + "%", .37f * Game.screenWidth, .189f * Game.screenHeight, Game.white);
            canvas.drawBitmap(Images.creepNormal1, null , cr1sw,null);
            canvas.drawBitmap(Images.minionSpawn, null, cr1swIcon, null);
            canvas.drawText((int)(player1.creepSpawnMultiplyer * 100) + "%", .72f * Game.screenWidth, .189f * Game.screenHeight, Game.white);
            //tint(150, 150, 255);

        } else {
            // display tower upgrade options
            displayUpgradeUI(player1, canvas);
        }

        //if(computerDoneBuilding) {
        aiBuildTimeLeft -= Time.deltaTime;
        if(aiBuildTimeLeft <= 0) {
            if(aiTowers.size() > 0) {
                Vector2f pos = aiPos.get(aiPos.size() - 1);
                player2.selectedBuildTile = pos;
                if(world[(int)pos.y][(int)pos.x] < 5) {
                    setTile(pos, aiTowers.get(aiTowers.size() - 1), false);
                } else if(((TowerTile)worldTiles[(int)pos.y][(int)pos.x]).upgrade()) {
                    TowerTile t1 = (TowerTile) worldTiles[(int)pos.y][(int)pos.x];
                    player1.fans -= t1.getUpgradeFanCost(0);
                    t1.upgradeHealth();
                }
                aiTowers.remove(aiTowers.size() - 1);
                aiPos.remove(aiPos.size() - 1);
                aiBuildTimeLeft = MAX_AI_BUILD_TIME;
            } else if(aiUpgrades.size() > 0) {
                player2.selectedBuildTile = new Vector2f(19, 3);
                switch(aiUpgrades.get(aiUpgrades.size() - 1)) {
                    case 1:
                        if(player2.fans >= creepSpeedCost) {
                            player2.creepSpeedMultiplyer += .25f;
                            player2.fans -= creepSpeedCost;
                        }
                    case 2:
                        if(player2.fans >= creepHealthCost) {
                            player2.creepHealthMultiplyer += .25f;
                            player2.fans -= creepHealthCost;
                        }
                    case 3:
                        if(player2.fans >= creepSpawnCost) {
                            player2.creepSpawnMultiplyer += .2f;
                            player2.fans -= creepSpawnCost;
                        }
                }
                aiUpgrades.remove(aiUpgrades.size() - 1);
                aiBuildTimeLeft = MAX_AI_BUILD_TIME;
            } else computerDoneBuilding = true;
        }
        //}

        //highlighting player 2 selected tile
        p = player2.selectedBuildTile;
        t = worldTiles[(int)p.y][(int)p.x].pos;
        canvas.drawRect(t.x, t.y, t.x + Game.tileWidth, t.y + Game.tileHeight, Game.black);
        Game.black.setStyle(Paint.Style.FILL);

        if (buildTimeLeft <= 0) {
            startMiniGame(canvas);
        } else buildTimeLeft -= Time.deltaTime;
    }

    private void displayUpgradeUI(Player player, Canvas canvas) { //sX = starting X coord
        Vector2f p = player.selectedBuildTile;
        TowerTile tileObj = (TowerTile) worldTiles[(int)p.y][(int)p.x];

        canvas.drawBitmap(tileObj.img, null, normalTower, null);
        //canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 3, new float[] {.2f * Game.screenWidth, .18f * Game.screenHeight, .21f * Game.screenWidth, .2f * Game.screenHeight, .2f * Game.screenWidth, .22f * Game.screenHeight}, 0, null, 0, null, 0,  new short[] {1,3,2}, 0, 3,  Game.white);
        if(tileObj.index == 10) {
            //info for current tower
            canvas.drawBitmap(Images.healthIcon, null, normalHealth, null);
            canvas.drawText(tileObj.health + "", .145f * Game.screenWidth, .15f * Game.screenHeight, Game.white);
        } else {
            canvas.drawBitmap(Images.healthIcon, null, normalHealth, null);
            canvas.drawText(tileObj.health + "", .147f * Game.screenWidth, .15f * Game.screenHeight, Game.white);
            canvas.drawBitmap(Images.damageIcon, null, normalDamage, null);
            canvas.drawText(tileObj.getDamage(0) + "", .147f * Game.screenWidth, .19f * Game.screenHeight, Game.white);
            canvas.drawBitmap(Images.fireSpeedIcon, null, normalRate, null);
            canvas.drawText(Game.fireSpeedConvert(tileObj.getFireSpeed(0)) + "", .147f * Game.screenWidth, .23f * Game.screenHeight, Game.white);
            canvas.drawBitmap(Images.rangeIcon, null, normalRange, null);
            canvas.drawText(tileObj.getRange(0) + "", .147f * Game.screenWidth, .27f * Game.screenHeight, Game.white);
            // radius around tower
            tileObj.displayRadius(canvas);
        }
        if(tileObj.upgradeIndex + 1 < tileObj.upgrades.length) {
            canvas.drawBitmap(tileObj.img, null, upgradeRect, null);
            //canvas.drawRect(.18f * Game.screenWidth, .19f * Game.screenHeight, .205f * Game.screenWidth, .21f * Game.screenHeight, Game.white);
            if(tileObj.index == 10) {
                // info for upgraded tower
                canvas.drawBitmap(Images.healthIcon, null, upgradeHealth, null);
                canvas.drawBitmap(Images.smallCoin, null, upgradeCost, null);
                canvas.drawBitmap(Images.deleteIcon, null, deleteIcon, null);
                canvas.drawText(tileObj.upgrades[tileObj.upgradeIndex + 1].health + " (+" + (tileObj.upgrades[tileObj.upgradeIndex + 1].health - tileObj.health) + ")", .735f * Game.screenWidth, .15f * Game.screenHeight, Game.white);
                canvas.drawText(tileObj.getUpgradeFanCost(1) + "", .855f * Game.screenWidth, .2f * Game.screenHeight, Game.white);
                //image(Images.healthIcon, sX + 238, 58);
                //text(tileObj.upgrades[tileObj.upgradeIndex + 1].health + " (+" + (tileObj.upgrades[tileObj.upgradeIndex + 1].health - tileObj.health) + ")", sX + 260, 70);
                //image(Images.healthIcon, 268, 88);
                //image(Images.smallCoin, sX + 235, 90);
                //text(tileObj.getUpgradeFanCost(1), sX + 260, 100);
                //text("2.", sX + 370, 50);
                //image(Images.deleteIcon, sX + 370, 70);
            } else {
                canvas.drawBitmap(Images.healthIcon, null, upgradeHealth, null);
                canvas.drawBitmap(Images.damageIcon, null, upgradeDamage, null);
                canvas.drawBitmap(Images.fireSpeedIcon, null, upgradeRate, null);
                canvas.drawBitmap(Images.rangeIcon, null, upgradeRange, null);
                canvas.drawBitmap(Images.smallCoin, null, upgradeCost, null);
                canvas.drawBitmap(Images.deleteIcon, null, deleteIcon, null);
                canvas.drawText(tileObj.upgrades[tileObj.upgradeIndex + 1].health + " (+" + (tileObj.upgrades[tileObj.upgradeIndex + 1].health - tileObj.health) + ")", .735f * Game.screenWidth, .15f * Game.screenHeight, Game.white);
                canvas.drawText(tileObj.getDamage(1) + " (+" + (tileObj.getDamage(1) - tileObj.getDamage(0)) + ")", .735f * Game.screenWidth, .2f * Game.screenHeight, Game.white);
                canvas.drawText(Game.fireSpeedConvert(tileObj.getFireSpeed(1)) + " (+" + (Game.fireSpeedConvert(tileObj.getFireSpeed(1)) - Game.fireSpeedConvert(tileObj.getFireSpeed(0))) + ")", .735f * Game.screenWidth, .25f * Game.screenHeight, Game.white);
                canvas.drawText(tileObj.getRange(1) + " (+" + (tileObj.getRange(1) - tileObj.getRange(0)) + ")", .87f * Game.screenWidth, .15f * Game.screenHeight, Game.white);
                canvas.drawText(tileObj.getUpgradeFanCost(1) + "", .855f * Game.screenWidth, .2f * Game.screenHeight, Game.white);
                //canvas.drawText(tileObj.upgrades[tileObj.upgradeIndex + 1].health + " (+" + (tileObj.upgrades[tileObj.upgradeIndex + 1].health - tileObj.health) + ")", sX + 245, 57);
                /*textSize(8);
                image(Images.healthIcon, sX + 223, 47);
                text(tileObj.upgrades[tileObj.upgradeIndex + 1].health + " (+" + (tileObj.upgrades[tileObj.upgradeIndex + 1].health - tileObj.health) + ")", sX + 245, 57);
                image(Images.damageIcon, sX + 225, 72);
                text(tileObj.getDamage(1) + " (+" + (tileObj.getDamage(1) - tileObj.getDamage(0)) + ")", sX + 245, 82);
                image(Images.fireSpeedIcon, sX + 225, 97);
                text(fireSpeedConvert(tileObj.getFireSpeed(1)) + " (+" + (fireSpeedConvert(tileObj.getFireSpeed(1)) - fireSpeedConvert(tileObj.getFireSpeed(0))) + ")", sX + 245, 107);
                image(Images.rangeIcon, sX + 290, 58);
                text(tileObj.getRange(1) + " (+" + (tileObj.getRange(1) - tileObj.getRange(0)) + ")", sX + 315, 70);
                image(Images.smallCoin, sX + 290, 85);
                text(tileObj.getUpgradeFanCost(1), sX + 315, 95);
                textSize(12);
                text("2.", sX + 390, 50);
                image(Images.deleteIcon, sX + 390, 70);*/
            }
        }
    }

    private void creepLoop(boolean isPlayer1, int i, Canvas canvas) {
        Player player = (isPlayer1 ? player1 : player2);
        Player enemy = (isPlayer1 ? player2 : player1);
        Creep c = player.creeps.get(i);
        if(c.timeToLeave <= 0) c.update(canvas);
        else c.timeUpdate();

        Tile t = null;
        boolean isOwnTower = false;
        if(c.nextTile != null) {
            t = worldTiles[(int)c.nextTile.y][(int)c.nextTile.x];
            isOwnTower = (isPlayer1 && c.nextTile.x < 9) || (!isPlayer1 && c.nextTile.x > 9);
        }

        if(t != null && t.index > 5 && !isOwnTower) {
            TowerTile tt = (TowerTile) t;
            if(tt.health - 25 > 0) tt.health -= 25;
            else {
                world[(int)c.nextTile.y][(int)c.nextTile.x] = t.backgroundTile.index;
                worldTiles[(int)c.nextTile.y][(int)c.nextTile.x] = t.backgroundTile;
            }
            player.creeps.remove(i);
        } else if (c.isDead) {
            if(c.health > 0) {
                if(enemy.health - 25 > 0)
                    enemy.health -= 25;
                else
                    gameOver(!isPlayer1);
                player.fans += 20;
            } else {
                player.fans += 10;
            }
            player.creeps.remove(i);
        }
    }

    private void startMiniGame(Canvas canvas) {
        isBuildingState = false;
        buildTimeLeft = MAX_BUILD_TIME;
        isMiniGame = true;
        miniGame = new RhythmGameSingle(player1, Game.randomInt(0, Game.miniGameSongs.length), maxRhythmTime, creepHealthMult);
        maxRhythmTime += 3;
        turns++;
        if(turns % 3 == 0) {
            creepHealthMult += .3f;
        }

        //pause background music
        //currentMusic.pause();
        //musicPaused = true;
    }

    private void onMiniGameEnd() {
        rhythmTimeLeft = maxRhythmTime;
        //miniGame.player.pause();
        isMiniGame = false;
        // resume background music
        //currentMusic.play();
        //musicPaused = false;

        int totalPossibleCreeps = miniGame.miss1 + miniGame.perfect1 + miniGame.good1;
        int numOfCreeps = (difficulty == 1 ? (int)(Game.randomInt(totalPossibleCreeps / 2, totalPossibleCreeps)) : totalPossibleCreeps);
        float startTime = 1;
        for(int i = 0; i < numOfCreeps; ++i) {
            Creep c;
            if(difficulty == 1)
                c = new Creep(Game.randomInt(0,1) > .5f, false, creepHealthMult * player2.creepHealthMultiplyer);
            else
                c = new Creep(true, false, creepHealthMult * 1.2f * player2.creepHealthMultiplyer);
            player2.creeps.add(c);
            c.speedMult = player2.creepSpeedMultiplyer;
            c.spawnMult = player2.creepSpawnMultiplyer;
            c.timeToLeave = startTime;
            startTime += .8;
            int rand = Game.randomInt(3, 7);
            c.pos = new Vector2f(Game.GRID_START_X + (19.5f * Game.tileWidth), Game.GRID_START_Y + ((rand + 0.5f) * Game.tileHeight));
            c.start = new Vector2f(19, rand);
            c.targetPos = new Vector2f(0, rand);
        }

        //remake map for pathfinder
        for(int i = 0; i < Game.worldRows; ++i) {
            for(int j = 0; j < Game.worldCols; ++j) {
                Pathfinding.PathTile pt = new Pathfinding.PathTile();
                Tile t = Game.tiles.get(world[i][j]);

                pt.cost = t.cost;
                pt.x = j;
                pt.y = i;

                Pathfinding.worldTiles[i][j] = pt;
            }
        }

        for(int i = 0; i < Game.worldRows; ++i) {
            for(int j = 0; j < Game.worldCols; ++j) {
                Pathfinding.worldTiles[i][j].setNeighbors();
            }
        }

        // find paths
        for (int i = 0; i < player1.creeps.size(); ++i) {
            player1.creeps.get(i).findPath();
        }

        for (int i = 0; i < player2.creeps.size(); ++i) {
            player2.creeps.get(i).findPath();
        }
    }

}
