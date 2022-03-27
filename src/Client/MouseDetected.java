package client;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
> An abstract adapter class for receiving mouse events. The methods in this class are empty. This class exists as
  convenience for creating listener objects.
> Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a component,
  when it exits and when a mouse wheel is moved.
*/
public class MouseDetected extends MouseAdapter{

    private final Cursor cursor;
    private final Cursor cursorClicked;
    private final Interfaz inter;

    public MouseDetected(Cursor cursor, Cursor cursorClicked, Interfaz inter){
        this.cursor = cursor;
        this.cursorClicked = cursorClicked;
        this.inter = inter;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        inter.setCursor(cursorClicked);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        inter.setCursor(cursor);
    }

}
