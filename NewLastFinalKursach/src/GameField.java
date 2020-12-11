import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GameField extends JPanel implements ActionListener
{
    private BufferedImage background;
    private BufferedImage eGunshot;
    private BufferedImage pGunshot;
    private BufferedImage pl;
    private BufferedImage en;
    boolean inGame = true;
    private  Timer timer;
    public int enemiesCount;
    public GameBase gameBase;

    public void iterate() {
        if(enemiesCount == 0) { inGame = false; }
        update();
        repaint();
    }

    private  void update() {
        enemiesCount = gameBase.enemies.toArray().length;
        gameBase.player.update(this);
        if(gameBase.playerGunshots.toArray().length != 0) {
            gameBase.playerGunshots.get(0).update(this);
        }
        if(gameBase.enemyGunshots.toArray().length != 0) {
            gameBase.enemyGunshots.get(0).update(this);
        }
        if(gameBase.enemies.toArray().length != 0) {
            gameBase.enemies.get(0).update(this);
        }
    }

    public void keyReleased(KeyEvent e) {
        gameBase.player.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        gameBase.player.keyPressed(e);
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE) {
            if(inGame) {
                PlayerGunshot shot = new PlayerGunshot(gameBase.player.getX()+Values.PLAYER_WIDTH/2, gameBase.player.getY(), 5 );
                gameBase.playerGunshots.add(shot);
            }
        }
        if(key==KeyEvent.VK_S) {
            try{
                FileOutputStream fos = new FileOutputStream("base.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gameBase);
                oos.close();
            } catch(Exception EXC) {
                EXC.printStackTrace();
            }
        }
        if(key==KeyEvent.VK_L) {
            try {
                FileInputStream fis = new FileInputStream("base.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                gameBase = (GameBase) ois.readObject();
                ois.close();
            } catch (Exception EXC) {
                EXC.printStackTrace();
            }
        }
    }

    private void draw(Graphics g) {
        gameBase.player.draw(g, this, pl);
        if(gameBase.playerGunshots.toArray().length != 0) {
            gameBase.playerGunshots.get(0).draw(g, this, pGunshot);
        }
        if(gameBase.enemies.toArray().length != 0) {
            gameBase.enemies.get(0).draw(g, this, en);
        }
        if(gameBase.enemyGunshots.toArray().length != 0) {
            gameBase.enemyGunshots.get(0).draw(g, this, eGunshot);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        if(inGame) {
            draw(g);
        } else {
            String messsege = "Game Over";
            String win = "Your Win!!!";
            Font small = new Font("Calibri", Font.BOLD, 30);
            FontMetrics metr = getFontMetrics(small);
            g.setColor(Color.WHITE);
            g.setFont(small);
            if(!inGame && enemiesCount == 0) {
                g.drawString(win, Values.FIELD_WIDTH/2-60, Values.FIELD_HEIGHT/2+5);
            } else {
                g.drawString(messsege, Values.FIELD_WIDTH/2-60, Values.FIELD_HEIGHT/2+5);
            }
            if(timer.isRunning()) {
                timer.stop();
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        iterate();
    }

    public GameField() {
        addKeyListener(new GameEventListener(this));
        setFocusable(true);
        setPreferredSize(new Dimension(Values.FIELD_WIDTH, Values.FIELD_HEIGHT));
        enemiesCount = 5*10;
        gameBase = new GameBase();
        try {
            background = ImageGenerator.newImage(Images.BACKGROUND);
            en = ImageGenerator.newImage(Images.ENEMY);
            eGunshot = ImageGenerator.newImage(Images.ENEMY_GUNSHOT);
            pl = ImageGenerator.newImage(Images.PLAYER);
            pGunshot = ImageGenerator.newImage(Images.PLAYER_GUNSHOT);
        } catch (Exception EXC) {}
        timer = new Timer(Values.GAME_SPEED, this);
        timer.start();
    }
}