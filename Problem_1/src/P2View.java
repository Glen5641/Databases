
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main view for problem 2. It creates 4 clickable buttons with 4 options
 * respectively: Option 1 - Add to Problem Table Option 2 - Increase Author
 * Compensation Option 3 - Display Author and Problem Tables Option 4 - Exit
 * Action listeners are placed on these buttons and are run through events
 * 
 * @author Clayton Glenn Matt Gaede Melcamsew Tiruneh
 * 
 * @version: 1
 * @date: 10/14/2018
 */
public class P2View extends JFrame implements ActionListener {

	// Default serial
	private static final long serialVersionUID = 1L;

	// Create object buttons for add, raise, display, and exit
	private JButton jbop1 = new JButton("Add Problem from Author");
	private JButton jbop2 = new JButton("Raise Compensation of Specific Author");
	private JButton jbop3 = new JButton("Display Problem and Author Table");
	private JButton jbop4 = new JButton("Exit");
	private JPanel jbpanel = new JPanel(new GridBagLayout());

	// Use gridbag constraints to layout GUI
	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Constructor for main view that sets a frame for the UI.
	 */
	public P2View() {

		// Set the frame data, layout, borders
		setFrame();
		setButtonBorders();
		this.setLayout(new BorderLayout());

		// Add components to the frame
		c.anchor = GridBagConstraints.WEST;
		addComponent(jbpanel, jbop1, 0, 0, 300, 30, 180, 5, 10, 10);
		addComponent(jbpanel, jbop2, 0, 30, 300, 30, 100, 5, 10, 10);
		addComponent(jbpanel, jbop3, 0, 60, 300, 30, 130, 5, 10, 10);
		addComponent(jbpanel, jbop4, 0, 90, 300, 30, 305, 5, 10, 10);
		add(jbpanel, BorderLayout.WEST);

		// Make frame visible
		setVisible(true);
	}

	/**
	 * Change gridbag constraints by function to clean up code and add the
	 * components to eachother.
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
	 * Set custom borders for no reason. GUI enhancement
	 */
	public void setButtonBorders() {
		jbop1.setBorder(BorderFactory.createRaisedBevelBorder());
		jbop2.setBorder(BorderFactory.createRaisedBevelBorder());
		jbop3.setBorder(BorderFactory.createRaisedBevelBorder());
		jbop4.setBorder(BorderFactory.createRaisedBevelBorder());
	}

	/**
	 * Set frame data to certain specifications.
	 */
	public void setFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Group Project 3");
		setLayout(new BorderLayout());
		this.setSize(345, 150);
		setLocationRelativeTo(null);
	}

	/**
	 * Create action commands that the action listener can distinguish each command.
	 */
	public void setActionCommands() {
		jbop1.setActionCommand("Option 1");
		jbop2.setActionCommand("Option 2");
		jbop3.setActionCommand("Option 3");
		jbop4.setActionCommand("Option 4");
	}

	/**
	 * Register 1 action listener on 4 different buttons.
	 * 
	 * @param jbopListener
	 */
	public void registerJbopListener(ActionListener jbopListener) {
		jbop1.addActionListener(jbopListener);
		jbop2.addActionListener(jbopListener);
		jbop3.addActionListener(jbopListener);
		jbop4.addActionListener(jbopListener);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Nothing is done in action performed, because
		// nothing needs updated. Just removes warnings.
	}
}
