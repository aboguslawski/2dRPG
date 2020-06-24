package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// *** input ***

public class KeyManager implements KeyListener {

    // w miejscu keys[keycode] zmienia sie na true kiedy klawisz z danym keycode jest wywolany

    private boolean[] keys;
    private boolean[] pressKeys;
    private boolean[] clickKeys;
    public boolean up, down, left, right, run, sword, prevEntity, nextEntity, interactWithEntity, attack;

    public KeyManager(){
        keys = new boolean[512];
        pressKeys = new boolean[512];
        clickKeys = new boolean[512];
    }

    public void tick(){
        // ruch
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];

        // aktywacja sprintu
        run = keys[KeyEvent.VK_SHIFT];

        // wyciagniecie i schowanie broni
        sword = pressKeys[KeyEvent.VK_1];

        // zmiana obiektu
        prevEntity = keys[KeyEvent.VK_Q];
        nextEntity = keys[KeyEvent.VK_E];

        // interakcja z obiektem
        interactWithEntity = keys[KeyEvent.VK_F];

        // atak
        attack = keys[KeyEvent.VK_A];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
        if(!pressKeys[keyEvent.getKeyCode()]) pressKeys[keyEvent.getKeyCode()] = true;
        else pressKeys[keyEvent.getKeyCode()] = false;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }
}
