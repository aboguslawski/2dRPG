package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// *** input ***

public class KeyManager implements KeyListener {

    // w miejscu keys[keycode] zmienia sie na true kiedy klawisz z danym keycode jest wywolany

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, shift, n1, q, e, f, i;
    public boolean space, w, a, d, s;

    public KeyManager() {
        keys = new boolean[512];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void tick() {

        // petla ktora sprawia ze kazda tablica dziala jak powinna
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        // ruch
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];

        // aktywacja sprintu
        shift = keys[KeyEvent.VK_SHIFT];

        // wyciagniecie i schowanie broni
        n1 = justPressed[KeyEvent.VK_1];

        // zmiana obiektu
        q = justPressed[KeyEvent.VK_Q];
        e = justPressed[KeyEvent.VK_E];

        // interakcja z obiektem
        f = justPressed[KeyEvent.VK_F];

        i = justPressed[KeyEvent.VK_I];

        // walka
        space = keys[KeyEvent.VK_SPACE];
        w = justPressed[KeyEvent.VK_W];
        a = justPressed[KeyEvent.VK_A];
        d = justPressed[KeyEvent.VK_D];
        s = keys[KeyEvent.VK_S];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() < 0 || keyEvent.getKeyCode() >= keys.length)
            return;
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() < 0 || keyEvent.getKeyCode() >= keys.length)
            return;
        keys[keyEvent.getKeyCode()] = false;
    }

    // funkcja sprawdzajaca wcisniety klawisz
    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }
}
