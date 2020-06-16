package tilegame.gfx;

import tilegame.Game;
import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.Entity;
import tilegame.tiles.Tile;

public class GameCamera {
    private float xOffset, yOffset; // offsety okreslaja o ile kamera ma przesunac cala mape wzgledem gracza
    private Handler handler;

    public GameCamera(Handler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    // kamera w rogach nie pokazuje pustych czesci mapy
    public void checkBlankSpace(){
        if(xOffset < 0){
            xOffset = 0;
        } else if(xOffset > handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth()){
            xOffset = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
        }

        if(yOffset < 0){
            yOffset = 0;
        } else if(yOffset > handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()){
            yOffset = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
        }
    }

    // postac na której kamera ma zostac wysrodkowana
    public void centerOnEntity(Entity e){
        xOffset = e.getX() - Display.SCREEN_WIDTH / 2 + e.getWidth() / 2;
        yOffset = e.getY() - Display.SCREEN_HEIGHT/ 2 + e.getHeight() / 2;
        checkBlankSpace();
    }


    // pobiera dwie wartosci i dodaje do offsetow
    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();
    }


    // GETTERS SETTERS

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
