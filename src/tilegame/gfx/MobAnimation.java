package tilegame.gfx;

import java.awt.image.BufferedImage;

public class MobAnimation {

    private int frames, width, height;
    private Animation s, n, e, w;

    public MobAnimation(String path, int speed, int frames, int width, int height) {
        this.frames = frames;
        this.width = width;
        this.height = height;

        this.s = new Animation(speed, animArray(frames, 0, width, height, path));
        this.n = new Animation(speed, animArray(frames, 1, width, height, path));
        this.e = new Animation(speed, animArray(frames, 2, width, height, path));
        this.w = new Animation(speed, animArray(frames, 3, width, height, path));

    }

    public void tick() {
        s.tick();
        n.tick();
        e.tick();
        w.tick();
    }

    private static BufferedImage[] animArray(int frames, int row, int width, int height, String path) {
        BufferedImage[] array = new BufferedImage[frames];
        Spritesheet playerWalkSheet = new Spritesheet(ImageLoader.loadImage(path));

        for (int i = 0; i < array.length; i++) {
            array[i] = playerWalkSheet.crop(width * i, height * row, width, height);
        }

        return array;
    }

    public Animation getS() {
        return s;
    }

    public Animation getN() {
        return n;
    }

    public Animation getE() {
        return e;
    }

    public Animation getW() {
        return w;
    }
}
