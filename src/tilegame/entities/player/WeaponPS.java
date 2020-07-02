package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.mobs.AttackHover;
import tilegame.gfx.MobAnimation;

import java.awt.*;

// stan wyciagnietej broni

public class WeaponPS extends PState {

    private static final int FORWARD_ATTACK = 1;
    private static final int LEFT_ATTACK = 2;
    private static final int RIGHT_ATTACK = 3;
    private static final int BLOCK = 4;

    private MobAnimation attack, prep;

    // attack speed
    private long lastAttackTimer, attackCooldown = 200, attackTimer = attackCooldown;

    // atak
    private AttackHover attackHover;

    // zasieg ataku
    private int attackRange;

    public WeaponPS(Handler handler, Player player) {
        super(handler, player);

        // animacje
        walk = new MobAnimation("/res/textures/playerSword.png", 250, 4, 64, 128);
        attack = new MobAnimation("/res/textures/playerMeeleAttack.png", 250, 1, 64, 128);
        prep = new MobAnimation("/res/textures/attackPrep.png", 250, 1, 64, 128);

        // inicjowana animacja
        animation = walk;

        // znacznik obiektu na ktorym gracz ma focus
        attackHover = new AttackHover(handler, player);

        // zasieg ataku
        this.attackRange = 100;

        // szybkosci poruszania sie w stanie
        this.walkSpeed = 2.5f;
        this.runSpeed = 4f;
    }

    @Override
    public void tick() {
        super.tick();
        attackHover.tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        attackHover.render(g);
    }

    @Override
    protected void getInput() {

        animation = walk;
        this.walkSpeed = 2.5f;
        this.runSpeed = 4f;

        // jesli wcisnieto przycisk atakowania
        if (handler.getKeyManager().attack) {

//            animation = prep;
            this.walkSpeed = 1f;
            this.runSpeed = 1f;

            // jesli jest ktos oznaczony czerwnoym hoverem
            if (attackHover.getHovered() != null) {

                focusDirection(attackHover.getHovered());

                if (handler.getKeyManager().atForward) {
                    // wykonaj atak na oznaczonym przeciwniku
                    makeForwardAttack(attackHover.getHovered());
                }
                else if(handler.getKeyManager().atLeft){
                    System.out.println("dir przed " + player.getDirection());
                    makeLeftAttack(attackHover.getHovered());
                    System.out.println("dir po " + player.getDirection());
                }
                else if(handler.getKeyManager().atRight){
                    System.out.println("dir przed " + player.getDirection());
                    makeRightAttack(attackHover.getHovered());
                    System.out.println("dir po " + player.getDirection());
                }


                player.move();

            }
        }
    }

    // wykonaj atak na przeslanym obiekcie
    private void makeAttack(Entity e) {
//
//        if(player.inventory.isActive())
//            return;

        animation = attack;

        // jesli roznica odleglosci pomiedzy graczem i obiektem jest mniejsza od zasiegu ataku, zran obiekt
        if (Math.abs(e.getX() - player.getX()) <= attackRange
                && Math.abs(e.getY() - player.getY()) <= attackRange) {
            e.hurt(4);
        }

        // reset timera po wykonanym ataku
        attackTimer = 0;

        if (e.getX() >= player.getX()) {
            player.setxMove(5);
        } else player.setxMove(-5);
        if (e.getY() >= player.getY()) {
            player.setyMove(5);
        } else player.setyMove(-5);

        System.out.println("attack, left hp" + e.getHealth());

    }

    // wykonaj atak na przeslanym obiekcie
    private void makeForwardAttack(Entity e) {

        // odliczanie do nastepnego ataku
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        // jesli zaatakowano za wczesnie, zresetuj czekanie do nastepnego ataku
        if (attackTimer < attackCooldown) {
            attackTimer = 0;
            return;
        }

        makeAttack(e);
        if (e.getX() >= player.getX()) {
            player.setxMove(20);
        } else player.setxMove(-20);
        if (e.getY() >= player.getY()) {
            player.setyMove(20);
        } else player.setyMove(-20);
    }

    private void makeLeftAttack(Entity e){

        // odliczanie do nastepnego ataku
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        // jesli zaatakowano za wczesnie, zresetuj czekanie do nastepnego ataku
        if (attackTimer < attackCooldown) {
            attackTimer = 0;
            return;
        }

        makeAttack(e);
    }

    private void makeRightAttack(Entity e){

        // odliczanie do nastepnego ataku
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        // jesli zaatakowano za wczesnie, zresetuj czekanie do nastepnego ataku
        if (attackTimer < attackCooldown) {
            attackTimer = 0;
            return;
        }

        makeAttack(e);
    }

    public AttackHover getAttackHover() {
        return attackHover;
    }
}
