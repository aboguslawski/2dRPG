package tilegame.entities.mobs;

import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.Entity;
import tilegame.entities.Player;
import tilegame.gfx.Assets;

import java.awt.*;
import java.util.ArrayList;

// oznacza i zwraca obiekty ktore maja attackable == true
// aktywowany jest kiedy gracz wejdzie w stan walki (wyciagnie bron)

public class AttackHover {

    private Handler handler;

    // teren sprawdzany jest wokol gracza
    private Player player;

    // zasieg sprawdzanego terenu w pixelach
    private int pixelRange;

    // moze kiedys to bedzie mialo zastosowanie
    private boolean on;

    // lista obiektow w zasiegu ktora jest caly czas aktualizowana
    private ArrayList<Entity> attackableEntitiesInRange;

    // aktualnie oznaczony obiekt ktory jest zwracany przez ta klase
    private Entity hovered;

    // rzeczy do timowania co ile ma sprawdzac obecnosc obiektu
    private int refreshSpeed, j; // j - indeks w liscie staticEntitiesInRange
    private long timer, lastTime;
    // aktualnie 250ms

    public AttackHover(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        this.pixelRange = 500;
        this.refreshSpeed = 250;
        this.j = 0;
        attackableEntitiesInRange = new ArrayList<>();
        this.hovered = null;
        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    // sprawdzanie obszaru i aktualizowanie listy attackableEntitiesInRange
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer >= refreshSpeed)
            attackableEntitiesInRange = checkForAttackableEntities();

        // hovered oznacza i zwraca aktualnie wybrany obiekt na pozycji j w liscie
        if (!attackableEntitiesInRange.isEmpty()) {
            hovered = attackableEntitiesInRange.get(j % attackableEntitiesInRange.size());
        }
        // jesli nie ma zadnego obiektu to nie zwracaj nic
        else hovered = null;
    }

    // jesli na liscie znajduje sie jakis obiekt renderuje pod wybranym obiektem czerwony hover
    public void render(Graphics g) {


        if (!attackableEntitiesInRange.isEmpty()) {
            g.drawImage(Assets.attackHover, (int) (hovered.getX() - handler.getGameCamera().getxOffset()),
                    (int) (hovered.getY() + hovered.getHeight() - handler.getGameCamera().getyOffset()), null);
        }
    }


    // stara funkcja

    // przeszukuje kwadratowy teren wokol gracza w zasiegu i
    // zwraca liste aktualnie znajdujacych sie w tym terenie obiektow do zaatakowania
    private ArrayList<Entity> checkForAttackableEntities2() {
        ArrayList<Entity> entities = new ArrayList<>();

        // max (0,x) zapobiega sprawdzaniu poza granicami mapy
        int xStart = Math.max(0, (int) player.getX() - pixelRange);
        int xEnd = (int) player.getX() + pixelRange;
        int yStart = Math.max(0, (int) player.getY() - pixelRange);
        int yEnd = (int) player.getY() + pixelRange;

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {

                // sprawdza czy na danych kordach (i, j) znajduje sie jakis obiekt
                // w przypadku gdy znajdzie obiekt zwraca go
                // w przeciwnym przypadku zwraca null
                Entity e = handler.getWorld().getEntityManager().getEntity(i, j);

                // jesli zwrocono obiekt niebedacy nullem i mozna wejsc z nim w interakcje
                // dodaj go do zwracanej listy
                if (e != null && e.isAttackable()) {
                    entities.add(e);
                }
            }
        }
        return entities;
    }

    private ArrayList<Entity> checkForAttackableEntities() {

        // array list ktora zostanie zwrocona
        ArrayList<Entity> attackableEntities = new ArrayList<>();

        // wszystkie obiekty ze swiata
        ArrayList<Entity> worldEntities = handler.getWorld().getEntityManager().getEntities();

        // koordy ekranu
        int xStart = (int) (handler.getGameCamera().getxOffset());
        int xEnd = (int) (handler.getGameCamera().getxOffset() + Display.SCREEN_WIDTH);
        int yStart = (int) (handler.getGameCamera().getyOffset());
        int yEnd = (int) (handler.getGameCamera().getyOffset() + Display.SCREEN_HEIGHT);

        // loopuj wszystkie obiekty
        for (Entity e : worldEntities) {

            // jesli jest atakowalny
            if(e.isAttackable()){

                // jesli znajduje sie w obszarze ekranu dodaj do zwracanej arraylist
                if (e.getX() >= xStart && e.getX() <= xEnd
                        && e.getY() >= yStart && e.getY() <= yEnd){
                    attackableEntities.add(e);
                }
            }
        }
        return attackableEntities;
    }

    // nastepny indeks
    public void nextEntity() {
        j++;
    }

    // poprzedni indeks
    public void prevEntity() {

        // jesli aktualnie wskazuje na 0 to zamiast dawac wartosci ujemne to wskakuje na koniec listy
        if (attackableEntitiesInRange.size() > 0) {
            if (j == 0) j = attackableEntitiesInRange.size() - 1;
            else j--;
        }

    }

    // GETTERS SETTERS

    public Entity getHovered() {
        return hovered;
    }

}
