
import javax.swing.*;

class MyJFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    public Control control;
    private MyContentPane contentPane;
    public MyJLayeredPane layeredPane;
    public JComboBox hostCombobox;
    public JTextField portField,  contentbaseField;
    public JLabel errorLable;
    public JButton actionButton;

    public MyJFrame(Control c) {
        control = c;

        setName("Webserver");
        setTitle("Webserver");
        setResizable(false);
        setBounds(10, 10, 900, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        getGlassPane().setVisible(true);

        contentPane = (MyContentPane) getContentPane();
        layeredPane = (MyJLayeredPane) getLayeredPane();

        hostCombobox = contentPane.MyJPanel.hostCombobox;
        portField = contentPane.MyJPanel.portField;
        contentbaseField = contentPane.MyJPanel.contentbaseField;
        actionButton = contentPane.MyJPanel.actionButton;

        errorLable = contentPane.MyJPanel.ErrorLable;

        hostCombobox.addItemListener(control);
        portField.addActionListener(control);
        contentbaseField.addActionListener(control);
        actionButton.addActionListener(control);

    }

    @Override
    protected JRootPane createRootPane() {
        return new MyJRootPane(this);
    }
}
