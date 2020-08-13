package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.gfx.MobAnimation;
import tilegame.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PlayerState {

    protected Handler handler;
    protected Player player;
    protected MobAnimation animation;
    protected PlayerState state;
    protected float walkSpeed, runSpeed;
    protected Tile playerTile;

    public void tick(){
        try{
            animation.tick();
        } catch (NullPointerException ignored){

        }
    }

    protected abstract void getInput();

    // renderuj aktualna animacje dla aktualnego stanu
    public void render(Graphics g){
        drawPlayer(g, getAnimationFrame(animation),
                player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    public abstract void postRender(Graphics g);

    // funkcja pomagajaca do renderowania
    protected void drawPlayer(Graphics g, BufferedImage img, float x, float y, int width, int height) {
        g.drawImage(img, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    // klatka animacji
    protected BufferedImage getAnimationFrame(MobAnimation animation) {
        switch (player.getDirection()) {
            case "W":
                return animation.getW().getCurrentFrame();
            case "E":
                return animation.getE().getCurrentFrame();
            case "N":
                return animation.getN().getCurrentFrame();
            default:
                return animation.getS().getCurrentFrame();
        }
    }

    protected void focus(Entity e){
        float px = player.getX() + player.getWidth() /2f;
        float py = player.getY() + player.getHeight() /2f;
        float ex = e.getX() + e.getWidth() /2f;
        float ey = e.getY() + e.getHeight() /2f;

        float xDiff = Math.abs(px - ex);
        float yDiff = Math.abs(py - ey);

        if(xDiff >= yDiff){
            if(px >= ex) player.setDirection("W");
            else player.setDirection("E");
        }
        else{
            if(py >= ey) player.setDirection("S");
            else player.setDirection("N");
        }

    }

    protected void moveTowards(Entity e, float speed){
        float px = player.getX() + player.getWidth() /2f;
        float py = player.getY() + player.getHeight() /2f;
        float ex = e.getX() + e.getWidth() /2f;
        float ey = e.getY() + e.getHeight() /2f;

        float xDiff = px - ex;
        float yDiff = py - ey;
        if(xDiff > 0) player.setxMove(-speed);
        if(xDiff < 0) player.setxMove(speed);
        if(yDiff > 0) player.setyMove(-speed);
        if(yDiff < 0) player.setyMove(speed);

    }

}
