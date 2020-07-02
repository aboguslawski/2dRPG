package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// *** input ***

public class KeyManager implements KeyListener {

    // w miejscu keys[keycode] zmienia sie na true kiedy klawisz z danym keycode jest wywolany

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, run, sword, prevEntity, nextEntity, interactWithEntity;
    public boolean attack, atForward, atLeft, atRight, block;

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
        run = keys[KeyEvent.VK_SHIFT];

        // wyciagniecie i schowanie broni
        sword = justPressed[KeyEvent.VK_1];

        // zmiana obiektu
        prevEntity = justPressed[KeyEvent.VK_Q];
        nextEntity = justPressed[KeyEvent.VK_E];

        // interakcja z obiektem
        interactWithEntity = justPressed[KeyEvent.VK_F];

        // walka
        attack = keys[KeyEvent.VK_SPACE];
        atForward = justPressed[KeyEvent.VK_W];
        atLeft = justPressed[KeyEvent.VK_A];
        atRight = justPressed[KeyEvent.VK_D];
        block = keys[KeyEvent.VK_S];
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
