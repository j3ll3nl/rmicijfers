
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

class MyJLayeredPane extends JLayeredPane implements MouseWheelListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private int currentLocation = 0;
    private ArrayList<MyJScrollPane> scrollPanes;

    public MyJLayeredPane() {
        setName("MyLayeredPane");
        addMouseWheelListener(this);

        this.scrollPanes = new ArrayList<MyJScrollPane>();
    }

    public void addInLayer(MyJScrollPane p) {
        add(p, JLayeredPane.DEFAULT_LAYER);
        this.scrollPanes.add(p);
    }

    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int size = this.scrollPanes.size();

        if (size == 1) return; // verlaat methode als er nog geen panes zijn om te scrollen

        MyJRootPane MyRootpane = (MyJRootPane) getParent();

        int rotation = mwe.getWheelRotation();

        this.currentLocation += rotation;

        if (this.currentLocation < 0)
            this.currentLocation = size-1;
        else if (this.currentLocation > size-1)
            this.currentLocation = 0;

        //if (Main.debug) System.out.println("Rotation: " + rotation + "\n Currentlocation: " + currentLocation + "\nTop: " + top + "\n");

        Component c = this.scrollPanes.get(this.currentLocation);
        moveToFront(c);

        MyRootpane.setGlassPane(new MyGlassPane(this.currentLocation+1));
        MyRootpane.createGlassPane();
        MyRootpane.updateUI();

    }

    public void actionPerformed(ActionEvent e) {
        if (Main.debug) System.out.println(e.toString());
    }
}
