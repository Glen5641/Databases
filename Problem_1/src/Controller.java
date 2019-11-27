import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the controller for the option views and main view. It controls
 * through action listeners and runs queries through JDBC.
 *
 * @author Clayton Glenn Matt Gaede Melcamsew Tiruneh
 *
 * @version: 1
 * @date: 10/14/2018
 */
public class Controller {

	private JDialog[] dDialog = new JDialog[3];
	private JDialog opDialog;

	private Option1View option1View;
	private Option2View option2View;
	private DisplayView dview;

	final String hostName = "";
	final String dbName = "";
	final String user = "";
	final String password = "";
	final String url = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
			hostName, dbName, user, password);

	/**
	 * Contructor for controller. Empty
	 */
	public Controller() {
	}

	/**
	 * Add jbutton listener to all 4 buttons in p2view
	 */
	private class JbopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if ("Add Problem from Author".equals(actionEvent.getActionCommand())) {
				try {
					displayTables();
					option1();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ("Raise Compensation of Specific Author".equals(actionEvent.getActionCommand())) {
				try {
					displayTables();
					option2();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ("Display Problem and Author Table".equals(actionEvent.getActionCommand())) {
				try {
					displayTables();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ("Exit".equals(actionEvent.getActionCommand())) {
				System.exit(0);
			}
		}
	}

	/**
	 * Add action listener to add problem button
	 */
	public class AddProblemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				addProblem();
			} catch (SQLException e1) {
			}
			opDialog.dispose();
			int i = 0;
			while (dDialog[i] != null && i < 2) {
				dDialog[i].dispose();
				++i;
			}
			try {
				displayTables();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add action listener to ok button on option 2
	 */
	public class OkListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				addCompensation();
			} catch (SQLException e1) {
			}
			opDialog.dispose();
			int i = 0;
			while (dDialog[i] != null && i < 2) {
				dDialog[i].dispose();
				++i;
			}
			try {
				displayTables();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method implements code for option 1 by accessing the database and
	 * querying
	 *
	 * @throws SQLException
	 */
	private void option1() throws SQLException {

		// Connect to database
		int setSize = 0;
		try (final Connection connection = DriverManager.getConnection(url)) {

			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery("Select * FROM author")) {
				while (resultSet.next()) {
					++setSize;
				}
			}

			String[] results = new String[setSize];
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery("Select * FROM author")) {
				int i = 0;
				while (resultSet.next()) {
					results[i] = resultSet.getString(1);
					++i;
				}
			}

			option1View = new Option1View(results);
			option1View.jbadd.addActionListener(new AddProblemListener());
			opDialog = new JDialog();
			opDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			opDialog.setTitle("Group Project 3 - Option 1");
			opDialog.setLayout(new BorderLayout());
			opDialog.setLocationRelativeTo(null);
			opDialog.setVisible(true);
			opDialog.add(option1View);
			opDialog.pack();
		}
	}

	/**
	 * This method implements code for option 2 by accessing the database and
	 * querying
	 *
	 * @throws SQLException
	 */
	private void option2() throws SQLException {

		// Connect to database
		int setSize = 0;
		try (final Connection connection = DriverManager.getConnection(url)) {

			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery("Select * FROM author")) {
				while (resultSet.next()) {
					++setSize;
				}
			}

			String[] results = new String[setSize];
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery("Select * FROM author")) {
				int i = 0;
				while (resultSet.next()) {
					results[i] = resultSet.getString(1);
					++i;
				}
			}
			option2View = new Option2View(results);
			option2View.jbok.addActionListener(new OkListener());
			opDialog = new JDialog();
			opDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			opDialog.setTitle("Group Project 3 - Option 2");
			opDialog.setLayout(new BorderLayout());
			opDialog.setLocationRelativeTo(null);
			opDialog.setVisible(true);
			opDialog.add(option2View);
			opDialog.pack();
		}
	}

	/**
	 * This method implements code for option 3 by accessing the database and
	 * querying
	 *
	 * @throws SQLException
	 */
	private void displayTables() throws SQLException {

		try (final Connection connection = DriverManager.getConnection(url)) {

			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery("SELECT * FROM author;")) {
				displayTable(resultSet);
			}

			System.out.println("");
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery("SELECT * FROM problem;")) {
				displayTable(resultSet);
			}
		}
	}

	/**
	 * This method displays a table from a parent query string.
	 *
	 * @param resultSet
	 * @throws SQLException
	 */
	private void displayTable(final ResultSet resultSet) throws SQLException {

		String resultString = "<html>";
		int i = resultSet.getMetaData().getColumnCount();
		while (resultSet.next()) {
			resultString += String.format("%s", resultSet.getString(1));
			for (int j = 2; j <= i; ++j) {
				resultString += String.format(" | %s", resultSet.getString(j));
			}
			resultString += "<br/>";
		}
		resultString += "</html>";
		int count = 0;
		while (dDialog[count] != null && count < 2)
			++count;
		dDialog[count] = new JDialog();
		dview = new DisplayView(resultString);
		dDialog[count].setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dDialog[count].setTitle("Group Project 3 - Option 3");
		dDialog[count].setLayout(new BorderLayout());
		dDialog[count].setLocationRelativeTo(null);
		dDialog[count].setVisible(true);
		dDialog[count].add(dview);
		dDialog[count].pack();
	}

	/**
	 * Function to add problem to the database by query
	 *
	 * @throws SQLException
	 */
	private void addProblem() throws SQLException {

		final String transaction = "EXEC op1Func @pid=" + option1View.tpid.getText() + ", @pname='"
				+ option1View.tpname.getText() + "', @aid=" + option1View.taid.getText();

		try (final Connection connection = DriverManager.getConnection(url)) {

			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
			}
		}
	}

	/**
	 * Function to query for compensation addition.
	 *
	 * @throws SQLException
	 */
	private void addCompensation() throws SQLException {

		final String transaction = "EXEC op2Func @aid=" + option2View.taid.getSelectedItem().toString();

		try (final Connection connection = DriverManager.getConnection(url)) {

			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
			}
		}

	}

	/**
	 * Makes the controller aware of the view
	 *
	 * @param view
	 */
	public void setP2View(P2View view) {
		view.registerJbopListener(new JbopListener());
	}
}
