package View;

import Controller.PlayerInputManager;
import Model.Enemies.Enemy;
import Model.Enemies.EnemyManager;
import Model.MapModel;
import Model.Object.GameObject;
import Model.Object.ObjectManager;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.lwjgl.opengl.GL20;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private Player player;
    private final PlayerInputManager playerInputManager;
    private MapModel mapModel;
    private EnemyManager enemyManager;

    private ShapeRenderer shapeRenderer;
    private ObjectManager objects;
    private GameObject gameObject;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.ISTANCE.getScreenWidth()/2,Boot.ISTANCE.getScreenHeight()/2,0 ));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.playerInputManager = new PlayerInputManager(player);
        this.mapModel = new MapModel();
        this.objects = new ObjectManager();
        this.enemyManager = new EnemyManager();
    }

    @Override
    public void show(){
        player = new Player();
        shapeRenderer = new ShapeRenderer();
        enemyManager.initializeEnemies();

        //Set the player's enemies
        player.setEnemies(enemyManager.getEnemies());

    }
    public void update(float delta){
        world.step(1/60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        enemyManager.update(delta);
    }

    @Override
    public void render(float delta){
        update(delta);
        player.update(delta);
        playerInputManager.update(delta);
        camera.position.set(player.getPlayerX() + player.getPLAYER_WIDTH() / 2 , player.getPlayerY() + player.getPLAYER_HEIGHT() / 2 , 0);
        camera.update();

        //clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapModel.render(batch,camera);
        objects.draw(batch);

        for (Enemy enemy : enemyManager.getEnemies()){
            batch.draw(enemy.getCurrentFrame(), enemy.getEnemyX(), enemy.getEnemyY(), enemy.getEnemyWidth(), enemy.getEnemyHeight());
        }
        batch.draw(player.getCurrentFrame(),player.getPlayerX(), player.getPlayerY(), player.getPLAYER_WIDTH(), player.getPLAYER_HEIGHT());
        batch.end();

        //DEBUG
        //renderDebug();
        //renderPlayerCollisionDebug();
    }

    private void renderPlayerCollisionDebug() {
        // Set the ShapeRenderer's projection matrix to the camera's combined matrix
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Begin drawing shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Set the color for the debug rendering (choose a color you prefer)
        shapeRenderer.setColor(Color.MAGENTA);

        // Draw a rectangle around the player's collision box
        shapeRenderer.rect(player.getPlayerX() + (player.getPLAYER_WIDTH()/4) , player.getPlayerY() , player.getPLAYER_WIDTH()/2+15, player.getPLAYER_HEIGHT()/2+15);

        // End drawing shapes
        shapeRenderer.end();

    }
    private void renderDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Iterate through collision objects and draw rectangles
        for (MapObject object : mapModel.getScaledCollisionObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
            // Add additional checks for other object types if needed
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }
}
