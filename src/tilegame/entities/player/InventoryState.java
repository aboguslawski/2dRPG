package tilegame.entities.player;

import tilegame.Handler;
import tilegame.gfx.MobAnimation;

import java.awt.*;

public class InventoryState extends LockedState {

    private boolean active;

    public InventoryState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        active = false;
        animation = new MobAnimation("/res/textures/playerIdle.png", 250, 4, 64, 128);
    }

    @Override
    public void tick() {
        player.setxMove(0);
        player.setyMove(0);
        System.out.println("inv state");
        getInput();
        player.inventory.tick();
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void postRender(Graphics g) {
        player.inventory.render(g);
    }

    @Override
    protected void getInput() {
        if(handler.getKeyManager().i){
            player.setPstate(player.walkingState);
        }
    }
}
