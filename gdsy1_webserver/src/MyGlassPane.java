
import javax.swing.JComponent;

import java.awt.*;
import javax.swing.JComponent;

public class MyGlassPane extends JComponent {

    private static final long serialVersionUID = 1L;
    private Font font = new Font("monospaced", Font.PLAIN, 50);
    private int number = 1;

    MyGlassPane() {
        setName("MyGlassPane");
    }

    MyGlassPane(int n) {
        number = n;
        setName("MyGlassPane");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D LayerNumber = (Graphics2D) g;
        LayerNumber.setFont(font);
        LayerNumber.setColor(Color.BLUE);
        LayerNumber.drawString("- " + number + " -", 700, 100);
    }
}
