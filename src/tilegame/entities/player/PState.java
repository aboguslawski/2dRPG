package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.gfx.MobAnimation;

import java.awt.*;
import java.awt.image.BufferedImage;

// podstawa do klas stanow gracza

public abstract class PState {

    protected Handler handler;
    protected Player player;

    // kazdy stan ma animacje ktora aktualnie tickuje
    protected MobAnimation animation;

    // animacje implementowane w kazdym stanie
    protected MobAnimation idle, walk, run;

    // aktualny stan
    protected static PState currentPState;

    // kazdy stan ma swoja predkosc chodzenia i biegu
    protected float walkSpeed, runSpeed;

    public PState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    }

    // tickuj aktualna animacje stanu
    // pobieraj input specyficzny dla danego stanu
    public void tick() {
        animation.tick();
        getInput();
    }

    // renderuj aktualna animacje dla aktualnego stanu
    public void render(Graphics g) {
        drawPlayer(g, getCurrentAnimationFrame(animation), player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    protected abstract void getInput();

    // funkcja pomagajaca do renderowania
    protected void drawPlayer(Graphics g, BufferedImage img, float x, float y, int width, int height) {
        g.drawImage(img, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    // klatka animacji
    protected BufferedImage getCurrentAnimationFrame(MobAnimation animation) {
        switch (player.getDirection()) {
            case "W":
                return animation.getW().getCurrentFrame();
            case "E":
                return animation.getE().getCurrentFrame();
            case "N":
                return animation.getN().getCurrentFrame();
            case "S":
                return animation.getS().getCurrentFrame();
            case "NW":
                return animation.getNw().getCurrentFrame();
            case "NE":
                return animation.getNe().getCurrentFrame();
            case "SE":
                return animation.getSe().getCurrentFrame();
            default:
                return animation.getSw().getCurrentFrame();
        }
    }

    protected void focusDirection(Entity e) {

        if (e.getX() <= player.getX() - 10 && e.getY() <= player.getY() - 10) {
            player.setDirection("NW");
        } else if (e.getX() >= player.getX() + 10 && e.getY() <= player.getY() - 10) {
            player.setDirection("NE");
        } else if (e.getX() >= player.getX() + 10 && e.getY() >= player.getY() + 10) {
            player.setDirection("SE");
        } else if (e.getX() <= player.getX() - 10 && e.getY() >= player.getY() + 10) {
            player.setDirection("SW");
        } else if (e.getX() < player.getX() + 10 && e.getX() > player.getX() - 10 && e.getY() <= player.getY() - 10) {
            player.setDirection("N");
        } else if (e.getX() >= player.getX() + 10 && e.getY() > player.getY() - 10 && e.getY() < player.getY() + 10) {
            player.setDirection("E");
        } else if (e.getX() < player.getX() + 10 && e.getX() > player.getX() - 10 && e.getY() >= player.getY() + 10) {
            player.setDirection("S");
        } else if (e.getX() <= player.getX() - 10 && e.getY() > player.getY() - 10 && e.getY() < player.getY() + 10) {
            player.setDirection("W");
        } else {
            if (Math.abs(player.getX() - e.getX()) >= Math.abs(player.getY() - e.getY())) {
                if (e.getX() >= player.getX()) {
                    player.setDirection("E");
                } else {
                    player.setDirection("W");
                }
            } else {
                if (e.getY() >= player.getY()) {
                    player.setDirection("S");
                } else {
                    player.setDirection("N");
                }
            }
        }
    }

    // GETTER SETTER

    public static PState getCurrentPState() {
        return currentPState;
    }

    public static void setCurrentPState(PState currentPState) {
        PState.currentPState = currentPState;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(int walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public float getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(int runSpeed) {
        this.runSpeed = runSpeed;
    }
}
