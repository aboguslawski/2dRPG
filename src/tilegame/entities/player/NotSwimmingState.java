package tilegame.entities.player;

import tilegame.gfx.ImageLoader;
import tilegame.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class NotSwimmingState extends UnlockedState {

    private BufferedImage inWater = ImageLoader.loadImage("/res/textures/inshallowwater.png");

    @Override
    public void tick() {
        super.tick();
        playerTile = handler.getWorld().getTile((int) player.getX() / Tile.TILE_WIDTH, (int) player.getY() / Tile.TILE_HEIGHT);
//        System.out.println("tile " + player.getX() / Tile.TILE_WIDTH + "/" + player.getY() / Tile.TILE_HEIGHT + " - " + playerTile.getId() + " depth: " + playerTile.getDepth());

        if(playerTile.getDepth() > 1){
            System.out.println("will swim");
            player.setPstate(player.swimmingState);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        playerTile = handler.getWorld().getTile((int) player.getX() / Tile.TILE_WIDTH, (int) player.getY() / Tile.TILE_HEIGHT);
        if (playerTile.getDepth() == 1){
            drawPlayer(g, inWater, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        }

    }
}
