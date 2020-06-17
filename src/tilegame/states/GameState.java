package tilegame.states;

import tilegame.Game;
import tilegame.Handler;
import tilegame.entities.Player;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.gfx.DayNightCycle;
import tilegame.worlds.World;

import java.awt.*;

public class GameState extends State {

    private Player player;
    private World world;

    public GameState(Handler handler){
        super(handler);
        Animation dncAnimation = new Animation(700,Assets.dayNightCycle);
        DayNightCycle cycle = new DayNightCycle(dncAnimation);
        world = new World(handler,"src/res/worlds/world1.txt", true, cycle);
        handler.setWorld(world);
        player = new Player(handler,world.getSpawnX(), world.getSpawnY(),10);

    }

    @Override
    public void tick() {
        world.tick();
        player.tick();
        world.getCycle().tick();

    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
        world.getCycle().render(g);
    }
}
