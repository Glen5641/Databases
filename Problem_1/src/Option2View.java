
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The view for Option 2. It creates some text fields, combo box and a button
 * with a listener. Option 2 is for adding a money to an authors bank account.
 * 
 * @author Clayton Glenn Matt Gaede Melcamsew Tiruneh
 * 
 * @version: 1
 * @date: 10/14/2018
 */
public class Option2View extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel laid = new JLabel("AID");
	JComboBox<String> taid;
	JButton jbok;
	private JPanel jbpanel = new JPanel(new GridBagLayout());

	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Constructor for selection view that sets a frame for the UI
	 * 
	 * @throws SQLException
	 */
	public Option2View(String[] aids) {

		taid = new JComboBox<String>(aids);

		this.setLayout(new BorderLayout());
		addComponent(jbpanel, laid, 0, 0, 50, 30, 20, 5, 10, 10);
		addComponent(jbpanel, taid, 50, 0, 50, 30, 155, 5, 10, 10);
		jbok = new JButton("OK");
		setButtonBorders();
		addComponent(jbpanel, jbok, 0, 90, 100, 30, 312, 5, 10, 10);
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
	 * Sets creative button borders
	 */
	public void setButtonBorders() {
		jbok.setBorder(BorderFactory.createRaisedBevelBorder());
	}

	/**
	 * Sets action command on the jbutton
	 */
	public void setActionCommands() {
		jbok.setActionCommand("OK");
	}

	/**
	 * Register action listener on button
	 * 
	 * @param jbokListener
	 */
	public void registerjbokListener(ActionListener jbokListener) {
		jbok.addActionListener(jbokListener);
	}

	/**
	 * Does nothing, just suppresses warning
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}
