
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;

public class MyJPanel extends JPanel {

    private JLabel hostLable,  portLable,  contentbaseLable;
    public JLabel ErrorLable;
    public JComboBox hostCombobox;
    public JTextField portField,  contentbaseField;
    public JButton actionButton;

    public MyJPanel() {
        setLayout(new FlowLayout());
        setSize(900, 35);

        hostLable = new JLabel();
        hostLable.setName("hostLable");
        this.add(hostLable);
        hostLable.setText("Host: ");

        InetAddress[] adress = null;
        try {
            adress = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ex) {
        } finally{}

        ComboBoxModel hostListModel = new DefaultComboBoxModel(adress);
        hostCombobox = new JComboBox();
        hostCombobox.setName("host");
        this.add(hostCombobox);
        hostCombobox.setModel(hostListModel);
        hostCombobox.setSelectedItem(adress);
        //hostCombobox.addItemListener(control);

        portLable = new JLabel();
        portLable.setName("portLable");
        this.add(portLable);
        portLable.setText("Port: ");

        portField = new JTextField("1337");
        portField.setName("portField");
        this.add(portField);
        portField.setColumns(4);

        contentbaseLable = new JLabel();
        contentbaseLable.setName("contentbaseLable");
        this.add(contentbaseLable);
        contentbaseLable.setText("ContentBase: ");

        contentbaseField = new JTextField("G:/HU/Jaar 2/GDSY/"); // Nu kan je bij de contentbase Control.contentbase
        contentbaseField.setName("contentbaseField");
        this.add(contentbaseField);
        contentbaseField.setColumns(23);

        actionButton = new JButton("Start");
        actionButton.setBackground(Color.GREEN);
        actionButton.setName("actionButton");
        this.add(actionButton);

        ErrorLable = new JLabel("Webserver (Geen meldingen)");
        ErrorLable.setForeground(Color.WHITE);
        ErrorLable.setName("errorlable");
    }
}
