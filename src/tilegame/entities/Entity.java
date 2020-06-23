package tilegame.entities;

import tilegame.Handler;

import java.awt.*;

public abstract class Entity {


    public static final int DEFAULT_HEALTH = 10; // domyslna ilosc punktow zycia

    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected int health;
    protected boolean active = true; // po smierci - false
    protected Rectangle bounds; // prostokat powodujacy kolizje
    protected boolean interaction; // czy mozna wejsc z entity w interakcje
    protected boolean attackable; // czy mozna go zaatakowac

    public Entity(Handler handler, float x, float y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // domyslnie z obiektem nie mozna wejsc w interakcje ani go zaatakowac
        this.interaction = false;
        this.attackable = false;

        this.health = DEFAULT_HEALTH;

        bounds = new Rectangle(0,0,width,height); // domyslnie entity powoduje kolizje caloscia
    }

    // HELPERY DO KOLIZJI

    public boolean checkEntityCollisions(float xOffset, float yOffset){
        for(Entity e : handler.getWorld().getEntityManager().getEntities()){

            // nie sprawdzaj kolizji z samym soba
            if(e.equals(this)){
                continue;
            }

            // jesli nachodzi na inna entity, zachodzi kolizja
            if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
                return  true;
            }
        }

        //jesli nie to nie
        return false;
    }

    // zwraca prostokat kolizji obiektu
    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        // prostokat wokol entity
        return  new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    // czy mozna wejsc w interakcje z tym obiektem
    public boolean isInteraction(){
        return this.interaction;
    }

    // pomocnicza funkcja rysujaca kolizje obiektu
    public void drawBounds(Graphics g){
        g.setColor(Color.black);
        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
    }

    public void hurt(int amt){
        health -= amt;
        if(health <= 0){
            active = false;
            die();
        }
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void interact();

    public abstract void die();

    // getters setters

    public boolean isAttackable(){
        return this.attackable;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
