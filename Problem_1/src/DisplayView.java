
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The view for Display. It creates a display frame to print out the contents of
 * the result set from JBDC.
 * 
 * @author Clayton Glenn Matt Gaede Melcamsew Tiruneh
 * 
 * @version: 1
 * @date: 10/14/2018
 */
public class DisplayView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel ldisplay;
	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Constructor for selection view that sets a frame for the UI
	 */
	public DisplayView(final String results) {

		ldisplay = new JLabel(results);

		this.setLayout(new BorderLayout());
		add(ldisplay, BorderLayout.CENTER);
		setVisible(true);
	}
}
