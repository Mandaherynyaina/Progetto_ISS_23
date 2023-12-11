package Model.Enemies;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;
    private final EnemyAnimationManager animationManager;

    public EnemyManager(){
        enemies = new ArrayList<>();
        animationManager = new EnemyAnimationManager();
    }

    public void initializeEnemies(){
        // Add enemies to the EnemyManager
        // Offset from tile x = -20 , y = -10
        enemies.add(new Enemy(100000, 10, 172, 1142));
        enemies.add(new Enemy(100000, 10, 492 , 1142));
        enemies.add(new Enemy(100000, 10, 300, 886));
        enemies.add(new Enemy(100000, 10, 1452, 374));
        enemies.add(new Enemy(100000, 10, 1260, 246));
        enemies.add(new Enemy(100000, 10, 1580, 118));
    }

    public void update( float delta){
        for(Enemy enemy : enemies){
            enemy.update(delta);
        }
        animationManager.update(delta);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
