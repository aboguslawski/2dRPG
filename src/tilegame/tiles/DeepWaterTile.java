package tilegame.tiles;

import tilegame.gfx.Assets;

public class DeepWaterTile extends Tile {

    public DeepWaterTile(int id){
        super(Assets.deepWater, id);
    }

    @Override
    public int getDepth() {
        return 3;
    }
}
