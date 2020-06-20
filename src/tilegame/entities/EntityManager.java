package tilegame.entities;

import tilegame.Handler;
import tilegame.entities.statics.StaticEntityHover;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private Handler handler;
    private Player player;
    private StaticEntityHover seHover;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();
        addEntity(player);
    }


    // TICK RENDER

    public void tick(){
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.tick();
        }
        seHover.tick();

    }

    public void render(Graphics g){
        for(Entity e : entities){
            e.render(g);
        }
        seHover.render(g);
    }

    // METODY

    public void addEntity(Entity e){
        entities.add(e);
    }

    // szukanie danej entity po kordach
    public Entity getEntity(int xCord, int yCord){
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            if(xCord == e.x && yCord == e.y) return e;
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
