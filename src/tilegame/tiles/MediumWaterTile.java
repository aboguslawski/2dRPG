package tilegame.tiles;

import tilegame.gfx.Assets;

public class MediumWaterTile extends Tile {

    public MediumWaterTile(int id){
        super(Assets.mediumWater, id);
    }

    @Override
    public int getDepth() {
        return 2;
    }

}
