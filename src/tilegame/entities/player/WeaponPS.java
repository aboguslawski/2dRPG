package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.mobs.AttackHover;
import tilegame.gfx.MobAnimation;

import java.awt.*;

// stan wyciagnietej broni

public class WeaponPS extends PState {

    // attack speed
    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;

    // atak
    private AttackHover attackHover;

    // zasieg ataku
    private int attackRange;

    public WeaponPS(Handler handler, Player player) {
        super(handler, player);

        // animacje
        walk = new MobAnimation("/res/textures/playerSword.png", 250, 4, 64, 128);

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

        // zmiana zaznaczonego obiektu
        if (!player.isLastNextEntity() && handler.getKeyManager().nextEntity) {
            attackHover.nextEntity();
        }
        if (!player.isLastPrevEntity() && handler.getKeyManager().prevEntity) {
            attackHover.prevEntity();
        }

        // jesli wcisnieto przycisk atakowania
        if (handler.getKeyManager().attack) {

            // jesli jest ktos oznaczony czerwnoym hoverem
            if (attackHover.getHovered() != null) {

                // wykonaj atak na oznaczonym przeciwniku
                makeAttack(attackHover.getHovered());
            }
        }
    }

    // wykonaj atak na przeslanym obiekcie
    private void makeAttack(Entity e) {

        // odliczanie do nastepnego ataku
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        // jesli zaatakowano za wczesnie, zresetuj czekanie do nastepnego ataku
        if (attackTimer < attackCooldown) {
            attackTimer = 0;
            return;
        }

        // jesli roznica odleglosci pomiedzy graczem i obiektem jest mniejsza od zasiegu ataku, zran obiekt
        if (Math.abs(e.getX() - player.getX()) <= attackRange
                && Math.abs(e.getY() - player.getY()) <= attackRange) {
            e.hurt(4);
        }

        // reset timera po wykonanym ataku
        attackTimer = 0;

        System.out.println("attack, left hp" + e.getHealth());

    }

}
