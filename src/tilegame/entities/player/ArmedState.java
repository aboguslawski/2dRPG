package tilegame.entities.player;

import tilegame.Handler;
import tilegame.gfx.MobAnimation;

import java.awt.*;

public class ArmedState extends NotSwimmingState {

    public ArmedState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        walkSpeed = 2.7f;
        runSpeed = 4.0f;

        idle = new MobAnimation("/res/textures/playerSwordIdle.png", 250, 4, 64, 128);
        walking = new MobAnimation("/res/textures/playerSword.png", 250, 4, 64, 128);
        running = new MobAnimation("/res/textures/playerSword.png", 150, 4, 64, 128);
        animation = idle;
    }

    @Override
    public void tick() {
        super.tick();
        getInput();
        player.getAttackHover().tick();

    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        player.getAttackHover().render(g);
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    protected void getInput() {
        super.getInput();
        if (handler.getKeyManager().n1) player.setPstate(player.walkingState);
        if (handler.getKeyManager().q) player.getAttackHover().prevEntity();
        if (handler.getKeyManager().e) player.getAttackHover().nextEntity();
        if (handler.getKeyManager().space) {
            try {
                focus(player.getAttackHover().getHovered());
            } catch (NullPointerException ignore) {
            }
            if(handler.getKeyManager().w) {
                player.attackState.resetTimer();
                player.setPstate(player.attackState);
            }
        }
    }
}
