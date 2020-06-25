package tilegame.entities.mobs;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.player.Player;
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

    private int j; // j - indeks w liscie staticEntitiesInRange

    private int xStart, xLen, yStart, yLen;

    public AttackHover(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        this.pixelRange = 750;
        this.j = 0;
        attackableEntitiesInRange = new ArrayList<>();
        this.hovered = null;

        setBounds();
    }

    // sprawdzanie obszaru i aktualizowanie listy attackableEntitiesInRange
    public void tick() {
        setBounds();

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


        g.setColor(Color.cyan);
        g.drawRect(xStart, yStart, xLen, yLen);
    }


    // stara funkcja

    // przeszukuje kwadratowy teren wokol gracza w zasiegu i
    // zwraca liste aktualnie znajdujacych sie w tym terenie obiektow do zaatakowania
    private ArrayList<Entity> checkForAttackableEntitiesOld() {
        ArrayList<Entity> entities = new ArrayList<>();

        // max (0,x) zapobiega sprawdzaniu poza granicami mapy

        for (int i = xStart; i <= xLen; i++) {
            for (int j = yStart; j <= yLen; j++) {

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

    // nowa funkcja

    // zwraca liste obiektow do zaatakowania
    // sprawdza kazdy obiekt i zwraca te mieszczace sie w granicach zasiegu i mozliwe do zaatakowania
    private ArrayList<Entity> checkForAttackableEntities() {

        // array list ktora zostanie zwrocona
        ArrayList<Entity> attackableEntities = new ArrayList<>();

        // wszystkie obiekty ze swiata
        ArrayList<Entity> worldEntities = handler.getWorld().getEntityManager().getEntities();

        // koordy ekranu


        // loopuj wszystkie obiekty
        for (Entity e : worldEntities) {

            // jesli jest atakowalny
            if (e.isAttackable()) {

                Rectangle hoverBounds = new Rectangle(xStart, yStart, xStart + xLen, yStart + yLen);
                Rectangle entityBounds = new Rectangle((int) (e.getX() - handler.getGameCamera().getxOffset()),
                        (int) (e.getY() - handler.getGameCamera().getyOffset()), e.getWidth(), e.getHeight());

                // jesli znajduje sie w obszarze ekranu dodaj do zwracanej arraylist
                if (hoverBounds.intersects(entityBounds)) {
                    attackableEntities.add(e);
                }
            }
        }
        return attackableEntities;
    }

    // nastepny indeks
    public void nextEntity() {
        if (j >= attackableEntitiesInRange.size() - 1) j = 0;
        else j++;
    }

    // poprzedni indeks
    public void prevEntity() {

        // jesli aktualnie wskazuje na 0 to zamiast dawac wartosci ujemne to wskakuje na koniec listy
        if (attackableEntitiesInRange.size() > 0) {
            if (j <= 0) j = attackableEntitiesInRange.size() - 1;
            else j--;
        }

    }

    private void setBounds() {
        xStart = (int) (player.getX() - handler.getGameCamera().getxOffset() - pixelRange);
        yStart = (int) (player.getY() - handler.getGameCamera().getyOffset() - pixelRange / 2);
        xLen = player.getWidth() + 2 * pixelRange;
        yLen = player.getHeight() + 2 * pixelRange / 2;
    }

    // GETTERS SETTERS

    public Entity getHovered() {
        return hovered;
    }

}
