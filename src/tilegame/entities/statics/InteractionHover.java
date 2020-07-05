package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.player.Player;
import tilegame.gfx.Assets;

import java.awt.*;
import java.util.ArrayList;

// znacznik statycznych obiektow
// oznacza statyczny obiekt z ktorym
// mozna wejsc w interakcje i znajduje sie w zasiegu
// pozwala zmienic wybrany obiekt <Q>
// pozwala przeprowadzic na nim interakcje <E>

public class InteractionHover {

    private Handler handler;

    // gracz jest centrum przeszukiwanego obszaru
    private Player player;

    // zasieg wykrywania
    private int pixelRange;

    // to chyba nic nie robi
    private boolean on;

    // lista obiektow w zasiegu
    private ArrayList<Entity> staticEntitiesInRange;

    // obiekt aktualnie oznaczony
    private Entity hovered;

    // rzeczy do timowania co ile ma sprawdzac obecnosc obiektu
    private int refreshSpeed, j; // j - indeks w liscie staticEntitiesInRange
    private long timer, lastTime;
    // aktualnie 250ms

    public InteractionHover(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        this.pixelRange = 80;
        this.on = false;
        this.refreshSpeed = 250;
        this.j = 0;
        staticEntitiesInRange = new ArrayList<>();
        this.hovered = null;
        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer >= refreshSpeed)

            // przeszukuje i zwraca tablice dostepnych obiektow
            staticEntitiesInRange = checkForStaticEntities();

        // jesli jakis obiekt znajduje sie na liscie
        if (!staticEntitiesInRange.isEmpty()) {
            if (j < 0 || j >= staticEntitiesInRange.size())
                j = 0;

            // zwroc obiekt znajdujacy sie na j miejscu w liscie
            hovered = staticEntitiesInRange.get(j);
        }
        // jesli nie ma zadnego obiektu to nie zwracaj nic
        else hovered = null;

    }

    public void render(Graphics g) {

        // jesli na liscie znajduje sie jakis obiekt -> jakis obiekt jest oznaczony
        // to podswietlenie tego obiektu
        if (!staticEntitiesInRange.isEmpty())
            g.drawImage(Assets.interactionHover, (int) (hovered.getX() + hovered.getWidth() / 2 - Assets.interactionHover.getWidth() / 2 - handler.getGameCamera().getxOffset()),
                    (int) (hovered.getY() + hovered.getHeight() + 10 - handler.getGameCamera().getyOffset()), null);
    }

    // wrzuca wszystkie obiekty w zasiegu do listy i zwraca ta liste
    private ArrayList<Entity> checkForStaticEntities() {
        ArrayList<Entity> staticEntities = new ArrayList<>();

        // max (0,x) zapobiega sprawdzaniu poza granicami mapy
        int xStart = Math.max(0, (int) player.getX() - pixelRange);
        int xEnd = (int) player.getX() + player.getWidth() + pixelRange;
        int yStart = Math.max(0, (int) player.getY() - pixelRange);
        int yEnd = (int) player.getY() + player.getHeight() + pixelRange;

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {

                // sprawdza czy na danych kordach (i, j) znajduje sie jakis obiekt
                // w przypadku gdy znajdzie obiekt zwraca go
                // w przeciwnym przypadku zwraca null
                Entity e = handler.getWorld().getEntityManager().getEntity(i, j);

                // jesli zwrocono obiekt niebedacy nullem i mozna wejsc z nim w interakcje
                // dodaj go do zwracanej listy
                if (e != null && e.isInteraction()) {
                    staticEntities.add(e);
                }
            }
        }
        return staticEntities;
    }

    // nastepny indeks
    public void nextEntity() {
        j++;
        if (j >= staticEntitiesInRange.size())
            j = 0;
    }

    // poprzedni indeks
    public void prevEntity() {
        j--;
        if (j < 0) {
            j = staticEntitiesInRange.size() - 1;
        }
    }

    // GETTERS SETTERS

    public Entity getHovered() {
        return hovered;
    }
}
