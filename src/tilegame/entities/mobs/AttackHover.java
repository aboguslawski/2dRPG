package tilegame.entities.mobs;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.Player;
import tilegame.gfx.Assets;

import java.awt.*;
import java.util.ArrayList;

public class AttackHover {

    private Handler handler;

    private Player player;

    private int pixelRange;

    private boolean on;

    private ArrayList<Entity> attackableEntitiesInRange;

    private Entity hovered;

    // rzeczy do timowania co ile ma sprawdzac obecnosc obiektu
    private int speed, j; // j - indeks w liscie staticEntitiesInRange
    private long timer, lastTime;
    // aktualnie 250ms

    public AttackHover(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        this.pixelRange = 500;
        this.on = false;
        this.speed = 250;
        this.j = 0;
        attackableEntitiesInRange = new ArrayList<>();
        this.hovered = null;
        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer >= speed)
            attackableEntitiesInRange = checkForAttackableEntities();

        if(!attackableEntitiesInRange.isEmpty()){
            hovered = attackableEntitiesInRange.get(j % attackableEntitiesInRange.size());
        }
    }

    public void render(Graphics g){
        if(!attackableEntitiesInRange.isEmpty()){
            g.drawImage(Assets.attackHover, (int) (hovered.getX() - handler.getGameCamera().getxOffset()),
                    (int) (hovered.getY() + hovered.getHeight() - handler.getGameCamera().getyOffset()), null);
        }
    }

    private ArrayList<Entity> checkForAttackableEntities(){
        ArrayList<Entity> entities = new ArrayList<>();

        // max (0,x) zapobiega sprawdzaniu poza granicami mapy
        int xStart = Math.max(0, (int) player.getX() - pixelRange);
        int xEnd = (int) player.getX() + pixelRange;
        int yStart = Math.max(0, (int) player.getY() - pixelRange);
        int yEnd = (int) player.getY() + pixelRange;

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {

                // sprawdza czy na danych kordach (i, j) znajduje sie jakis obiekt
                // w przypadku gdy znajdzie obiekt zwraca go
                // w przeciwnym przypadku zwraca null
                Entity e = handler.getWorld().getEntityManager().getEntity(i, j);

                // jesli zwrocono obiekt niebedacy nullem i mozna wejsc z nim w interakcje
                // dodaj go do zwracanej listy
                if (e != null && e.isAttackable()) {
                    entities.add(e);
                }
            }
        }
        return entities;
    }

    // nastepny indeks
    public void nextEntity() {
        j++;
    }

    // GETTERS SETTERS

    public Entity getHovered() {
        return hovered;
    }

}
