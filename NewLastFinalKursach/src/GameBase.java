import java.io.Serializable;
import java.util.ArrayList;

public class GameBase implements Serializable
{
    public Player player;
    public ArrayList<PlayerGunshot> playerGunshots;
    public ArrayList<EnemyGunshot> enemyGunshots;
    public ArrayList<Enemy> enemies;

    public GameBase() {
        playerGunshots = new ArrayList<PlayerGunshot>();
        enemyGunshots = new ArrayList<EnemyGunshot>();
        enemies = new ArrayList<Enemy>();
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
    }
}
