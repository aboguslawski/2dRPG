package tilegame.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    // spritesheet
    public static BufferedImage floor, wall, shallowWater, mediumWater, deepWater;
    // test player
    public static BufferedImage player, playerSword;
    // mask
    public static BufferedImage bkgdMask;
    // static entities
    public static BufferedImage torchStand, torchStand2, house1, lamp1, lamp1Night, hover;

    // player animations
    public static BufferedImage[] playerS, playerN, playerW, playerE, playerNW, playerNE, playerSW, playerSE,
            dayNightCycle, btStart;

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    public static void init() {
        Spritesheet spritesheet = new Spritesheet(ImageLoader.loadImage("/res/textures/Spritesheet.png"));
//        Spritesheet playerWalkSheet = new Spritesheet(ImageLoader.loadImage("/res/textures/playerWalk.png"));

        floor = spritesheet.crop(0, 0, TILE_WIDTH, TILE_HEIGHT);
        wall = spritesheet.crop(TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        shallowWater = spritesheet.crop(TILE_WIDTH * 2, 0, TILE_WIDTH, TILE_HEIGHT);
        mediumWater = spritesheet.crop(TILE_WIDTH * 3, 0, TILE_WIDTH, TILE_HEIGHT);
        deepWater = spritesheet.crop(TILE_WIDTH * 4, 0, TILE_WIDTH, TILE_HEIGHT);

        // animacja gracza
        playerS = animArray(4, 0, 64, 128);
        playerN = animArray(4, 1, 64, 128);
        playerE = animArray(4, 2, 64, 128);
        playerW = animArray(4, 3, 64, 128);
        playerNW = animArray(4, 4, 64, 128);
        playerNE = animArray(4, 5, 64, 128);
        playerSE = animArray(4, 6, 64, 128);
        playerSW = animArray(4, 7, 64, 128);

        // maska
        bkgdMask = ImageLoader.loadImage("/res/textures/mask.png");

        // cykl dnia i nocy
        dayNightCycle = cycleAnim();

        // static entities
        torchStand = ImageLoader.loadImage("/res/textures/torchstand.png");
        torchStand2 = ImageLoader.loadImage("/res/textures/torchstand2.png");
        house1 = ImageLoader.loadImage("/res/textures/statics/house1.png");
        lamp1 = ImageLoader.loadImage("/res/textures/statics/lamp1.png");
        lamp1Night = ImageLoader.loadImage("/res/textures/statics/lamp1night.png");

        hover = ImageLoader.loadImage("/res/textures/hover.png");

        // menu

        btStart = new BufferedImage[2];
        btStart[0] = ImageLoader.loadImage("/res/textures/menu/start.png");
        btStart[1] = ImageLoader.loadImage("/res/textures/menu/start2.png");

        // stare
        player = ImageLoader.loadImage("/res/textures/test.png");
        playerSword = ImageLoader.loadImage("/res/textures/testSword.png");

    }

    private static BufferedImage[] animArray(int frames, int row, int width, int height) {
        BufferedImage[] array = new BufferedImage[frames];
        Spritesheet playerWalkSheet = new Spritesheet(ImageLoader.loadImage("/res/textures/playerWalk.png"));

        for (int i = 0; i < array.length; i++) {
            array[i] = playerWalkSheet.crop(width * i, height * row, width, height);
        }

        return array;
    }

    // cykl dnia i nocy animacja
    private static BufferedImage[] cycleAnim() {
        BufferedImage[] dayNightCycle = new BufferedImage[24];

        for (int i = 0; i < dayNightCycle.length; i++) {
            if (i <= 5)
                dayNightCycle[i] = ImageLoader.loadImage("/res/textures/daycycle/night.png");
            else if (i <= 8)
                dayNightCycle[i] = ImageLoader.loadImage("/res/textures/daycycle/dawn.png");
            else if (i <= 17)
                dayNightCycle[i] = ImageLoader.loadImage("/res/textures/daycycle/midday.png");
            else if (i <= 21)
                dayNightCycle[i] = ImageLoader.loadImage("/res/textures/daycycle/sunset.png");
            else
                dayNightCycle[i] = ImageLoader.loadImage("/res/textures/daycycle/dusk.png");
        }
        return dayNightCycle;
    }
}
