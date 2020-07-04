package tilegame.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    //++++++++++++++++++++++ FONTS

    public static Font font50;

    //+++++++++++++++++++++ IMAGES

    // spritesheet
    public static BufferedImage floor, wall, shallowWater, mediumWater, deepWater;
    // test player
    public static BufferedImage player, playerSword, enemy1;
    // mask
    public static BufferedImage bkgdMask;
    // static entities
    public static BufferedImage torchStand, torchStand2, house1, lamp1, lamp1Night, chest1, interactionHover, attackHover;
    // items
    public static BufferedImage coins, mushroom;
    // inventory
    public static BufferedImage inventoryScreen, lootScreen;
    // player animations
    public static BufferedImage[] dayNightCycle, btStart;

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    public static void init() {

        // FONTS

        font50 = FontLoader.loadFont("C:\\Users\\Adam Bogusławski\\Desktop\\Programowanie\\Projects\\RPG\\src\\res\\fonts\\cpc.ttf", 50);

        // IMAGES

        Spritesheet spritesheet = new Spritesheet(ImageLoader.loadImage("/res/textures/Spritesheet.png"));

        floor = spritesheet.crop(0, 0, TILE_WIDTH, TILE_HEIGHT);
        wall = spritesheet.crop(TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        shallowWater = spritesheet.crop(TILE_WIDTH * 2, 0, TILE_WIDTH, TILE_HEIGHT);
        mediumWater = spritesheet.crop(TILE_WIDTH * 3, 0, TILE_WIDTH, TILE_HEIGHT);
        deepWater = spritesheet.crop(TILE_WIDTH * 4, 0, TILE_WIDTH, TILE_HEIGHT);

        // inventory and loot

        inventoryScreen = ImageLoader.loadImage("/res/textures/inventory.png");
        lootScreen = ImageLoader.loadImage("/res/textures/lootWindow.png");

        // maska
        bkgdMask = ImageLoader.loadImage("/res/textures/mask2.png");

        // cykl dnia i nocy
        dayNightCycle = cycleAnim();

        // static entities
        torchStand = ImageLoader.loadImage("/res/textures/torchstand.png");
        torchStand2 = ImageLoader.loadImage("/res/textures/torchstand2.png");
        house1 = ImageLoader.loadImage("/res/textures/statics/house1.png");
        lamp1 = ImageLoader.loadImage("/res/textures/statics/lamp1.png");
        lamp1Night = ImageLoader.loadImage("/res/textures/statics/lamp1night.png");


        // hovery
        interactionHover = ImageLoader.loadImage("/res/textures/interactionHover.png");
        attackHover = ImageLoader.loadImage("/res/textures/attackHover.png");

        // menu

        btStart = new BufferedImage[2];
        btStart[0] = ImageLoader.loadImage("/res/textures/menu/start.png");
        btStart[1] = ImageLoader.loadImage("/res/textures/menu/start2.png");

        // enemies

        enemy1 = ImageLoader.loadImage("/res/textures/entities/mobs/enemy.png");

        // items

        coins = ImageLoader.loadImage("/res/textures/items/coins.png");
        mushroom = ImageLoader.loadImage("/res/textures/items/mushroom.png");

        // chest
        chest1 = ImageLoader.loadImage("/res/textures/statics/chest.png");

        // stare
        player = ImageLoader.loadImage("/res/textures/test.png");
        playerSword = ImageLoader.loadImage("/res/textures/testSword.png");

    }

    private static BufferedImage[] animArray(int frames, int row, int width, int height, String path) {
        BufferedImage[] array = new BufferedImage[frames];
        Spritesheet playerWalkSheet = new Spritesheet(ImageLoader.loadImage(path));

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
