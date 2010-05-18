
import javax.swing.*;

public class MyJScrollPane extends JScrollPane {

    static int id;
    public MyJTextPane MyJTextPane;

    public MyJScrollPane(int i) {
        id = i;
        setBounds(3, 37, 890, 300);
        MyJTextPane = new MyJTextPane();
        setViewportView(MyJTextPane);

    }

    public int getId() {
        return id;
    }
}
