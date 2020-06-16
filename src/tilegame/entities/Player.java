package tilegame.entities;

import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // ANIMATIONS
    private Animation animDown, animUp, animLeft, animRight;

    private boolean sword;

    public Player(Handler handler, float x, float y, int health) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.sword = false;

        // wspolrzedne kolizji gracza
        // bez tego pokryje sie caly obraz gracza
        bounds.x = 8;
        bounds.y = 80;
        bounds.width = 48;
        bounds.height = 48;

        // Animation
        animDown = new Animation(200, Assets.player_down);
        animUp = new Animation(200, Assets.player_up);
        animLeft = new Animation(200, Assets.player_left);
        animRight = new Animation(200, Assets.player_right);

    }

    @Override
    public void tick() {
        //Animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        //Movement
        getInput();
        move(); // metoda z klasy Creature
        handler.getGameCamera().centerOnEntity(this); //wycentruj kamere na graczu
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if(handler.getKeyManager().up){
            yMove = -speed;
        }
        if(handler.getKeyManager().down){
            yMove = speed;
        }
        if(handler.getKeyManager().left){
            xMove = -speed;
        }
        if(handler.getKeyManager().right){
            xMove += speed;
        }
        if(handler.getKeyManager().run){
            this.speed = 5;
        }else this.speed = Creature.DEFAULT_SPEED;
        if(handler.getKeyManager().sword){
            this.sword = true;
        }else this.sword = false;
    }

    @Override
    public void render(Graphics g) {
        if(this.sword)
            drawPlayer(g, Assets.playerSword, x, y, width, height);
        else
            drawPlayer(g, getCurrentAnimationFrame(), x, y, width, height);

        // zamalowanie prostokata kolizji dla widocznosci
//        g.setColor(Color.red);
//        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//                (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
//                bounds.width, bounds.height);
    }

    private void drawPlayer(Graphics g, BufferedImage img, float x, float y, int width, int height){
        g.drawImage(img, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame(){
        if(xMove < 0){
            return animLeft.getCurrentFrame();
        }
        else if(xMove > 0){
            return animRight.getCurrentFrame();
        }
        else if(yMove < 0){
            return animUp.getCurrentFrame();
        }
        else return animDown.getCurrentFrame();
    }
}
