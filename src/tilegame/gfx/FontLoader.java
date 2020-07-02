package tilegame.gfx;

import java.awt.*;
import java.io.File;
import java.io.IOException;

// klasa ladujaca czcionke z danego pliku i nadajaca jej wybrany rozmiar
public class FontLoader {

    public static Font loadFont(String path, float size){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}