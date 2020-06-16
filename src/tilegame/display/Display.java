package tilegame.display;

import javax.swing.*;
import java.awt.*;

// ** glowny ekran **

public class Display {

    public static int SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;

    private JFrame frame;
    private Canvas canvas; // obrazy


    public Display(String title){
        createDisplay(title);
    }

    private void createDisplay(String title){
        frame = new JFrame(title);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true); //usuwa ramki okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        canvas.setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        canvas.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
        frame.requestFocus();
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }

}
