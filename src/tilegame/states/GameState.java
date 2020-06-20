package tilegame.states;

import tilegame.Game;
import tilegame.Handler;
import tilegame.entities.Player;
import tilegame.entities.statics.TorchStand;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.gfx.DayNightCycle;
import tilegame.worlds.World;

import java.awt.*;

public class GameState extends State {

    private World world;

    public GameState(Handler handler){
        super(handler);
        Animation dncAnimation = new Animation(700,Assets.dayNightCycle);
        DayNightCycle cycle = new DayNightCycle(dncAnimation);
        world = new World(handler,"src/res/worlds/world1.txt", true, cycle);
        handler.setWorld(world);
    }

    @Override
    public void tick() {
        world.tick();

//        world.getCycle().tick();

    }

    @Override
    public void render(Graphics g) {
        world.render(g);
//        world.getCycle().render(g);
    }
}
