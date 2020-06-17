package tilegame.entities;

import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // ANIMATIONS
    private Animation animS, animN, animW, animE, animNW, animNE, animSE, animSW;

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
        animS = new Animation(200, Assets.playerS);
        animN = new Animation(200, Assets.playerN);
        animW = new Animation(200, Assets.playerW);
        animE = new Animation(200, Assets.playerE);
        animNW = new Animation(200,Assets.playerNW);
        animNE = new Animation(200,Assets.playerNE);
        animSE = new Animation(200,Assets.playerSE);
        animSW = new Animation(200,Assets.playerSW);

    }

    @Override
    public void tick() {
        //Animations
        animS.tick();
        animN.tick();
        animW.tick();
        animE.tick();
        animNW.tick();
        animNE.tick();
        animSE.tick();
        animSW.tick();
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

    // ANIMACJA
    private BufferedImage getCurrentAnimationFrame(){
        if(this.direction.equals("W")) return animW.getCurrentFrame();
        else if(this.direction.equals("E")) return animE.getCurrentFrame();
        else if(this.direction.equals("N")) return animN.getCurrentFrame();
        else if(this.direction.equals("S")) return animS.getCurrentFrame();
        else if(this.direction.equals("NW")) return animNW.getCurrentFrame();
        else if(this.direction.equals("NE")) return animNE.getCurrentFrame();
        else if(this.direction.equals("SE")) return animSE.getCurrentFrame();
        else return animSW.getCurrentFrame();
    }
}
