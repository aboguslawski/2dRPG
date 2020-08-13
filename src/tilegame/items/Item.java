package tilegame.items;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {

    // tablica wszystkich przedmiotow dostepnych w grze
    public static Item[] items = new Item[512];

    // przedmioty
    public static Item coinItem = new Item(Assets.coins, "coins", 0);
    public static Item mushroomItem = new Item(Assets.mushroom, "mushroom", 1);

    // szerokosc i wysokosc przedmiotu
    // picked_up : jesli ilosc itemow w danym miejscu osiagnie -1, to znaczy ze zostal podniesiony i go tam juz nie ma
    public static final int ITEMWIDTH = Tile.TILE_WIDTH, ITEMHEIGHT = Tile.TILE_HEIGHT;

    protected Handler handler;
    protected BufferedImage texture; // tekstura przedmiotu
    protected String name; // nazwa przedmiotu
    protected final int id; // id przedmiotu

    protected Rectangle bounds; // granice przedmiotu lezacego na ziemi

    protected int x, y, count;
    protected boolean pickedUp = false;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;

        bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);

        items[id] = this;

    }

    public void tick() {

        // podnoszenie przedmiotu
        if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)
        && handler.getKeyManager().f){
            pickedUp = true;
            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
        }
    }

    public void render(Graphics g, int x, int y) {
        // jesli przedmiot nie jest podlaczany pod zaden handler, nie renderuj go
        if (handler == null)
            return;
        render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
    }

    public void render(Graphics g) {
        g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), ITEMWIDTH, ITEMHEIGHT, null);
    }

    // testowa funkcja
    public Item createNew(int count) {
        Item i = new Item(texture, name, id);
        i.setPickedUp(true);
        i.setCount(count);

        return i;
    }

    // tworzenie nowego przedmiotu na wskazanej pozycji
    public Item createNew(int x, int y) {
        Item i = new Item(texture, name, id);
        i.setPosition(x, y);

        return i;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    // GETTERS SETTERS


    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
