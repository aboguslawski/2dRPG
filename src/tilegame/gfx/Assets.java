package tilegame.gfx;

import tilegame.entities.Player;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage floor, wall, shallowWater, mediumWater, deepWater;
    public static BufferedImage player, playerSword;

    public static BufferedImage[] player_down, player_up, player_left, player_right;

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    public static void init(){
        Spritesheet spritesheet = new Spritesheet(ImageLoader.loadImage("/res/textures/Spritesheet.png"));
        Spritesheet playerWalkSheet = new Spritesheet(ImageLoader.loadImage("/res/textures/playerWalk.png"));

        floor = spritesheet.crop(0,0,TILE_WIDTH, TILE_HEIGHT);
        wall = spritesheet.crop(TILE_WIDTH,0,TILE_WIDTH, TILE_HEIGHT);
        shallowWater = spritesheet.crop(TILE_WIDTH*2, 0, TILE_WIDTH, TILE_HEIGHT);
        mediumWater = spritesheet.crop(TILE_WIDTH*3, 0, TILE_WIDTH, TILE_HEIGHT);
        deepWater = spritesheet.crop(TILE_WIDTH*4, 0, TILE_WIDTH, TILE_HEIGHT);

        // down
        player_down = new BufferedImage[4];
        player_down[0] = playerWalkSheet.crop(0,0, 64, 128);
        player_down[1] = playerWalkSheet.crop(64,0, 64, 128);
        player_down[2] = playerWalkSheet.crop(64 * 2,0, 64, 128);
        player_down[3] = playerWalkSheet.crop(64 * 3,0, 64, 128);

        // up
        player_up = new BufferedImage[4];
        player_up[0] = playerWalkSheet.crop(0,128, 64, 128);
        player_up[1] = playerWalkSheet.crop(64,128, 64, 128);
        player_up[2] = playerWalkSheet.crop(64 * 2,128, 64, 128);
        player_up[3] = playerWalkSheet.crop(64 * 3,128, 64, 128);

        // left
        player_right = new BufferedImage[4];
        player_right[0] = playerWalkSheet.crop(0,128 *2, 64, 128);
        player_right[1] = playerWalkSheet.crop(64,128 *2, 64, 128);
        player_right[2] = playerWalkSheet.crop(64 * 2,128 *2, 64, 128);
        player_right[3] = playerWalkSheet.crop(64 * 3,128 *2, 64, 128);

        // right
        player_left = new BufferedImage[4];
        player_left[0] = playerWalkSheet.crop(0,128 *3, 64, 128);
        player_left[1] = playerWalkSheet.crop(64,128 *3, 64, 128);
        player_left[2] = playerWalkSheet.crop(64 * 2,128 *3, 64, 128);
        player_left[3] = playerWalkSheet.crop(64 * 3,128 *3, 64, 128);

        player = ImageLoader.loadImage("/res/textures/test.png");
        playerSword = ImageLoader.loadImage("/res/textures/testSword.png");

    }
}
