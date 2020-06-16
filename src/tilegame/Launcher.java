package tilegame;

import tilegame.display.Display;

// ** tylko uruchamianie gry

public class Launcher {


    public static void main(String[] args) {
        Game game = new Game("Gothic2");
        game.start();
    }
}
