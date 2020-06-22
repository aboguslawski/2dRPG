package tilegame.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

// kazdy graficzny obiekt bedacy czescia UI reagujacy na myszke

public abstract class UIObject {

    // gdzie sie wyswietla
    protected float x, y;

    // w jakich wielkosciach
    protected int width, height;

    // czy kursor znajduje sie na obiekcie
    protected boolean hovering = false;

    // prostokat
    protected Rectangle bounds;

    public UIObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void onClick();

    public void onMouseMove(MouseEvent e) {

        // jesli punkt X i Y myszki znajduja sie wewnatrz prostokata
        if (bounds.contains(e.getX(), e.getY()))
            hovering = true;
        else
            hovering = false;
    }

    public void onMouseRelease(MouseEvent e) {
        if (hovering)
            onClick();
    }


    // GETTERS SETTERS

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
