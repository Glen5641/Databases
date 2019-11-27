/**
 * This Program creates a GUI that accesses the SQL database from JDBC and
 * Manipulates the Author table or Adds to the problem table. The program was
 * created to complete problem 2 in group project 3.
 * 
 * @author Clayton Glenn Matt Gaede Melkamsew Tiruneh
 * 
 * @version: GP3-P2-Group34
 * @date: 10/14/2018
 */

public class problem2 {

	static private P2View view;
	static private Controller controller;

	/**
	 * This is the driver that connects the view to the controller, making them
	 * co-aware. The Model is the SQL DB itself. The GUI is structured as a MVC.
	 * 
	 * @param String[] args
	 */
	public static void main(String[] args) {

		// Init the main view
		view = new P2View();

		// Init the controller to control the multiple views
		controller = new Controller();

		// Make controller aware of the view through listeners
		controller.setP2View(view);
	}
}