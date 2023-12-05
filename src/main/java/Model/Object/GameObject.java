package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {
    private float x, y;
    private String name;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;

    public GameObject(String name,float x, float y, TextureRegion texture){
        this.name = name;
        this.x = x;
        this.y = y;
        this.texture = texture;

        remove = false;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture,x,y,OBJECT_HEIGHT,OBJECT_WIDTH);
    }

    public Rectangle getArea(){
        return  new Rectangle(x,y,OBJECT_WIDTH,OBJECT_HEIGHT);
    }

    public boolean collide(Player player){
        Rectangle rect = getArea();
        Rectangle rect2 = player.getArea();

        return rect2.overlaps(rect);
    }

    public boolean isRemove(){return remove;}
    public void setRemove(boolean b){remove = b;}
}
