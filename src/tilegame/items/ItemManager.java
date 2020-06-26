package tilegame.items;

import tilegame.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;

    public ItemManager(Handler handler){
        this.handler = handler;
        items = new ArrayList<Item>();

        init();
    }

    private void init(){
        addItem(Item.mushroomItem.createNew(200, 400));
    }

    public void tick(){
        Iterator<Item> it = items.iterator();
        while(it.hasNext()){
            Item i = it.next();
            i.tick();
            if(i.getCount() == Item.PICKED_UP)
                it.remove();
        }
    }

    public void render(Graphics g){
        for(Item i : items)
            i.render(g);
    }

    public void addItem(Item i){
        i.setHandler(handler);
        items.add(i);
    }

    // GETTERS SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
