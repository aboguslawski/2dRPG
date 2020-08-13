package tilegame.entities;

import tilegame.Handler;
import tilegame.tiles.Tile;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f; // domyslna szybkosc poruszania sie po mapie
    public static final int DEFAULT_CREATURE_WIDTH = 48;
    public static final int DEFAULT_CREATURE_HEIGHT = 116;

    protected String direction; // strona swiata w ktora jest zwrocony
    protected float walkSpeed, runSpeed, actualSpeed;
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        this.walkSpeed = DEFAULT_SPEED;
        this.runSpeed = DEFAULT_SPEED + 2f;
        this.actualSpeed = walkSpeed;
        xMove = 0;
        yMove = 0;
        direction = "S";
    }

    // ruch creature
    public void move() {
        // ruch podzielony jest na poziomy i pionowy

        // w wodzie o glebokosci 1 lub wiekszej szybkosc ruchu jest zredukowana do 70%
        speedInWater(1, 0.7f);

        // jesli nie ma kolizji z innym obiektem, wykonaj ruch
        if (!checkEntityCollisions(xMove, 0f))
            moveX();
        if (!checkEntityCollisions(0f, yMove))
            moveY();

    }

    // ruch poziomy (lewo prawo)
    public void moveX() {

        // ** KOLIZJE X **
        if (xMove > 0) {
            // w prawo
            // zwrot
            direction = "E";

            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH; // tx - gdzie chcemy sie ruszyc (prawy kraniec)

            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT)  // prawy gory rog
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) // prawy dolny rog
            {
                // jesli nie ma kolizji w prawym gornym ani w prawym dolnym rogu, mozesz isc w prawo
                x += xMove;
            } else {
                //naprawa bledu
                x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
            }
        } else if (xMove < 0) {
            // w lewo
            // zwrot
            direction = "W";

            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH; // tx - gdzie chcemy sie ruszyc (lewy kraniec)

            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT)  // lewy gory rog
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))// lewy dolny rog
            {
                // jesli nie ma kolizji w lewym gornym ani w lewym dolnym rogu, mozesz isc w prawo
                x += xMove;
            } else {
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
            }

        }
    }

    // ruch pionowy (gora dol)
    public void moveY() {

        // ** KOLIZJE Y **
        if (yMove < 0) {
            // do gory
            // zwrot
            direction = "N";

            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT; // ty - gdzie chcemy sie ruszyc (gorny kraniec)

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) // lewy gorny rog
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_HEIGHT, ty)) // prawy gorny rog
            {
                // jesli nie ma kolizji w lewym gornym ani w prawym gornym rogu, moesz isc do gory
                y += yMove;
            } else {
                y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
            }
        } else if (yMove > 0) {
            // w dol
            // zwrot
            direction = "S";

            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT; // ty - gdzie chcemy sie ruszyc (dolny kraniec)

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) // lewy dolny rog
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_HEIGHT, ty)) // prawy dolny rog
            {
                // jesli nie ma kolizji w lewym dolnym ani w prawym dolnym rogu, mozesz isc w dol
                y += yMove;
            } else {
                y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
            }
        }
    }

    // spowolnienie kiedy kreatura wejdzie do wody
    public void speedInWater(int depth, float amount) {
        if (inWater((int) (x + bounds.x + bounds.width / 2) / Tile.TILE_WIDTH,
                (int) (y + bounds.y + bounds.height / 2) / Tile.TILE_HEIGHT) >= depth
                && inWater((int) (x + bounds.x + bounds.width / 2) / Tile.TILE_WIDTH,
                (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT) >= depth) {
            walkSpeed = walkSpeed * amount;
        }
    }

    // czy tile na ktory chce wejsc jest solid?
    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    // glebokosc
    protected int inWater(int x, int y) {
        return handler.getWorld().getTile(x, y).getDepth();
    }


    // Getters Setters


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public float getActualSpeed() {
        return actualSpeed;
    }

    public void setActualSpeed(float actualSpeed) {
        this.actualSpeed = actualSpeed;
    }
}
