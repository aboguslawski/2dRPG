package tilegame.entities.mobs;

import tilegame.Handler;
import tilegame.entities.Creature;
import tilegame.entities.Entity;
import tilegame.entities.statics.Corpse;
import tilegame.items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

// podstawowa klasa moba ktora beda rozszerzac:
// - wrogie moby
// - neutralne moby
// - npc

public abstract class Mob extends Creature {

    // niektore neutralne moby lub npc moga stac sie agresywne np po zaatakowaniu przez gracza
    protected boolean hostile, chasing;

    // kontrola uplynietego czasu
    private long lastTime, timer;

    // zasiegi
    protected int sightRange, chaseRange, idleRange;

    // koordy spawnu moba
    protected float xSpawn, ySpawn;

    // drop
    protected ArrayList<Item> loot;

    // tekstura zwlok
    protected BufferedImage corpseImg;

    // obiekt zwlok
    protected Corpse corpse;

    public Mob(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);

        // koordy spawnu to koordy z ktorymi zostal zainicjalizowany
        xSpawn = x;
        ySpawn = y;

        // mozna atakowac wszystkie moby nawet jesli nie sa agresywne
        hostile = false;
        attackable = true;
        chasing = false;

        // domyslne predkosci moba
        this.walkSpeed = 1.0f;
        this.runSpeed = 3.0f;
        this.actualSpeed = walkSpeed;

        // domyslne zasiegi moba
        sightRange = 350;
        chaseRange = 1000;
        idleRange = 200;

        // timery
        lastTime = System.currentTimeMillis();
        timer = 0;

        // inicjalizacja
        loot = new ArrayList<>();
    }

    @Override
    public void tick() {
        //timery
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        // cialo gracza
        Rectangle playerBounds = handler.getWorld().getEntityManager().getPlayer().getPlayerRect();

        // jesli mob jest wrogi i gracz wkroczy na pole widzenia moba
        // to mob lapie agro na graczu dopoki ten znajduje sie w tym polu
        if (hostile && playerBounds.intersects(getSightArea())) {
            chasing = true;
            aggro(handler.getWorld().getEntityManager().getPlayer());
        }
        // w przeciwnym wypadku
        else {
            chasing = false;

            //jesli mob nie znajduje sie niedaleko spawna to ma na niego wrocic
            if (!getIdleArea().contains(this.x, this.y)) {
                backToSpawn();
            }

            //jesli znajduje sie na spawnie i nie wykrywa gracza, co 3 sekundy dostaje losowa instrukcje ruchu
            else if (timer > 1000) {
                idleMovement();
                timer = 0;
            }
        }

        // po otrzymaniu instrukcji wykonaj wedlug nich ruch
        move();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);

        // zasieg wzroku
        g.drawRect((int) (x - handler.getGameCamera().getxOffset() - sightRange),
                (int) (y - handler.getGameCamera().getyOffset() - sightRange), width + sightRange * 2, height + sightRange * 2);

        // wykrywalne cialo gracza
        g.drawRect((int) (handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset()),
                (int) (handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset()),
                handler.getWorld().getEntityManager().getPlayer().getWidth(), handler.getWorld().getEntityManager().getPlayer().getWidth());

        // pole idlowania
        g.setColor(Color.blue);
        g.drawRect((int) (xSpawn - handler.getGameCamera().getxOffset() - idleRange),
                (int) (ySpawn - handler.getGameCamera().getyOffset() - idleRange), idleRange * 2, idleRange * 2);

        // zasieg gonienia
        g.setColor(Color.pink);
        g.drawRect((int) (xSpawn - handler.getGameCamera().getxOffset() - chaseRange),
                (int) (ySpawn - handler.getGameCamera().getyOffset() - chaseRange), chaseRange * 2, chaseRange * 2);

        // cialo moba
        g.setColor(Color.black);
        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);

    }

    @Override
    public void die() {

        // po smierci tworzy obiekt zwlok na swoim miejscu
        handler.getWorld().getEntityManager().addEntity(new Corpse(handler, x, y + height, 64, 32, corpseImg, loot));
    }

    // losowe instrukcje ruchu dla "naturalnego" wygladu
    // do ulepszenia w przyszlosci
    protected void idleMovement() {
        actualSpeed = walkSpeed;

        Random rn = new Random();
        int rand = rn.nextInt() % 3;

        if (rand == 0) {

            rand = rn.nextInt() % 3;
            if (rand == 0) {
                setxMove(actualSpeed);
            } else if (rand == 1) {
                setxMove(-actualSpeed);
            } else {
                setxMove(0);
            }

        } else {
            setxMove(0);
        }

        rand = rn.nextInt() % 3;
        if (rand == 0) {

            rand = rn.nextInt() % 3;
            if (rand == 0) {
                setyMove(actualSpeed);
            } else if (rand == 1) {
                setyMove(-actualSpeed);
            } else {
                setyMove(0);
            }

        } else {
            setyMove(0);
        }

    }

    // poscig za przeslanym obiektem i walenie hitow
    protected void aggro(Entity e) {
        // zaczyna biec
        actualSpeed = runSpeed;

        // timer do wykonywania atakow
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        // jesli znajduje sie na lewo od obiektu wykonuje ruch w prawo
        if (e.getX() + e.getBounds().getX() + e.getBounds().getWidth() / 2 - bounds.getWidth() >= x + bounds.getX() + bounds.getWidth() / 2)
            xMove = actualSpeed;
            // jesli na prawo to wykonuje ruch w lewo
        else if (e.getX() + e.getBounds().getX() + e.getBounds().getWidth() / 2 + bounds.getWidth() <= x + bounds.getX() + bounds.getWidth() / 2)
            xMove = -actualSpeed;
            // zapobiega trzesieniu sie
        else xMove = 0;

        // jesli znajduje sie nad obiektem to biegnie w dol
        if (e.getY() + e.getBounds().getY() + e.getBounds().getHeight() / 2 - bounds.getHeight() >= y + bounds.getY() + bounds.getHeight() / 2)
            yMove = actualSpeed;
            // jesli pod to biegnie w gore
        else if (e.getY() + e.getBounds().getY() + e.getBounds().getHeight() / 2 + bounds.getHeight() <= y + bounds.getY() + bounds.getHeight() / 2)
            yMove = -actualSpeed;
            // ten sam sposob zapobiegajacy trzesieniu sie
        else yMove = 0;

        // jesli ma aggro na przeciwniku, stoi w miejscu i znajduje sie blisko, to znaczy ze moze sprobowac wykonac atak
        if (xMove == 0 && yMove == 0 && Math.abs(e.getX() - x) < bounds.getWidth() * 2 && Math.abs(e.getY() - y) < bounds.getHeight() * 2) {

            // dokonuje ataku co sekunde
            if (timer > 1000) {
                e.hurt(3);
                System.out.println("player got hit, hp left: " + e.getHealth());
                timer = 0;
            }

        }
    }

    // powrot do idle area
    protected void backToSpawn() {
        actualSpeed = walkSpeed;

        if (x < xSpawn - 15) xMove = actualSpeed;
        else if (x > xSpawn + 15) xMove = -actualSpeed;
        if (y < ySpawn - 15) yMove = actualSpeed;
        else if (y > ySpawn + 15) yMove = -actualSpeed;
    }

    // zasieg wykrywania gracza
    public Rectangle getSightArea() {
        return new Rectangle((int) (x - handler.getGameCamera().getxOffset() - sightRange),
                (int) (y - handler.getGameCamera().getyOffset() - sightRange), width + sightRange * 2, height + sightRange * 2);
    }

    // zasieg chasowania
    public Rectangle getChaseArea() {
        return new Rectangle((int) (xSpawn - handler.getGameCamera().getxOffset() - chaseRange),
                (int) (ySpawn - handler.getGameCamera().getyOffset() - chaseRange), chaseRange * 2, chaseRange * 2);
    }

    // maly zasieg wokol respawnu
    public Rectangle getIdleArea() {
        return new Rectangle((int) (xSpawn - handler.getGameCamera().getxOffset() - idleRange),
                (int) (ySpawn - handler.getGameCamera().getyOffset() - idleRange), idleRange * 2, idleRange * 2);
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }

    public void setLoot(ArrayList<Item> loot) {
        this.loot = loot;
    }

    public Corpse getCorpse() {
        return corpse;
    }

    public void setCorpse(Corpse corpse) {
        this.corpse = corpse;
    }

    public BufferedImage getCorpseImg() {
        return corpseImg;
    }

    public void setCorpseImg(BufferedImage corpseImg) {
        this.corpseImg = corpseImg;
    }
}
