package tilegame.inventory;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.items.Item;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;

    // czy jest uruchomiony
    private boolean active = false;

    // wszystkie przedmioty w danym ekwipunku
    private ArrayList<Item> inventoryItems;

    // parametry i rozmiar okna ekwipunku
    private int invX = 450, invY = 50,
            invWidth = 1020, invHeight = 980,
            invListSpacing = 200,
            invListCenterX = invX + 350, invListCenterY = invY + 1030;

    // ustawienie obrazka i licznika wybranego przedmiotu
    // prawdopodobnie tymczasowe
    private int invImageX = 1100, invImageY = 50, invImageWidth = 400, invImageHeight = 400,
            invCountX = 1350, invCountY = 650;

    // indeks wybranego przedmiotu z listy
    private int selectedItem = 0;

    public Inventory(Handler handler){
        this.handler = handler;
        inventoryItems = new ArrayList<>();

//        addItem(Item.mushroomItem.createNew(4));
//        addItem(Item.coinItem.createNew(3));
    }

    public void tick(){

        // po wcisnieciu 'I' na klawiaturze zmienia sie stan aktywacji (I wlacza i wylacza okno)
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_I))
            active = !active;

        // jesli okno ekwipunku nie jest aktywne, nie wykonuj dalej metody tick
        if(!active)
            return;

        // strzalkami przelacza sie pomiedzy przedmiotami
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP))
            selectedItem--;
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN))
            selectedItem++;

        // zabezpieczenie przed wyjsciem poza zakres listy
        if(selectedItem < 0)
            selectedItem = inventoryItems.size() - 1;
        else if(selectedItem >= inventoryItems.size())
            selectedItem = 0;

//        System.out.println("INVENTORY:");
//        for(Item i : inventoryItems){
//            System.out.println(i.getName() + " " + i.getCount());
//        }
    }

    public void render(Graphics g){

        // jesli okno ekwipunku nie jest aktywne, nie wykonuj dalej tej metody
        if(!active)
            return;

        // render okna ekwipunku
        g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);

        // dlugosc listy
        int len = inventoryItems.size();

        // jesli nie ma przedmiotow to nie probuj ich renderowac
        if(len == 0)
            return;

        // renderowanie przedmiotow z listy w odpowiednich miejscach w oknie ekwipunku
        for(int i = - 4; i < 5; i++){

            // renderuj pozycje w ktorych istnieja jakies przedmioty
            if(selectedItem + i < 0 || selectedItem + i >= len)
                continue;

            // w centralnym miejscu listy wyroznij zaznaczony item
            if(i == 0){
                Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName(),
                        invListCenterX, invListCenterY + i * invListSpacing, true, Color.yellow, Assets.font50);
            }else{ // pozosotale przedmioty
                Text.drawString(g, inventoryItems.get(selectedItem + i).getName(),
                        invListCenterX, invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font50);
            }
        }

        // wybrany przedmiot
        Item item = inventoryItems.get(selectedItem);
        // jego obrazek
        g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
        // jego ilosc
        Text.drawString(g, "" + item.getCount(), invCountX, invCountY, true, Color.white, Assets.font50);
    }

    // INVENTORY METHODS

    // dodaj przedmiot do ekwipunku
    public void addItem(Item item){

        // zwieksza ilosc przedmiotu w odpowiednim indeksie w tablicy
        for(Item i : inventoryItems){
            if(i.getId() == item.getId()){
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        // po czym dodaje go do listy ekwipunku
        inventoryItems.add(item);
    }

    // GETTERS SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
