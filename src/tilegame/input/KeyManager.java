package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

// *** input ***

public class KeyManager implements KeyListener {

    // w miejscu keys[keycode] zmienia sie na true kiedy klawisz z danym keycode jest wywolany

    private boolean[] keys;
    private boolean[] pressKeys;
    private boolean[] clickKeys;
    public boolean up, down, left, right, run, sword, q;

    public KeyManager(){
        keys = new boolean[256];
        pressKeys = new boolean[256];
        clickKeys = new boolean[256];
    }

    public void tick(){
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        run = keys[KeyEvent.VK_SHIFT];
        sword = pressKeys[KeyEvent.VK_1];
        q = keys[KeyEvent.VK_Q];
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
