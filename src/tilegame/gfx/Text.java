package tilegame.gfx;

import java.awt.*;

//wypisuje tekst na ekranie we wskazanym miejscu, wskazanym kolorze i czcionce
public class Text {

    public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color color, Font font){
        g.setColor(color);
        g.setFont(font);
        int x = xPos;
        int y = yPos;
        if(center){
            FontMetrics fm = g.getFontMetrics(font);
            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight()) / 2 + fm.getAscent();
        }
        g.drawString(text, x, y);

    }
}
