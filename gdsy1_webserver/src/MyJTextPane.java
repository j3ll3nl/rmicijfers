
import java.awt.Dimension;
import javax.swing.JTextPane;

public class MyJTextPane extends JTextPane {

    public MyJTextPane() {
        setPreferredSize(new Dimension(890, 300));
    }

    public void append(String message) {
        message = getText() + "\n" + message;
        setText(message);
    }
}
