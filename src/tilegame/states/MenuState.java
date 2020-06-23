package tilegame.states;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;

import java.awt.*;

// menu - pierwsze co wyswietla sie po uruchomieniu gry

public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        // dodanie przycisku
        uiManager.addObject(new UIImageButton(200, 200, 500, 200, Assets.btStart, new ClickListener() {
            @Override
            public void onClick() {
                // dezaktywuj UIManager
                handler.getMouseManager().setUiManager(null);

                // zmien stan z menu na gre
                State.setState(handler.getGame().gameState);
            }
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();

        // wejdz od razu do gamestate
        handler.getMouseManager().setUiManager(null);
        State.setState(handler.getGame().gameState);
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}
