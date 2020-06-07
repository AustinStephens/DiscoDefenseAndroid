package isys221.discodefense.Scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import isys221.discodefense.Game;

public class Instructions implements Scene {

    private boolean buildingInstructions = true;
    private Rect backButton = new Rect(0,0,200, 200);

    public void update(Canvas canvas) {
        /*background(180);

        pushStyle();
        if(mouseX >= 0 && mouseX < 90 && mouseY >= 0 && mouseY < 70) {
            noStroke();
            fill(200, 0, 0, 150);
            rect(0, 0, 90, 70);
        }

        noStroke();
        if(buildingInstructions) {
            fill(200, 0, 0);
            rect(0, 155, 100, 105);
            fill(200, 0, 0, 150);
            rect(0, 260, 100, 105);
        } else {
            fill(200, 0, 0, 150);
            rect(0, 155, 100, 105);
            fill(200, 0, 0);
            rect(0, 260, 100, 105);
        }


        textSize(20);
        fill(0);
        textAlign(CENTER);
        text("Back", 45, 40);
        text("Building\nStage", 50, 200);
        text("Rhythm\nGame &\nIcons", 50, 290);
        textSize(30);
        text("Instructions", 500, 40);
        popStyle();

        fill(0);
        textSize(12);
        if(buildingInstructions) {
            text("The game starts out in the bulding stage, where you can build your towers. You each have a selected tile that\nstarts out in the top corner of each side.Player 1 moves this with the WASD keys, Player 2 uses the arrow keys.", 160, 100);
            image(Images.standardTower, 135, 160);
            text("Once you've highlighted the tile you want. Press the corresponding key to spawn the tower you want there, if you have enough coins.\nThe buttons are 1-5 for Player 1, and 1-5 on the num pad for Player 2, or 6,7,8,9, and 0 on the regular keyboard.", 180, 175);
            image(Images.creepNormal1, 135, 230);
            text("If you highlight one of the spawn tiles (the purple ones in front of your DJ stand). You can upgrade your minion's (what attack the enemy's\nbase) speed, health, and spawn rate. The buttons are 1-3 for Player 1 and 1-3 on the num pad for Player 2, or 6-8 on the regular keyboard.", 180, 240);
            image(Images.lightningTower, 135, 300);
            text("If you highlight a tile with a tower already on it, you'll be able to upgrade that tower. Press 1 for Player 1 or\n1 on the num pad / 6 for Player 2. Press 2 for Player 1 or 2 on the num pad / 7 for Player 2 to delete that tower.", 180, 315);
            image(Images.wall, 135, 370);
            text("If you block off all paths to your DJ stand, the enemy's minions will attack\nany tower that stands in their way and give the enemy double points.", 180, 385);
            text("This stage lasts 30 seconds each time. Click the mouse when both of you are done building.", 180, 450);
        } else {
            textAlign(CENTER);
            textSize(20);
            text("Rhythm Game", 500, 100);
            text("Icons", 500, 260);
            textAlign(LEFT);
            textSize(12);
            text("When the building stage is over, the rhythm mini game starts. Notes will come down the screen.\nPress 'q' for Player 1 or 7 on the num pad or keyboard for Player 2 when the note is on the yellow line.\nIf you hit it when it's on the yellow line, you'll earn a powered up minion, if it lands on the purple line, you'll get a regular, weaker minion.\nThe length of the mini game increases as the game goes on.", 160, 150);
            image(Images.smallCoin, 235, 300);
            text("Coins", 260, 313);
            image(Images.damageIcon, 390, 300);
            text("Damage", 415, 313);
            image(Images.fireSpeedIcon, 550, 300);
            text("Rate of Fire", 575, 313);
            image(Images.healthIcon, 740, 300);
            text("Health", 765, 313);
            image(Images.rangeIcon, 255, 370);
            text("Range", 280, 383);
            image(Images.minionSpeed, 420, 370);
            text("Speed (minions)", 445, 383);
            image(Images.minionSpawn, 620, 370);
            text("Spawn Rate (minions)", 645, 383);
        }*/
    }

    public void onSceneEnter() {
    }

    public void onSceneExit() {
    }

    public void onMousePressed(MotionEvent event) {
        int mouseX = (int) event.getX();
        int mouseY = (int) event.getY();
        if(backButton.contains(mouseX, mouseY))
            Game.changeScene(new MainMenu());
        /*else if(mouseX >= 0 && mouseX <= 100 && mouseY >= 150 && mouseY <= 260)
            buildingInstructions = true;
        else if(mouseX >= 0 && mouseX <= 100 && mouseY >= 260 && mouseY <= 365)
            buildingInstructions = false;*/
    }

    public void onKeyPressed() {

    }
}
