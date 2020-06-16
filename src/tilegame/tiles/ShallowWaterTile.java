package tilegame.tiles;

import tilegame.gfx.Assets;

public class ShallowWaterTile extends Tile {

    public ShallowWaterTile(int id) {
        super(Assets.shallowWater, id);
    }

    @Override
    public int getDepth() {
        return 1;
    }
}
