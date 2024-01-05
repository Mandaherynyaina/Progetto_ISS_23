package Model.NPC;

import Model.Interactable;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DaveNPC implements NPC , Interactable {
    private Vector2 position;
    private TextureRegion texture;
    private Texture interactTexture;
    private NPCObserver observer;
    private boolean hasTalked = false;

    public DaveNPC(Vector2 position){
        this.position = position;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("NPC/Dave/Dave.png")));
        interactTexture = new Texture(Gdx.files.internal("map/Interaction/Interaction.png"));
    }
    @Override
    public void interact(Player player) {
        talk();
    }

    @Override
    public void addObserver(NPCObserver observer) {
        this.observer = observer;
        System.out.println("Added observer for Dave : " + observer);
    }

    @Override
    public void update(float delta) {

    }

    private void notifyObservers(String message) {
        observer.onNPCTalk(message);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x+ texture.getRegionHeight()/2,position.y+texture.getRegionHeight()/2);
    }

    @Override
    public boolean isCollision(float x, float y, float width, float height) {
        return false;
    }

    @Override
    public void draw(SpriteBatch batch, Player player) {
        batch.draw(texture,position.x,position.y,texture.getRegionWidth()*3,texture.getRegionHeight()*3);

        if (isPlayerInRange(player.getHitBox().x+ player.getHitBox().width/2,
                player.getHitBox().y +player.getHitBox().height/2,
                120)) {
            float messageX = position.x + 10;
            float messageY = position.y + texture.getRegionHeight()*2;

            batch.draw(interactTexture, messageX, messageY, 96, 96);
        }else if (!hasTalked) {
            observer.onNPCFinishedTalk();
            hasTalked = true; // Imposta la variabile a true dopo la chiamata
        }

        System.out.println("Has talked : "+ hasTalked);

    }

    public boolean isPlayerInRange(float playerX, float playerY, float range) {
        float distance = new Vector2(playerX, playerY).dst(position.x + texture.getRegionHeight()/2, position.y + texture.getRegionHeight()/2);
        return distance <= range;
    }

    @Override
    public void talk() {
        String message = "Lorem ipsum dolor";
        hasTalked = false;
        notifyObservers("SONO DAVE : "+message);
    }
}
