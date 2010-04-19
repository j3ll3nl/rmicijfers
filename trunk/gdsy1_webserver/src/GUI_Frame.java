import java.awt.*;
import javax.swing.*;
import java.net.InetAddress;

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
* The GUI for the Diagnostic Webserver application. This class is instantiated by {@link Control}
*/
public class GUI_Frame extends JFrame
{

	private static final long serialVersionUID = -1968536892247944829L;

	{
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel hostLable;
	private JTextField codeBaseText;
	private JButton button;
	private JLabel errorOutput;
	private JTextArea diagnosticOutPut;
	private JLabel codebaseLable;
	private JTextField portField;
	private JLabel portLable;
	private JComboBox hostList;

	private Control control;
	private String version;
	/**
	 * This creates an instance of this class.
	 * @param control contains an instance of {@link Control}
	 */
	public GUI_Frame(Control c, String v)
	{
		control = c;
		version = v;
		initGUI();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	/**
	 * This method creates the actual GUI.
	 */
	private void initGUI()
	{
		try {
			this.setResizable(false);
			this.setTitle("Diagnostic Webserver - "+version);
			this.addWindowListener(control);
			getContentPane().setLayout(null);
			{
				hostLable = new JLabel();
				getContentPane().add(hostLable);
				hostLable.setText("host=");
				hostLable.setBounds(27, 15, 29, 14);
			}
			{
				InetAddress adress[] = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());

				ComboBoxModel hostListModel =
					new DefaultComboBoxModel(adress);
				hostList = new JComboBox();
				hostList.setName("hostList");
				getContentPane().add(hostList);
				hostList.setModel(hostListModel);
				hostList.addItemListener(control);
				hostList.setSelectedItem(adress);
				hostList.setBounds(65, 12, 182, 20);
				//control.address = adress[0];
			}
			{
				portLable = new JLabel();
				getContentPane().add(portLable);
				portLable.setText("port=");
				portLable.setBounds(281, 15, 28, 14);
			}
			{
				portField = new JTextField();
				portField.setName("portField");
				getContentPane().add(portField);
				portField.setText("4711");
				portField.setBounds(319, 12, 71, 20);
				portField.addActionListener(control);
			}
			{
				codebaseLable = new JLabel();
				getContentPane().add(codebaseLable);
				codebaseLable.setText("codebase=");
				codebaseLable.setBounds(417, 15, 54, 14);
			}
			{
				codeBaseText = new JTextField();
				codeBaseText.setName("codeBaseText");
				codeBaseText.addActionListener(control);
				getContentPane().add(codeBaseText);
				codeBaseText.setBounds(481, 12, 155, 20);
			}
			{
				button = new JButton();
				button.setName("button");
				getContentPane().add(button);
				button.setText("Start");
				button.addActionListener(control);
				button.setBackground(Color.green);
				button.setBounds(646, 11, 91, 23);
			}
			{
				diagnosticOutPut = new JTextArea();
				diagnosticOutPut.setEditable(false);
				diagnosticOutPut.setSize(100, 100);
				JScrollPane scrollpane = new JScrollPane(diagnosticOutPut,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollpane.setBounds(0, 40, 767, 344);
				getContentPane().add(scrollpane);

			}
			{
				errorOutput = new JLabel();
				errorOutput.setForeground(Color.RED);
				getContentPane().add(errorOutput);
				errorOutput.setBounds(10, 392, 722, 10);
			}
			{
				this.setSize(773, 438);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
/**
 * This method fills the diagnostic outputfield with a given {@link String}.
 * @param text contains an new line for the diagnostic Outputfield
 */
	public void setText(String text)
	{
		diagnosticOutPut.setText(diagnosticOutPut.getText()+text);
	}

	/**
	 * When an error occures, this function will fill the ErrorOutput with a short explanation of the error.
	 * @param text
	 */
	public void setError(String text)
	{
		errorOutput.setText(text);
	}
}
