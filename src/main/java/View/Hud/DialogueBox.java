package View.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DialogueBox {
    private Table table;
    private Label dialogueLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
    private boolean isVisible;
    private String text;


    public DialogueBox(String message){
        this.text = message;
        table = new Table();



        // Aggiungi il background o l'immagine della finestra di dialogo
        Texture backgroundTexture = new Texture(Gdx.files.internal("NPC/DialogueBox.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(600,200);


        //popolazione table
        table.add(backgroundImage).width(600).height(200);
        table.add(dialogueLabel);

        table.setVisible(false);
        isVisible = false;
    }

    public Table getTable() {
        return table;
    }

    public void show(String text) {
        dialogueLabel.setText(text);
        table.setVisible(true);
        isVisible = true;
    }

    public String getText(){
        return text;
    }

    public void hide() {
        table.setVisible(false);
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setMessage(String message) {
        this.text = message;
    }
}
