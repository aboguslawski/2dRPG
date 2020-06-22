package tilegame.entities.mobs.hostile;

import tilegame.Handler;
import tilegame.entities.mobs.Mob;

public abstract class HostileMob extends Mob {

    public HostileMob(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        this.hostile = true;
    }
}
