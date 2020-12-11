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
    public Player player;
    public ArrayList<PlayerGunshot> playerGunshots;
    public ArrayList<EnemyGunshot> enemyGunshots;
    public ArrayList<Enemy> enemies;

    public void iterate() {
        if(enemiesCount == 0) { inGame = false; }
        update();
        repaint();
    }

    private  void update() {
        enemiesCount = enemies.toArray().length;
        player.update(this);
        if(playerGunshots.toArray().length != 0) {
            playerGunshots.get(0).update(this);
        }
        if(enemyGunshots.toArray().length != 0) {
            enemyGunshots.get(0).update(this);
        }
        if(enemies.toArray().length != 0) {
            enemies.get(0).update(this);
        }
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE) {
            if(inGame) {
                PlayerGunshot shot = new PlayerGunshot(player.getX()+Values.PLAYER_WIDTH/2, player.getY(), 5 );
                playerGunshots.add(shot);
            }
        }
        if(key==KeyEvent.VK_S) {
            try{
                FileOutputStream fos = new FileOutputStream("base.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(player);
                oos.close();
            } catch(Exception EXC) {
                EXC.printStackTrace();
            }
            try{
                FileOutputStream fos = new FileOutputStream("eni.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(enemies);
                oos.close();
            } catch(Exception EXC) {
                EXC.printStackTrace();
            }
            try{
                FileOutputStream fos = new FileOutputStream("pg.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(playerGunshots);
                oos.close();
            } catch(Exception EXC) {
                EXC.printStackTrace();
            }
            try{
                FileOutputStream fos = new FileOutputStream("eg.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(enemyGunshots);
                oos.close();
            } catch(Exception EXC) {
                EXC.printStackTrace();
            }
        }
        if(key==KeyEvent.VK_L) {
            try {
                FileInputStream fis = new FileInputStream("base.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                player = (Player) ois.readObject();
                ois.close();
            } catch (Exception EXC) {
                EXC.printStackTrace();
            }
            try {
                FileInputStream fis = new FileInputStream("eni.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                enemies = (ArrayList<Enemy>) ois.readObject();
                ois.close();
            } catch (Exception EXC) {
                EXC.printStackTrace();
            }
            try {
                FileInputStream fis = new FileInputStream("pg.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                playerGunshots = (ArrayList<PlayerGunshot>) ois.readObject();
                ois.close();
            } catch (Exception EXC) {
                EXC.printStackTrace();
            }
            try {
                FileInputStream fis = new FileInputStream("eg.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                enemyGunshots = (ArrayList<EnemyGunshot>) ois.readObject();
                ois.close();
            } catch (Exception EXC) {
                EXC.printStackTrace();
            }
        }
    }

    private void draw(Graphics g) {
        player.draw(g, this, pl);
        if(playerGunshots.toArray().length != 0) {
            playerGunshots.get(0).draw(g, this, pGunshot);
        }
        if(enemies.toArray().length != 0) {
            enemies.get(0).draw(g, this, en);
        }
        if(enemyGunshots.toArray().length != 0) {
            enemyGunshots.get(0).draw(g, this, eGunshot);
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
        playerGunshots = new ArrayList<PlayerGunshot>();
        enemyGunshots = new ArrayList<EnemyGunshot>();
        enemies = new ArrayList<Enemy>();
        enemiesCount = 5*10;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                Enemy enemy = new Enemy(150+70*j, 100+70*i, 1);
                int tmp = (i+1)*(j+1)%3;
                switch (tmp) {
                    case 0:
                        enemy.setMoveRight(true);
                        enemy.setMoveDown(false);
                        break;
                    case 1:
                        enemy.setMoveRight(false);
                        enemy.setMoveDown(true);
                        break;
                    case 2:
                        enemy.setMoveRight(true);
                        enemy.setMoveDown(true);
                        break;
                }
                enemies.add(enemy);
            }
        }
        player = new Player(1, 1,2);
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