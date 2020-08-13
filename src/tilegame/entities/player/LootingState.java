package tilegame.entities.player;

import tilegame.Handler;

import java.awt.*;

public class LootingState extends LockedState {

    public LootingState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    }

    @Override
    protected void getInput() {

    }

    @Override
    public void postRender(Graphics g) {

    }
}
