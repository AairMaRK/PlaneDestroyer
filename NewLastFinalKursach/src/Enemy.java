import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Enemy extends Sprite implements Serializable
{
    private boolean isVisiable = true;
    private boolean moveRight = true;
    private boolean moveDown = true;

    public boolean isVisiable() { return isVisiable; }

    public void setMoveRight(boolean b) { moveRight = b; }
    public void setMoveDown(boolean b) { moveDown = b; }
    public void setVisiable(boolean b) { isVisiable = b; }

    @Override
    public void die() {
        super.die();
        isVisiable = false;
    }

    @Override
    public void move() {
        if(moveRight && isVisiable) {
            x += speed;
        } else if(!moveRight && isVisiable) {
            x -= speed;
        }
        if(moveDown && isVisiable) {
            y -= speed;
        } else if(!moveDown && isVisiable ) {
            y += speed;
        }
        if(x < Values.ENEMY_WIDTH) {
            x = Values.ENEMY_WIDTH;
            moveRight = !moveRight;
        }
        if(x >= Values.FIELD_WIDTH - 2*Values.ENEMY_WIDTH) {
            x = Values.FIELD_WIDTH - 2*Values.ENEMY_WIDTH;
            moveRight = !moveRight;
        }
        if(y < Values.ENEMY_HEIGHT) {
            y = Values.ENEMY_HEIGHT;
            moveDown = !moveDown;
        }
        if(y >= (Values.FIELD_HEIGHT- 2*Values.ENEMY_HEIGHT)-150) {
            y = (Values.FIELD_HEIGHT- 2*Values.ENEMY_HEIGHT)-150;
            moveDown = !moveDown;
        }
    }

    @Override
    public void draw(Graphics g, GameField gameField, BufferedImage bufferedImage) {
        for(int i = 0; i < gameField.gameBase.enemies.toArray().length; i++) {
            Enemy enemy = gameField.gameBase.enemies.get(i);
            if(enemy.isVisiable()) {
                try{
                    g.drawImage(bufferedImage, enemy.x, enemy.y, gameField);
                } catch (Exception EXC) {}
            } else {
                enemy.die();
                gameField.gameBase.enemies.remove(enemy);
            }
        }
    }

    @Override
    public void update(GameField gameField) {
        for(Enemy enemy : gameField.gameBase.enemies) {
            enemy.move();
            if((enemy.x == Values.FIELD_HEIGHT-gameField.gameBase.player.y-enemy.y && moveRight == false) || (enemy.x == gameField.gameBase.player.x) || (Values.FIELD_WIDTH-gameField.gameBase.player.x-enemy.x == Values.FIELD_HEIGHT-gameField.gameBase.player.y-enemy.y && moveRight == true)) {
                EnemyGunshot enemyGunshot = new EnemyGunshot(enemy.x, enemy.y, 3);
                gameField.gameBase.enemyGunshots.add(enemyGunshot);
            }
        }
    }

    public Enemy(int x, int y, int speed) {
        super(x, y, speed);
    }
}
