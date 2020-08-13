package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.gfx.MobAnimation;

import java.awt.*;

public class AttackState extends LockedState {

    private long timer, lastTime, duration;
    private boolean damage;
    private Entity target;

    public AttackState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        damage = false;

        lastTime = System.currentTimeMillis();
        timer = 0;
        duration = 400;

        animation = new MobAnimation("/res/textures/playerSwordHit.png", 100, 4, 64, 128);
    }

    @Override
    public void tick() {
        super.tick();
        player.getAttackHover().tick();
        target = player.getAttackHover().getHovered();
        switch (player.getDirection()) {
            case "S":
                player.setyMove(0.5f);
                break;
            case "N":
                player.setyMove(-0.5f);
                break;
            case "E":
                player.setxMove(0.5f);
                break;
            case "W":
                player.setxMove(-0.5f);
                break;
        }

        if(timer > 300 && !damage && target != null){
            target.hurt(3);
            damage = true;
            System.out.println(target.getHealth());
        }

        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(timer >= duration){
            player.setPstate(player.armedState);
        }


    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        player.getAttackHover().render(g);
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    protected void getInput() {

    }

    public void resetTimer() {
        this.timer = 0;
        lastTime = System.currentTimeMillis();
        damage = false;
    }
}
