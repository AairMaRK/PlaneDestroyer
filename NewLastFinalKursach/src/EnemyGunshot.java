import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class EnemyGunshot extends Sprite implements Serializable
{
    private boolean visiable = true;

    public boolean isVisiable() {
        return visiable;
    }
    public void setVisiable(boolean b) { visiable = b; }

    @Override
    public void die() {
        super.die();
        visiable = false;
    }

    @Override
    public void move() {
        y += speed;
        if(y > Values.FIELD_HEIGHT) { die(); }
    }

    @Override
    public void draw(Graphics g, GameField gameField, BufferedImage bufferedImage) {
        for(int i = 0; i < gameField.enemyGunshots.toArray().length; i++) {
            EnemyGunshot enemyGunshot = gameField.enemyGunshots.get(i);
            if(enemyGunshot.isVisiable()) {
                try {
                    g.drawImage(bufferedImage, enemyGunshot.x, enemyGunshot.y, gameField);
                } catch (Exception EXC) {}
            } else {
                enemyGunshot.die();
                gameField.enemyGunshots.remove(enemyGunshot);
            }
        }
    }

    @Override
    public void update(GameField gameField) {
        for (EnemyGunshot enemyGunshot : gameField.enemyGunshots) {
            enemyGunshot.move();
            if(enemyGunshot.y >= gameField.player.y && enemyGunshot.x >= gameField.player.x && enemyGunshot.y <= gameField.player.y+Values.ENEMY_HEIGHT && enemyGunshot.x <= gameField.player.x+Values.ENEMY_WIDTH) {
                enemyGunshot.setVisiable(false);
                gameField.inGame = false;
            }
        }
    }

    public EnemyGunshot(int x, int y, int speed) {
        super(x, y, speed);
    }
}
