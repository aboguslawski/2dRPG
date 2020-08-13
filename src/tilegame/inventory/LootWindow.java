package tilegame.inventory;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.items.Item;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// okno lootu obslugujace przeslana przez gracza skrzynie

public class LootWindow {

    private Handler handler;

    // przedmioty znajdujace sie w przeslanej przez gracza ogladanej skrzyni
    private ArrayList<Item> lootItems;

    // okno aktywuje sie wylaczeni po wejsciu w interakcje ze skrzynia
    private boolean active = false;

    // przedmiot aktualnie wybrany w oknie
    private int selectedItem = 0;

    // ustawienie, szerokosc, dlugosc, odstepy pomiedzy miejscami na przedmioty, wspolrzedne srodkowego elementu
    private int lootX = 200, lootY = 50,
            lootWidth = 1080, lootHeight = 300,
            lootSpacing = 200,
            lootListCenterX = 600, lootListCenterY = lootY + 50;

    public LootWindow(Handler handler) {
        this.handler = handler;
        this.lootItems = new ArrayList<>();
    }

    public void tick() {

        // jesli gracz nie jest w interakcji z zadna skrzynia, wyjdz
        if (handler.getWorld().getEntityManager().getPlayer().getLooting() == null){
            active = false;
            return;
        }

        // w przeciwnym przypadku aktywuj okno i przejdz do wykonywania reszty instrukcji
        active = true;

        // updatowanie przedmiotow widocznych w oknie na podstawie przedmiotow znajdujacych sie w skrzyni
        this.lootItems = handler.getWorld().getEntityManager().getPlayer().getLooting().getContent();

        // lewo-prawo poruszanie sie po przedmiotach w oknie
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT))
            selectedItem--;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT))
            selectedItem++;

        // zabezpieczenie przed wyjsciem poza zakres listy
        if (selectedItem < 0)
            selectedItem = lootItems.size() - 1;
        else if (selectedItem >= lootItems.size())
            selectedItem = 0;

        // wyciagniecie przedmiotu ze skrzyni i umieszczenie go w ekwipunku gracza
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)){
            try{
                handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(lootItems.get(selectedItem));
                lootItems.remove(selectedItem);
            } catch (IndexOutOfBoundsException ignored){}
        }
    }

    public void render(Graphics g) {

        // jesli gracz aktualnie nic nie lootuje to nie renderuj nic
        if (handler.getWorld().getEntityManager().getPlayer().getLooting() == null)
            return;

        // okno
        g.drawImage(Assets.lootScreen, lootX, lootY, lootWidth, lootHeight, null);

        // dlugosc listy
        int len = lootItems.size();

        // jesli nie ma przedmiotow to nie probuj ich renderowac
        if (len == 0)
            return;

        // wyswietlanie
        for (int i = -2; i < 3; i++) {

            // renderuj pozycje w ktorych istnieja jakies przedmioty
            if (selectedItem + i < 0 || selectedItem + i >= len)
                continue;

            // w centralnym miejscu listy wyroznij zaznaczony item
            g.drawImage(lootItems.get(selectedItem + i).getTexture().getScaledInstance(200, 200, 0),
                    lootListCenterX + i * lootSpacing, lootListCenterY, null);

            // ilosc
            Text.drawString(g, "" + lootItems.get(selectedItem + i).getCount(),
                    lootListCenterX + i * lootSpacing, lootListCenterY, true, Color.white, Assets.font50);
        }

    }

    // GETTERS SETTERS

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
}
