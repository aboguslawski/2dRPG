package tilegame.entities;

import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.statics.*;
import tilegame.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    private Handler handler;
    private Player player;
    private StaticEntityHover seHover;

    // wszystkie obiekty na mapie
    private ArrayList<Entity> entities;

    // najpierw renderowane sa entitiesy ktore sa nizej
    private Comparator<Entity> renderSorter = (a, b) -> {
        if (a.getY() + a.getHeight() - a.bounds.height < b.getY() + b.getHeight() - b.bounds.height)
            return -1; // a jest nad b
        return 1;// jak nie to b jest nad a
    };

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();

        // od razu dodaj gracza do listy obiektow
        addEntity(player);
        init();
    }


    // TICK RENDER

    public void tick() {

        // tickuj kazda entity na liscie
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }

        // sortuj liste tak, zeby kazdy renderowal sie w odpowiedniej kolejnosci
        entities.sort(renderSorter);

        // tickuj wskaznik
        seHover.tick();

    }

    public void render(Graphics g) {

        seHover.render(g);

        // ograniczenie renderu do zasiegu kamery
        int xStart = (int) (handler.getGameCamera().getxOffset() - Display.SCREEN_WIDTH);
        int xEnd = (int) (handler.getGameCamera().getxOffset() + Display.SCREEN_WIDTH * 2);
        int yStart = (int) (handler.getGameCamera().getyOffset() - Display.SCREEN_HEIGHT);
        int yEnd = (int) (handler.getGameCamera().getyOffset() + Display.SCREEN_HEIGHT * 2);

        for (Entity e : entities) {
            if (e.getX() >= xStart && e.getX() <= xEnd
                    && e.getY() >= yStart && e.getY() <= yEnd)
                e.render(g);
        }
    }

    // METODY

    // lista obiektow z ktorymi odpala sie gra
    private void init() {
        addEntity(new TorchStand(handler, 500, 500));
        addEntity(new TorchStand(handler, 530, 510));
        addEntity(new House1(handler, 1170, 80));
        addEntity(new Lamp1(handler, 950, 450));
        addEntity(new Lamp1(handler, 690, 900));
    }

    // dodanie obiektu do listy
    public void addEntity(Entity e) {
        entities.add(e);
    }

    // szukanie danej entity po kordach
    // jesli znaleziono zwraca ten obiekt
    // w przeciwnym wypadku zwraca null
    public Entity getEntity(int xCord, int yCord) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (xCord == e.x && yCord == e.y) return e;
        }
        return null;
    }


    // GETTERS SETTERS

    public StaticEntityHover getSeHover() {
        return seHover;
    }

    public void setSeHover(StaticEntityHover seHover) {
        this.seHover = seHover;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
