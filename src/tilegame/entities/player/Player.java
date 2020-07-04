package tilegame.entities.player;

import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.Creature;
import tilegame.entities.statics.Chest;
import tilegame.entities.statics.InteractionHover;
import tilegame.inventory.Inventory;
import tilegame.inventory.LootWindow;

import java.awt.*;
import java.awt.event.KeyEvent;

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
    private boolean sword;

    protected Chest looting;

    protected Inventory inventory;
    protected LootWindow lootWindow;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.sword = false;
        this.health = 20;
        initHealth = health;

        // wspolrzedne kolizji gracza
        // bez tego pokryje sie caly obraz gracza
        bounds.x = 8;
        bounds.y = 90;
        bounds.width = 32;
        bounds.height = 32;

        // inicjalizacja
        this.walkPS = new WalkPS(handler, this);
        this.weaponPS = new WeaponPS(handler, this);
        this.pstate = walkPS;
        interactionHover = new InteractionHover(handler, this);
        inventory = new Inventory(handler);
        lootWindow = new LootWindow(handler);
        looting = null;
    }

    @Override
    public void tick() {

        if (looting != null) {
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
                looting = null;
            }
        }

        // tickuj znacznik interakcji
        interactionHover.tick();

        // Movement
        getInput();
        move(); // metoda z klasy Creature

        // tickuj aktualny stan
        pstate.tick();

        //wycentruj kamere na graczu
        handler.getGameCamera().centerOnEntity(this);

        // tickuj ekwipunek
        inventory.tick();
        lootWindow.tick();
    }

    // renderowanie odpowiedniej animacji gracza
    @Override
    public void render(Graphics g) {

        // renderuj znacznik interakcji
        interactionHover.render(g);

        // renderuj aktualny stan
        pstate.render(g);

//        g.setColor(Color.black);
//        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);

    }

    // render wywolywany w innej kolejnosci niz podstawowy render gracza
    public void postRender(Graphics g) {

        // render ekwipunku i okna lootu
        inventory.render(g);
        lootWindow.render(g);

        // pasek zdrowia
        g.setColor(Color.red);
        if (health > 0)
            g.fillRect(20, Display.SCREEN_HEIGHT - 120 + 100 - (health * 100) / initHealth, 100, (health * 100) / initHealth);
        g.setColor(Color.white);
        g.drawRect(20, Display.SCREEN_HEIGHT - 120, 100, 100);

        // pasek staminy
        g.setColor(Color.yellow);
        g.fillRect(140, Display.SCREEN_HEIGHT - 120, 100, 100);
        g.setColor(Color.white);
        g.drawRect(140, Display.SCREEN_HEIGHT - 120, 100, 100);

    }

    // reakcje na dany klawisz
    private void getInput() {
        xMove = 0;
        yMove = 0;

        // po aktywowaniu ekwipunku przejdz do stanu chodzenia i nie reaguj na zadne dalsze instrukcje
        if (inventory.isActive() || lootWindow.isActive()) {
            pstate = walkPS;
            return;
        }

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
            if (!this.sword) {
                this.sword = true;
                this.pstate = weaponPS;
            } else {
                this.pstate = walkPS;
                this.sword = false;
            }
        }

        // zmiana zaznaczonego obiektu do interakcji
        if (handler.getKeyManager().nextEntity) {
            interactionHover.nextEntity();
            weaponPS.getAttackHover().nextEntity();
        }
        if (handler.getKeyManager().prevEntity) {
            interactionHover.prevEntity();
            weaponPS.getAttackHover().prevEntity();
        }
        // interakcja z zaznaczonym obiektem
        if (handler.getKeyManager().interactWithEntity) {
            if (interactionHover.getHovered() != null)
                interactionHover.getHovered().interact();
        }
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


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public InteractionHover getInteractionHover() {
        return interactionHover;
    }

    public void setInteractionHover(InteractionHover interactionHover) {
        this.interactionHover = interactionHover;
    }

    public Rectangle getPlayerRect() {
        return new Rectangle((int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height);
    }

    public Chest getLooting() {
        return looting;
    }

    public void setLooting(Chest looting) {
        this.looting = looting;
    }

    public LootWindow getLootWindow() {
        return lootWindow;
    }

    public void setLootWindow(LootWindow lootWindow) {
        this.lootWindow = lootWindow;
    }
}
