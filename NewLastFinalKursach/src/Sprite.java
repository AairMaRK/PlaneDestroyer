import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Sprite implements Serializable
{
    protected int x;
    protected int y;
    protected int speed;

    private boolean dead = false;

    public abstract void move();
    public abstract void draw(Graphics g, GameField gameField, BufferedImage bufferedImage);
    public abstract void update(GameField gameField);

    public void setX(int x) { this.x = x; }
    public int getX() { return x; }

    public void setY(int y) { this.y = y; }
    public int getY() { return y; }

    public boolean isDead() { return dead; }
    public void die() { dead = true; }

    public Sprite(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
