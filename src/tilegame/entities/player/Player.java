package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Creature;
import tilegame.entities.statics.InteractionHover;

import java.awt.*;

// zreformowana klasa gracza
// gracz przelacza sie pomiedzy stanami
public class Player extends Creature {

    // stany
    private PState pstate;
    private WalkPS walkPS;
    private WeaponPS weaponPS;

    // znacznik interakcji z innymi obiektami
    // w interakcje mozna wchodzic bez wzledu na aktualny stan
    private InteractionHover interactionHover;

    // wczesniejszy stan aktywowanych klawiszy
    private boolean sword, lastNextEntity, lastPrevEntity, lastInteract;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.sword = false;
        this.lastPrevEntity = false;
        this.lastNextEntity = false;
        this.lastInteract = false;
        this.health = 20;

        // inicjacja stanow
        this.walkPS = new WalkPS(handler, this);
        this.weaponPS = new WeaponPS(handler, this);

        // gracz rodzi sie w stanie chodzenia
        this.pstate = walkPS;

        // inicjalizacja znacznika interakcji
        interactionHover = new InteractionHover(handler, this);

        // wspolrzedne kolizji gracza
        // bez tego pokryje sie caly obraz gracza
        bounds.x = 8;
        bounds.y = 90;
        bounds.width = 32;
        bounds.height = 32;
    }

    @Override
    public void tick() {

        // tickuj aktualny stan
        pstate.tick();

        // tickuj znacznik interakcji
        interactionHover.tick();

        // Movement
        getInput();
        move(); // metoda z klasy Creature
        handler.getGameCamera().centerOnEntity(this); //wycentruj kamere na graczu
    }

    // renderowanie odpowiedniej animacji gracza
    @Override
    public void render(Graphics g) {

        // renderuj znacznik interakcji
        interactionHover.render(g);

        // renderuj aktualny stan
        pstate.render(g);
    }

    // reakcje na dany klawisz
    private void getInput() {
        xMove = 0;
        yMove = 0;

        // aktualna predkosc poruszania zalezna jest od stanu w ktorym znajduje sie gracz
        walkSpeed = pstate.getWalkSpeed();
        runSpeed = pstate.getRunSpeed();


        // trzymajac shift zmienia sie predkosc ruchu
        if (handler.getKeyManager().run) {
            actualSpeed = runSpeed;
        } else actualSpeed = walkSpeed;

        // ruch
        if (handler.getKeyManager().up) {
            yMove = -actualSpeed;
        }
        if (handler.getKeyManager().down) {
            yMove = actualSpeed;
        }
        if (handler.getKeyManager().left) {
            xMove = -actualSpeed;
        }
        if (handler.getKeyManager().right) {
            xMove += actualSpeed;
        }

        // wyciagniecie broni i zmiana stanu
        if (handler.getKeyManager().sword) {
            this.sword = true;
            this.pstate = weaponPS;
        } else {
            this.sword = false;
            this.pstate = walkPS;
        }

        // zmiana zaznaczonego obiektu do interakcji
        if (!lastNextEntity && handler.getKeyManager().nextEntity) {
            interactionHover.nextEntity();
        }
        if (!lastPrevEntity && handler.getKeyManager().prevEntity) {
            interactionHover.prevEntity();
        }
        // interakcja z zaznaczonym obiektem
        if (!lastInteract && handler.getKeyManager().interactWithEntity) {
            if (interactionHover.getHovered() != null)
                interactionHover.getHovered().interact();
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

    // GETTERS SETTERS

    public InteractionHover getInteractionHover() {
        return interactionHover;
    }

    public void setInteractionHover(InteractionHover interactionHover) {
        this.interactionHover = interactionHover;
    }

    public boolean isLastNextEntity() {
        return lastNextEntity;
    }

    public void setLastNextEntity(boolean lastNextEntity) {
        this.lastNextEntity = lastNextEntity;
    }

    public boolean isLastPrevEntity() {
        return lastPrevEntity;
    }

    public void setLastPrevEntity(boolean lastPrevEntity) {
        this.lastPrevEntity = lastPrevEntity;
    }

    public boolean isLastInteract() {
        return lastInteract;
    }

    public void setLastInteract(boolean lastInteract) {
        this.lastInteract = lastInteract;
    }
}
