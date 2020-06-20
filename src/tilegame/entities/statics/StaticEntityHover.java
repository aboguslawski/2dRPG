package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.Player;
import tilegame.gfx.Assets;

import java.awt.*;
import java.util.ArrayList;

public class StaticEntityHover {

    private Handler handler;
    private Entity player;
    private int pixelRange;
    private boolean on;
    private int i, j;
    private ArrayList<Entity> entitiesInRange;
    private Entity hovered;

    public StaticEntityHover(Handler handler, Entity player, int pixelRange){
        this.handler = handler;
        this.player = player;
        this.pixelRange = pixelRange;
        this.on = false;
        this.i = 0;
        this.j = 0;
        entitiesInRange = new ArrayList<>();
    }

    public void tick(){
        i++;
        if(i%30 == 0)
        entitiesInRange = checkForStaticEntities();
        if(!entitiesInRange.isEmpty()) {
            hovered = entitiesInRange.get(j % entitiesInRange.size());
        }

    }

    public void render(Graphics g){
        for(int i = 0; i < entitiesInRange.size(); i++){
//            Entity e = entitiesInRange.get(i);
            g.drawImage(Assets.hover, (int)(hovered.getX() - handler.getGameCamera().getxOffset()),
                    (int)(hovered.getY() + hovered.getHeight() - handler.getGameCamera().getyOffset()),null);
        }
    }

    private ArrayList<Entity> checkForStaticEntities(){
        ArrayList<Entity> staticEntities = new ArrayList<Entity>();

        int xStart = Math.max(0,(int)player.getX() - pixelRange);
        int xEnd = Math.max(0,(int)player.getX() + pixelRange);
        int yStart = (int)player.getY() - pixelRange;
        int yEnd = (int)player.getY() + pixelRange;

        for(int i = xStart; i <= xEnd; i++){
            for(int j = yStart; j <= yEnd; j++){
                Entity e = handler.getWorld().getEntityManager().getEntity(i,j);
                if(e != null){
                    System.out.println("mam");
                    staticEntities.add(e);
                }
            }
        }
        return staticEntities;
    }

    public void nextEntity(){
        j++;
    }

}
