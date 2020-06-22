package tilegame.input;

import tilegame.ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


// pobiera i przesyla input z myszki

public class MouseManager implements MouseListener, MouseMotionListener {

    // czy wcisnieto lewy, prawy przycisk myszy
    private boolean leftPressed, rightPressed;

    //wpolrzedne X i Y kursora
    private int mouseX, mouseY;

    //
    private UIManager uiManager;

    public MouseManager() {

    }


    // Implemented methods


    // po kazdej zmianie polozenia kursora updatuje jego wspolrzedne
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();

        // jesli do uiManager przypisany jest jakis obiekt, odczytuj eventy myszki
        if (uiManager != null)
            uiManager.onMouseMove(mouseEvent);
    }

    // nacisniecie przycisku
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if (mouseEvent.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;
    }

    // zwolnienie przycisku
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
            leftPressed = false;
        else if (mouseEvent.getButton() == MouseEvent.BUTTON3)
            rightPressed = false;

        //slucha tylko zwolnienia przycisku myszki
        if (uiManager != null)
            uiManager.onMouseRelease(mouseEvent);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    // getters setters


    public UIManager getUiManager() {
        return uiManager;
    }

    public void setUiManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }


}
