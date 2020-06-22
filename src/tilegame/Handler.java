package tilegame;

import tilegame.display.Display;
import tilegame.gfx.GameCamera;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.worlds.World;

// handler ogarnia temat
public class Handler {

    private Game game;
    private World world;

    public Handler(Game game) {
        this.game = game;
    }

    // METHODS

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }

    public int getWidth() {
        return Display.SCREEN_WIDTH;
    }

    public int getHeight() {
        return Display.SCREEN_HEIGHT;
    }

    // GETTERS SETTERS

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
