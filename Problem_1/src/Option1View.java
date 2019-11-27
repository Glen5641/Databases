
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The view for Option 1. It creates some text fields and a button with a
 * listener. Option 1 is for adding a problem to the database.
 * 
 * @author Clayton Glenn Matt Gaede Melcamsew Tiruneh
 * 
 * @version: 1
 * @date: 10/14/2018
 */
public class Option1View extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel lpid = new JLabel("PID");
	private JLabel lpname = new JLabel("PNAME");
	private JLabel laid = new JLabel("AID");
	JTextField tpid = new JTextField();
	JTextField tpname = new JTextField();
	JTextField taid = new JTextField();
	JButton jbadd;
	private JPanel jbpanel = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Constructor for Option1 view that sets a frame for the UI
	 * 
	 * @throws SQLException
	 */
	public Option1View(final String[] results) {

		this.setLayout(new BorderLayout());
		c.anchor = GridBagConstraints.WEST;
		addComponent(jbpanel, lpid, 0, 0, 50, 30, 50, 5, 10, 10);
		addComponent(jbpanel, lpname, 0, 30, 50, 30, 50, 5, 10, 10);
		addComponent(jbpanel, laid, 0, 60, 50, 30, 50, 5, 10, 10);
		addComponent(jbpanel, tpid, 50, 0, 50, 30, 200, 5, 10, 10);
		addComponent(jbpanel, tpname, 50, 30, 50, 30, 200, 5, 10, 10);
		addComponent(jbpanel, taid, 50, 60, 50, 30, 200, 5, 10, 10);
		jbadd = new JButton("Add");
		setButtonBorders();
		addComponent(jbpanel, jbadd, 0, 90, 100, 30, 312, 5, 10, 10);
		add(jbpanel, BorderLayout.WEST);

		setVisible(true);
	}

	/**
	 * Adds components to other components
	 * 
	 * @param to
	 * @param from
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param ipadx
	 * @param ipady
	 * @param weightx
	 * @param weighty
	 */
	public void addComponent(JComponent to, JComponent from, int gridx, int gridy, int gridwidth, int gridheight,
			int ipadx, int ipady, int weightx, int weighty) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.ipadx = ipadx;
		c.ipady = ipady;
		c.weightx = weightx;
		c.weighty = weighty;
		to.add(from, c);
	}

	/**
	 * Sets a cool little border on the button
	 */
	public void setButtonBorders() {
		jbadd.setBorder(BorderFactory.createRaisedBevelBorder());
	}

	/**
	 * Sets action commands to the buttons
	 */
	public void setActionCommands() {
		jbadd.setActionCommand("Add");
	}

	/**
	 * Registers an action listener to the add button.
	 * 
	 * @param jbAddListener
	 */
	public void registerJbAddListener(ActionListener jbAddListener) {
		jbadd.addActionListener(jbAddListener);
	}

	/**
	 * Does nothing, just suppresses warning
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}
