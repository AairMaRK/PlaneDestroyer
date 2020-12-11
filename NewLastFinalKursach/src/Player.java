import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Player extends Sprite implements Serializable
{
    private boolean moveLeft;
    private boolean moveRight;

    public Player(int x, int y, int speed) {
        super(x, y, speed);
        moveLeft = false;
        moveRight = false;
        int startX = Values.FIELD_WIDTH / 2 - Values.PLAYER_WIDTH / 2;
        int startY = Values.FIELD_HEIGHT - 100;
        setX(startX);
        setY(startY);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_A) { moveLeft = true; }
        if(key == KeyEvent.VK_D) { moveRight = true; }
    }

    public void  keyReleased(KeyEvent e) {
        moveLeft = false;
        moveRight = false;
    }

    @Override
    public void move() {
        if(moveRight) { x += speed; }
        if(moveLeft) { x -= speed; }
        if(x<Values.PLAYER_WIDTH) {
            x=Values.PLAYER_WIDTH;
        }
        if(x>=Values.FIELD_WIDTH - 2*Values.PLAYER_WIDTH) {
            x=Values.FIELD_WIDTH - 2*Values.PLAYER_WIDTH;
        }
    }

    @Override
    public void draw(Graphics g, GameField gameField, BufferedImage bufferedImage) {
        try {
            g.drawImage(bufferedImage, gameField.gameBase.player.getX(), gameField.gameBase.player.getY(), gameField);
        } catch (Exception EXC) {}
    }

    @Override
    public void update(GameField gameField) {
        gameField.gameBase.player.move();
    }
}
