import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class PlayerGunshot extends Sprite implements Serializable
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
        y -= speed;
        if(y < 0) { die(); }
    }

    @Override
    public void draw(Graphics g, GameField gameField, BufferedImage bufferedImage) {
        for(int i = 0; i < gameField.playerGunshots.toArray().length; i++) {
            PlayerGunshot shot = gameField.playerGunshots.get(i);
            if(shot.isVisiable()) {
                try {
                    g.drawImage(bufferedImage, shot.x, shot.y, gameField);
                } catch (Exception EXC) {}
            } else {
                shot.die();
                gameField.playerGunshots.remove(shot);
            }
        }
    }

    @Override
    public void update(GameField gameField) {
        for(PlayerGunshot shot : gameField.playerGunshots) {
            shot.move();
        }
        for(Enemy enemy : gameField.enemies) {
            for(PlayerGunshot shot : gameField.playerGunshots) {
                if(shot.y >= enemy.y && shot.x >= enemy.x && shot.y <= enemy.y+Values.ENEMY_HEIGHT && shot.x <= enemy.x+Values.ENEMY_WIDTH) {
                    shot.setVisiable(false);
                    enemy.setVisiable(false);
                    gameField.enemiesCount -= 1;
                }
            }
        }
    }

    public PlayerGunshot(int x, int y, int speed) {
        super(x, y, speed);
    }
}
