package tilegame.entities.player;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.gfx.MobAnimation;
import tilegame.tiles.Tile;

import java.awt.*;

public class SwimmingState extends UnlockedState {

    public SwimmingState(Handler handler, Player player) {
        this.player = player;
        this.handler = handler;
        walkSpeed = 1.5f;
        runSpeed = 2.4f;

        idle = new MobAnimation("/res/textures/playerSwim.png", 500, 4, 64, 128);
        walking = new MobAnimation("/res/textures/playerSwim.png", 250, 4, 64, 128);
        running = new MobAnimation("/res/textures/playerSwim.png", 150, 4, 64, 128);
        animation = idle;
    }

    @Override
    public void tick() {
        super.tick();
        getInput();
        System.out.println("swimming");
        playerTile = handler.getWorld().getTile((int) player.getX() / Tile.TILE_WIDTH, (int) player.getY() / Tile.TILE_HEIGHT);
        player.getInteractionHover().tick();
        if (playerTile.getDepth() <= 1)
            player.setPstate(player.walkingState);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        player.getInteractionHover().render(g);
    }

    @Override
    public void postRender(Graphics g) {

    }
}
