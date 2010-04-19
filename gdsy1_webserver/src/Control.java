import java.awt.Color;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.BindException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.io.PrintStream;
import java.io.File;

/*
 This file is part of Diagnostic Webserver.

 Diagnostic Webserver is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Diagnostic Webserver is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Diagnostic Webserver.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2008, Henze Berkheij & Mark van de Haar
 */

/**
 * This class instantiates the {@link GUI_Frame} and controls all webserver
 * activities. This class is instantiated by Main.
 **/

public class Control implements ActionListener, ItemListener, WindowListener {

	private Thread thread = null;
	private Server server = null;
	private InetAddress address;
	private int port = 4711;
	private String codeBase;
	private String version;
	private GUI_Frame frame;

	/**
	 * This creates an instance of this class.
	 * 
	 * @param version
	 *            will contain the version of the software
	 */
	public Control(String v) {
		version = v;
		frame = new GUI_Frame(this, version);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		System.setOut(new PrintStream(new MyOutputStream(frame)));
		System.out.println("Welcome to the Diagnostic Webserver...");
	}

	/**
	 * By pressing the the button Start/Stop, this function will execute the
	 * function {@link #start()} or {@link #stop()} and changes the color of the
	 * button
	 */
	public void doButton(JButton b) {
		if (b.getText() == "Start") {
			if (this.start()) {
				b.setBackground(Color.red);
				b.setText("Stop");
				frame.setError("");
			}

		} else if (b.getText() == "Stop") {

			if (this.stop()) {
				b.setBackground(Color.green);
				b.setText("Start");
				frame.setError("");
			}
		}
	}

	/**
	 * When you select an item from the list, this function will be executed. If
	 * needed, it will restart the the server by calling "{@link #restart()}".
	 */
	public void doChoice(JComboBox c) {
		address = (InetAddress) c.getSelectedItem();
		if (server != null) {
			System.out.println("Host has changed, restarting...");
			restart();
		}

	}

	/**
	 * Based on this Textfield, the function changes the port or the codeBase.
	 * If needed, the function will restart the server by calling
	 * {@link #restart()}.
	 * 
	 * @param textField
	 *            contains the textField
	 * @return boolean
	 */
	public boolean doTextField(JTextField t) {
		if (t.getName() == "codeBaseText") {

			codeBase = t.getText();
			File folder = new File(codeBase);
			if (!folder.isDirectory() && !folder.exists()) {
				codeBase = null;
				frame
						.setError("This is not a folder or this folder does not exists");
				return false;
			} else {
				frame.setError("");
				System.out.println("setting codebase to: " + codeBase);
			}
		} else if (t.getName() == "portField") {
			try {
				port = Integer.parseInt(t.getText());
				frame.setError("");
				System.out.println("setting port to: " + port);
			} catch (NumberFormatException nfe) {
				frame.setError("No port set or this port does not exist");
				return false;
			}
		}
		if (server != null) {
			restart();
		}
		return true;
	}

	/**
	 * This function creates and starts the thread containing an instance of the
	 * class {@link Server}.
	 * 
	 * @return boolean
	 */
	public boolean start() {
		System.out.print("Checking settings...");

		if (codeBase == null) {
			System.out.println("ERROR: CODEBASE " + codeBase);
			return false;
		} else if (port == 0) {
			System.out.println("ERROR: PORT");
			return false;
		}
		System.out.println("OK");
		System.out.print("starting Service...");
		try {
			server = new Server(address, port, codeBase, version);
			thread = new Thread(server);
			thread.start();
			System.out.println("OK");
		} catch (BindException io) {
			System.out.println("FAILED");
			frame.setError("this port is already in use");
			return false;
		} catch (IOException io) {
			System.out.println("FAILED");
			frame.setError("An I/O error occured");
			return false;
		}
		return true;
	}

	/**
	 * This function calls {@link Server#close()} and stops the thread
	 * containing an instance of the class {@link Server}.
	 * 
	 * @return boolean
	 */
	public boolean stop() {
		try {
			System.out.print("Closing socket...");
			server.close();
			server = null;
		} catch (IOException io) {
			System.out.println("FAILED");
			return false;
		} catch (Exception e) {
			System.out.println("FAILED");
			frame.setError("The port is occupied by another service");
			return false;
		}
		System.out.println("OK");
		try {
			System.out.print("Stopping service...");
			if (thread == null) {
				thread.join();
				thread = null;
			}
		} catch (InterruptedException io) {
			System.out.println("FAILED");
			return false;
		}
		System.out.println("OK");
		return true;
	}

	/**
	 * This restarts the server by first calling {@link #stop()}. if this
	 * returns true, the method will execute {@link #start()} in order to start
	 * the server.
	 */
	public void restart() {
		System.out.println("Restarting Server...");

		if (this.stop()) {
			this.start();
		}
	}

	/**
	 * This function is part of the {@link ActionListener}. In this case it will
	 * execute {@link #doButton()} if the source is the start/stop button and
	 * {@link #doTextField(JTextField)} if the source is an instance of
	 * {@link JTextField}
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {

			doButton((JButton) e.getSource());
		}
		if (e.getSource() instanceof JTextField) {
			doTextField((JTextField) e.getSource());
		}
	}

	/**
	 * This function is part of the {@link ItemListener}. In this case the
	 * ItemListener will execute {@link #doChoice()} when
	 * {@link GUI_Frame#hostList} has changed.
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == 1) {
			if (e.getSource() instanceof JComboBox) {
				doChoice((JComboBox) e.getSource());
			}
		}
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowClosed(WindowEvent e) {

	}

	/**
	 * This function is part of the {@link WindowListener}. In this case the
	 * function will stop all server activities by calling {@link #stop()} and
	 * exits the program.
	 */
	public void windowClosing(WindowEvent e) {
		stop();
		System.exit(0);
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowOpened(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowIconified(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowDeiconified(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowActivated(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowDeactivated(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowGainedFocus(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowLostFocus(WindowEvent e) {
	}

	/**
	 * This function is part of the {@link WindowListener}.
	 */
	public void windowStateChanged(WindowEvent e) {

	}
}
