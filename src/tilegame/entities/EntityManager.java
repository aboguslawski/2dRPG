package tilegame.entities;

import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.mobs.hostile.Enemy1;
import tilegame.entities.player.Player;
import tilegame.entities.statics.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {

    private Handler handler;
    private Player player;

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
        Iterator<Entity> it = entities.iterator();

        // tickuj kazda entity na liscie
        while(it.hasNext()) {
            Entity e = it.next();
            e.tick();
            if(!e.isActive())
                it.remove();
        }

        // sortuj liste tak, zeby kazdy renderowal sie w odpowiedniej kolejnosci
        entities.sort(renderSorter);

    }

    public void render(Graphics g) {

        int xStart2 = Math.max(0, (int) (player.getX() - handler.getGameCamera().getxOffset() - 500));
        int xEnd2 = (int) (player.getX() - handler.getGameCamera().getxOffset() + 500);
        int yStart2 = Math.max(0, (int) (player.getY() - handler.getGameCamera().getyOffset() - 500));
        int yEnd2 = (int) (player.getY() - handler.getGameCamera().getyOffset() + 500);

//        g.setColor(Color.black);
//        g.fillRect(xStart2, yStart2, xEnd2 - xStart2 ,yEnd2 - yStart2);


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
        addEntity(new TorchStand(handler, 520, 520));
        addEntity(new House1(handler, 1170, 80));
        addEntity(new Lamp1(handler, 950, 450));
        addEntity(new Lamp1(handler, 690, 900));
        addEntity(new Enemy1(handler, 300,400));
        addEntity(new Enemy1(handler, 400, 300));
        addEntity(new Enemy1(handler, 500, 100));
        addEntity(new Enemy1(handler, 140,220));
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
