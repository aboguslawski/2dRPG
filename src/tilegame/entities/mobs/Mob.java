package tilegame.entities.mobs;

import tilegame.Handler;
import tilegame.entities.Creature;

public abstract class Mob extends Creature {

    protected boolean hostile;

    public Mob(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);

        this.hostile = false;
        this.attackable = true;
    }

}
