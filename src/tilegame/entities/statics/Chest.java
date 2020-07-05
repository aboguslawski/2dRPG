package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// chest to klasa kazdego obiektu z ktorego zbierany jest loot
// czyli nie tylko skrzynia ale obiekty takie jak zwloki beda rozszerzaly ta klase

public class Chest extends StaticEntity {

    // tekstura, w przyszlosci animacja
    protected BufferedImage texture;

    // przedmioty znajdujace sie w skrzyni
    protected ArrayList<Item> content;

    public Chest(Handler handler, float x, float y, int width, int height, BufferedImage texture) {
        super(handler, x, y, width, height);
        this.texture = texture;

        // z kazda skrzynia mozna wejsc w interakcje
        interaction = true;

        // inicjalizacja
        content = new ArrayList<>();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {

        // renderowanie samego obrazu skrzyni
        g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void interact() {

        // jesli gracz aktualnie nie przeglada innej skrzyni, ustaw go na przegladanie tej
        if (handler.getWorld().getEntityManager().getPlayer().getLooting() == null)
            handler.getWorld().getEntityManager().getPlayer().setLooting(this);
    }

    @Override
    public void die() {
    }

    // umieszczanie przedmiotow w skrzyni
    public void addContent(Item item) {
        content.add(item);
    }

    public void removeContent(Item item) {
        content.remove(item);
    }


    // GETTERS SETTERS


    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public ArrayList<Item> getContent() {
        return content;
    }

    public void setContent(ArrayList<Item> content) {
        this.content = content;
    }


}
