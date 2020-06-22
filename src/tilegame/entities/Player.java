package tilegame.entities;

import tilegame.Handler;
import tilegame.entities.mobs.AttackHover;
import tilegame.entities.statics.InteractionHover;
import tilegame.gfx.MobAnimation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // ANIMATIONS

    // animacje ruchu
    private MobAnimation walkingAnim, swordAnim;

    private InteractionHover interactionHover;
    private AttackHover attackHover;

    // wczesniejszy stan aktywowanych klawiszy
    private boolean sword, prevQ, prevE;

    public Player(Handler handler, float x, float y, int health) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.sword = false;
        this.prevQ = false;
        this.prevE = false;

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


    }

    @Override
    public void tick() {
        //Animations
        walkingAnim.tick();
        swordAnim.tick();

        interactionHover.tick();
        if(this.sword){
            attackHover.tick();
        }

        //Movement
        getInput();
        move(); // metoda z klasy Creature
        handler.getGameCamera().centerOnEntity(this); //wycentruj kamere na graczu
    }

    // renderowanie odpowiedniej animacji gracza
    @Override
    public void render(Graphics g) {

        interactionHover.render(g);
        if(this.sword){
            attackHover.render(g);
        }

        // bron
        if (this.sword)
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
        if (!prevQ && handler.getKeyManager().nextEntity) {
            interactionHover.nextEntity();
            attackHover.nextEntity();
        }

        // interakcja z zaznaczonym obiektem
        if (!prevE && handler.getKeyManager().interactWithEntity) {
            if (interactionHover.getHovered() != null)
                interactionHover.getHovered().interact();
        }

        // kontrola czy nastapila zmiana w przycisnieciu klawisza
        prevE = handler.getKeyManager().interactWithEntity;
        prevQ = handler.getKeyManager().nextEntity;
    }

    // brak przewidzianych interakcji gracza z samym soba?
    @Override
    public void interact() {

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


    // GETTERS SETTERS

    public InteractionHover getInteractionHover() {
        return interactionHover;
    }

    public void setInteractionHover(InteractionHover interactionHover) {
        this.interactionHover = interactionHover;
    }

}
