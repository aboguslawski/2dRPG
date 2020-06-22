package tilegame.ui;

import tilegame.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//identyczne dzialanie do EntityManager

public class UIManager {

    private Handler handler;

    // lista obiektow UI
    private ArrayList<UIObject> objects;

    public UIManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
    }

    public void tick() {
        for (UIObject o : objects) {
            o.tick();
        }
    }

    public void render(Graphics g) {
        for (UIObject o : objects) {
            o.render(g);
        }
    }

    public void onMouseMove(MouseEvent e) {
        for (UIObject o : objects) {
            o.onMouseMove(e);
        }
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIObject o : objects) {
            o.onMouseRelease(e);
        }
    }

    // dodaje obiekt do listy
    public void addObject(UIObject o) {
        objects.add(o);
    }

    // usuwa obiekt z listy
    public void removeObject(UIObject o) {
        objects.remove(o);
    }

    // GETTERS SETTERS


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<UIObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<UIObject> objects) {
        this.objects = objects;
    }
}
