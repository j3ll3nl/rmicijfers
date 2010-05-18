
import java.awt.*;
import javax.swing.*;

class MyContentPane extends JPanel {

    private static final long serialVersionUID = 1L;
    public MyJLayeredPane layeredPane;
    public MyJPanel MyJPanel;

    MyContentPane() {
        if (Main.debug) System.out.println("MyContentPane()");
        setName("MyContentPane");
        setLayout(new BorderLayout(1, 1));
        setPreferredSize(new Dimension(900, 500));
        setBackground(Color.BLACK);
        setOpaque(true);

        MyJPanel = new MyJPanel();

        add(MyJPanel, BorderLayout.NORTH);
        add(MyJPanel.ErrorLable, BorderLayout.SOUTH);

    }
}
