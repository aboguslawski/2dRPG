package tilegame.entities.player;

import tilegame.gfx.MobAnimation;

public abstract class UnlockedState extends PlayerState {

    protected MobAnimation idle, walking, running;

    @Override
    protected void getInput() {
        animation = idle;
        player.setxMove(0);
        player.setyMove(0);

//        if(player.inventory.isActive()){
//            state = new InventoryState();
//        }

        float actualSpeed;
        if (handler.getKeyManager().shift) {
            actualSpeed = runSpeed;
        } else actualSpeed = walkSpeed;

        if (handler.getKeyManager().up) {
            player.setyMove(-actualSpeed);
        }
        if (handler.getKeyManager().down) player.setyMove(actualSpeed);
        if (handler.getKeyManager().left) player.setxMove(-actualSpeed);
        if (handler.getKeyManager().right) player.setxMove(actualSpeed);

        if (player.getxMove() != 0 && player.getyMove() != 0) {
            player.setxMove((float) (player.getxMove() / 1.2));
            player.setyMove((float) (player.getyMove() / 1.2));
        }

        if (handler.getKeyManager().up
                || handler.getKeyManager().down
                || handler.getKeyManager().left
                || handler.getKeyManager().right) {
            animation = walking;
            if (handler.getKeyManager().shift) animation = running;
        }

        if (handler.getKeyManager().e) player.getInteractionHover().nextEntity();
        if (handler.getKeyManager().q) player.getInteractionHover().prevEntity();
        if (handler.getKeyManager().f) player.getInteractionHover().getHovered().interact();
        if(handler.getKeyManager().i) player.setPstate(player.inventoryState);
    }
}
