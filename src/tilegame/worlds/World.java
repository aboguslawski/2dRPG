package tilegame.worlds;

import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.EntityManager;
import tilegame.entities.player.Player;
import tilegame.gfx.Assets;
import tilegame.gfx.DayNightCycle;
import tilegame.items.ItemManager;
import tilegame.tiles.Tile;
import tilegame.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World {

    private Handler handler;
    private int width, height; // szerokosc i wysokosc w tilesach
    private int spawnX, spawnY; // respawn gracza
    private int[][] worldTiles; // wszystkie tilesy w danym swiecie
    private Player player; // gracz
    private boolean surface; // czy powierzchnia
    private DayNightCycle cycle; // cykl dnia i nocy
    // Entities
    private EntityManager entityManager;
    // Items
    private ItemManager itemManager;

    public World(Handler handler, String path, boolean surface, DayNightCycle cycle) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 100, 100));
        itemManager = new ItemManager(handler);
//        player = entityManager.getPlayer();

        // ustaw wskaznik statycznych obiektow na graczu w zasiegu 50 pixeli
//        player.setInteractionHover(new InteractionHover(handler, player));


        loadWorld(path);

        // ustawienie kordow spawnu gracza
        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);

        this.surface = surface;
        this.cycle = cycle;
    }

    public void tick() {
        itemManager.tick();
        entityManager.tick();
    }

    public void render(Graphics g) {

        // ograniczenie renderu do zasiegu kamery
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + Display.SCREEN_WIDTH) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + Display.SCREEN_HEIGHT) / Tile.TILE_HEIGHT + 1);

        // renderowanie tilesow w odpowiednich miejscach
        for (int j = yStart; j < yEnd; j++) {
            for (int i = xStart; i < xEnd; i++) {
                getTile(i, j).render(g, (int) (i * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int) (j * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }

        // maska
        renderMask(g, Assets.bkgdMask);


        itemManager.render(g);
        //entities
        entityManager.render(g);

    }

    // zwraca tile na danych kordach i j
    public Tile getTile(int i, int j) {

        // jesli wyjdziesz poza mape, tak jakby jestes na podlodze
        if (i < 0 || j < 0 || i >= width || j >= height) {
            return Tile.floorTile;
        }

        Tile tile = Tile.tiles[worldTiles[i][j]];

        // jesli nie znaleziono zadnego tile, to zwraca sciane
        if (tile == null) return Tile.wallTile;

        // zwroc aktualny tile
        return tile;
    }

    // ladowanie swiata z pliku
    private void loadWorld(String path) {

        //wczytanie pliku
        String file = Utils.loadFileAsString(path);

        //tablica numerow id z pliku
        String[] tokens = file.split("\\s+");

        // pierwszy numer w pliku to szerokosc -> konwert ze stringa do inta
        width = Utils.parseInt(tokens[0]);

        // drugi numer w pliku to wysokosc swiata
        height = Utils.parseInt(tokens[1]);

        // kordy spawnu gracza
        spawnX = Utils.parseInt(tokens[2]) * Tile.TILE_WIDTH;
        spawnY = Utils.parseInt(tokens[3]) * Tile.TILE_HEIGHT;

        // tablica z id tilesow
        worldTiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                //przerzucenie z tokens[] do worldTiles[][]
                worldTiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }

    }

    // rysowanie maski na calym ekranie
    private void renderMask(Graphics g, BufferedImage img) {
        g.drawImage(img, (int) (0 - handler.getGameCamera().getxOffset()), (int) (0 - handler.getGameCamera().getyOffset()), null);
    }

    public boolean isSurface() {
        return this.surface;
    }


    // GETTERS SETTERS


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DayNightCycle getCycle() {
        return cycle;
    }

    public void setCycle(DayNightCycle cycle) {
        this.cycle = cycle;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
}