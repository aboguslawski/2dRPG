package tilegame.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    // STATIC STUFF

    public static Tile[] tiles = new Tile[128]; // zbior kazdej odmiany tilesow
    public static Tile floorTile = new FloorTile(0);
    public static Tile wallTile = new WallTile(1);
    public static Tile shallowWaterTile = new ShallowWaterTile(2);
    public static Tile mediumWaterTile = new MediumWaterTile(3);
    public static Tile deepWaterTile = new DeepWaterTile(4);

    // CLASS

        //szerokosc i wysokosc kazdej tiles
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

        //parametry kazdej tile
    protected BufferedImage texture;
    protected final int id;

    // konstruktor
    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void tick(){

    }

    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    // czy da sie na niej chodzic
    public boolean isSolid(){
        return false;
    }

    // glebokosc
    public int getDepth(){
        return 0;
    }

    public int getId(){
        return id;
    }
}
