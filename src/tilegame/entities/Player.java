package tilegame.entities;

import tilegame.Handler;
import tilegame.entities.mobs.AttackHover;
import tilegame.entities.mobs.Mob;
import tilegame.entities.statics.InteractionHover;
import tilegame.gfx.MobAnimation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // ANIMATIONS

    // animacje ruchu
    private MobAnimation walkingAnim, swordAnim, meeleAttackAnim;

    private InteractionHover interactionHover;
    private AttackHover attackHover;

    // wczesniejszy stan aktywowanych klawiszy
    private boolean sword, lastNextEntity, lastPrevEntity, lastInteract;

    // attack speed
    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;

    // atak

    private int attackRange;

    public Player(Handler handler, float x, float y, int health) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.sword = false;
        this.lastPrevEntity = false;
        this.lastNextEntity = false;
        this.lastInteract = false;

        // znaczniki
        interactionHover = new InteractionHover(handler, this);
        attackHover = new AttackHover(handler, this);

        // wspolrzedne kolizji gracza
        // bez tego pokryje sie caly obraz gracza
        bounds.x = 8;
        bounds.y = 90;
        bounds.width = 32;
        bounds.height = 32;

        // Animation
        walkingAnim = new MobAnimation("/res/textures/playerWalk.png", 250, 4, 64, 128);
        swordAnim = new MobAnimation("/res/textures/playerSword.png", 250, 4, 64, 128);
        meeleAttackAnim = new MobAnimation("/res/textures/playerMeeleAttack.png", 250, 1, 64, 128);

        this.attackRange = 50;

    }

    @Override
    public void tick() {
        // Animations
        walkingAnim.tick();
        swordAnim.tick();
        meeleAttackAnim.tick();

        interactionHover.tick();
        if (this.sword) {
            attackHover.tick();
        }

        // Movement
        getInput();
        move(); // metoda z klasy Creature
        handler.getGameCamera().centerOnEntity(this); //wycentruj kamere na graczu


    }

    // renderowanie odpowiedniej animacji gracza
    @Override
    public void render(Graphics g) {

        interactionHover.render(g);
        if (this.sword) {
            attackHover.render(g);
        }

        // bron
        if(handler.getKeyManager().attack){
            drawPlayer(g, getCurrentAnimationFrame(meeleAttackAnim), x, y, width, height);
        }
        else if (this.sword)
            drawPlayer(g, getCurrentAnimationFrame(swordAnim), x, y, width, height);

            // zwykly ruch
        else
            drawPlayer(g, getCurrentAnimationFrame(walkingAnim), x, y, width, height);
    }

    // reakcje na dany klawisz
    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
        }
        if (handler.getKeyManager().right) {
            xMove += speed;
        }

        // trzymajac lewy shift zwieksza sie predkosc ruchu z defaultowej do 5
        if (handler.getKeyManager().run) {
            this.speed = 5;
        } else this.speed = Creature.DEFAULT_SPEED;

        // wyciagniecie broni
        if (handler.getKeyManager().sword) {
            this.sword = true;
        } else this.sword = false;

        // zmiana zaznaczonego obiektu
        if (!lastNextEntity && handler.getKeyManager().nextEntity) {
            interactionHover.nextEntity();
            attackHover.nextEntity();
        }
        if (!lastPrevEntity && handler.getKeyManager().prevEntity) {
            interactionHover.prevEntity();
            attackHover.prevEntity();
        }
        // interakcja z zaznaczonym obiektem
        if (!lastInteract && handler.getKeyManager().interactWithEntity) {
            if (interactionHover.getHovered() != null)
                interactionHover.getHovered().interact();
        }

        // Attack

        // jesli wcisnieto przycisk atakowania
        if(handler.getKeyManager().attack){

            // jesli jest ktos oznaczony czerwnoym hoverem
            if(attackHover.getHovered() != null){

                // wykonaj atak na oznaczonym przeciwniku
                makeAttack(attackHover.getHovered());
            }
        }


        // kontrola czy nastapila zmiana w przycisnieciu klawisza
        lastInteract = handler.getKeyManager().interactWithEntity;
        lastPrevEntity = handler.getKeyManager().prevEntity;
        lastNextEntity = handler.getKeyManager().nextEntity;
    }

    // brak przewidzianych interakcji gracza z samym soba?
    @Override
    public void interact() {

    }

    @Override
    public void die() {
        System.out.println("player died");
    }

    // wykonaj atak na przeslanym obiekcie
    private void makeAttack(Entity e) {

        // odliczanie do nastepnego ataku
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        // jesli zaatakowano za wczesnie, zresetuj czekanie do nastepnego ataku
        if (attackTimer < attackCooldown){
            attackTimer = 0;
            return;
        }

        // jesli roznica odleglosci pomiedzy graczem i obiektem jest mniejsza od zasiegu ataku, zran obiekt
        if (Math.abs(e.x - this.x) <= attackRange
                && Math.abs(e.y - this.y) <= attackRange){
            e.hurt(4);
        }

        attackTimer = 0;

        System.out.println("attack, left hp" + e.getHealth());

    }

    // funkcja pomagajaca do renderowania
    private void drawPlayer(Graphics g, BufferedImage img, float x, float y, int width, int height) {
        g.drawImage(img, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    // klatka animacji
    private BufferedImage getCurrentAnimationFrame(MobAnimation animation) {
        if (this.direction.equals("W")) return animation.getW().getCurrentFrame();
        else if (this.direction.equals("E")) return animation.getE().getCurrentFrame();
        else if (this.direction.equals("N")) return animation.getN().getCurrentFrame();
        else if (this.direction.equals("S")) return animation.getS().getCurrentFrame();
        else if (this.direction.equals("NW")) return animation.getNw().getCurrentFrame();
        else if (this.direction.equals("NE")) return animation.getNe().getCurrentFrame();
        else if (this.direction.equals("SE")) return animation.getSe().getCurrentFrame();
        else return animation.getSw().getCurrentFrame();
    }

    // funkcja pomagajaca renderujaca prostokat ataku
    private void drawAttackBounds(Graphics g, Rectangle r) {
        g.setColor(Color.white);
        g.drawRect((int) (r.x - handler.getGameCamera().getxOffset()),
                (int) (r.y - handler.getGameCamera().getyOffset()), r.width, r.height);
    }


    // GETTERS SETTERS

    public InteractionHover getInteractionHover() {
        return interactionHover;
    }

    public void setInteractionHover(InteractionHover interactionHover) {
        this.interactionHover = interactionHover;
    }

}
