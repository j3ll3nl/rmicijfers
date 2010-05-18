
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Control implements ActionListener, ItemListener {

    private Thread thread = null;
    private Server server = null;
    public static String contentbase;
    public String logs;
    private ArrayList<MyJScrollPane> ScrollPanes;
    private MyJFrame Gui;
    

    public Control() {

        ScrollPanes = new ArrayList<MyJScrollPane>();
        Gui = new MyJFrame(this);
        Gui.setVisible(true);

        log(1, "1337SERV V0.1\n");

    }

    public void doStart() throws Exception {
        log(0, "De server probeert te starten");
        log(1, "De server probeert te starten");
        try {
            server = new Server(this,(InetAddress) Gui.hostCombobox.getSelectedItem(), Integer.parseInt(Gui.portField.getText()), Gui.contentbaseField.getText());
            log(1, server.toString());
            thread = new Thread(server);
            log(1, thread.toString());
            thread.start();
            log(1, "De webserver is gestart");
            log(0, "De webserver is gestart");
        } catch (Exception e) {
            log(0, e.getMessage());
        } finally{}

    }

    public void doStop() {
        log(0, "De webserver probeert te stoppen.");
        log(1, "De webserver probeert te stoppen.");

        if (Main.debug) System.out.println("Debug: Control doStop(): Webserver probeert te stoppen."); //debug regel die alleen weergegeven word als Main.debug op true staat

        try {
            while (!server.threadsClosed()) {server.closeThreads();}
            server.close();
            System.gc();
            log(0, "De webserver is gestopt.");
            log(1, "De webserver is gestopt.");
        } catch (Exception e) {
            log(1, e.getMessage());
            log(0, e.getMessage());
        } finally{}
    }

    public int log(String message) {
        int i = this.ScrollPanes.size() +1;
        log(i,message);
        return i;
    }

    public void log(int i, String message) {
        Date d = new Date();
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("H:mm:ss",Locale.UK);

        message = "[" + formatter.format(d) + "]\t" + message;
        if (i > 0) {
            try {
                MyJScrollPane scrollPane = ScrollPanes.get(i - 1);
                scrollPane.MyJTextPane.append(message);
            } catch (IndexOutOfBoundsException e) {
                MyJScrollPane scrollPane = new MyJScrollPane(i);
                scrollPane.MyJTextPane.setText(message);
                ScrollPanes.add(scrollPane);
                Gui.layeredPane.addInLayer(scrollPane);
            } finally{}
        } else {
            Gui.errorLable.setText(message);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Gui.actionButton) {
            if (Gui.actionButton.getText().equals("Start")) {
                Gui.actionButton.setText("Stop");
                Gui.actionButton.setBackground(Color.RED);
                try {
                    doStart();
                } catch (Exception ex) {
                    log(0, ex.getMessage());
                } finally{}
            } else if (Gui.actionButton.getText().equals("Stop")) {
                Gui.actionButton.setText("Start");
                Gui.actionButton.setBackground(Color.GREEN);
                try {
                    doStop();
                } catch (Exception ex) {
                    log(0, ex.getMessage());
                } finally{}
            }

        }
        if (e.getSource() == Gui.contentbaseField) {
            if (Main.debug) System.out.println(e.getActionCommand());
        }

        if (e.getSource() == Gui.portField) {
            if (Main.debug) System.out.println(e.getActionCommand());
        }

    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == Gui.hostCombobox) {
            log(0, "Host is gewijzigd in '" + e.getItem() + "'");
        }
    }
}
