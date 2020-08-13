package tilegame.entities.player;

import tilegame.Handler;
import tilegame.gfx.MobAnimation;

import java.awt.*;

public class WalkingState extends NotSwimmingState {


    private ArmedState arm;

    public WalkingState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        walkSpeed = 3.0f;
        runSpeed = 4.4f;
arm = new ArmedState(handler, player);

        idle = new MobAnimation("/res/textures/playerIdle.png", 250, 4, 64, 128);
        walking = new MobAnimation("/res/textures/playerWalk.png", 250, 4, 64, 128);
        running = new MobAnimation("/res/textures/playerWalk.png", 150, 4, 64, 128);
        animation = idle;
    }

    @Override
    public void tick() {
        super.tick();
        getInput();
        player.getInteractionHover().tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        player.getInteractionHover().render(g);
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    protected void getInput() {
        super.getInput();

        if(handler.getKeyManager().n1) player.setPstate(player.armedState);
    }
}
