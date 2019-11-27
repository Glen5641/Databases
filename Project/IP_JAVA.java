import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Project For DBMS. Program to query and manipulate the database system for
 * Future, INC. There are 20 selections overall for individuals to choose from
 * to query the database.
 * 
 * @version Individual Project
 * @date 11/19/2018
 * @author CGlenn
 * @OUID 113375641
 * @4x4 Glen5641
 *
 */
public class Individual_project {

	// Global URL to access Database occasionally
	final static String hostName = "glen5641-sql-server.database.windows.net";
	final static String dbName = "cs-dsa-4513-sql-db";
	final static String user = "glen5641";
	final static String password = "Ftrwurbg_1";
	final static String url = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
			hostName, dbName, user, password);

	// Global Debugging boolean for query strings
	static boolean debug = false;

	/**
	 * Selection #1: Enter a new employee (2/month). Asks for general employee
	 * information and type of employee. Once type of employee is found, relative
	 * information is queried from the user.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select1(Scanner input) throws SQLException {

		// Query Employee Name
		System.out.println("Employee Name: ");
		String name = input.nextLine().toUpperCase().toUpperCase();

		// Query Employee Address
		System.out.println("Employee Address: ");
		String address = input.nextLine().toUpperCase().toUpperCase();

		// Query Employee Type and Accept Wide Range of Strings
		int type = 0;
		while (type == 0) {
			System.out.println("Employee Type:");
			System.out.println("(1) Quality Controller");
			System.out.println("(2) Technical Staff");
			System.out.println("(3) Worker");
			String string = input.nextLine().toUpperCase().toUpperCase();
			try {
				type = Integer.parseInt(string);
			} catch (NumberFormatException e) {
				if (string.equalsIgnoreCase("Quality Controller"))
					type = 1;
				if (string.equalsIgnoreCase("Technical Staff"))
					type = 2;
				if (string.equalsIgnoreCase("Quality"))
					type = 1;
				if (string.equalsIgnoreCase("Technical"))
					type = 2;
				if (string.equalsIgnoreCase("Worker"))
					type = 3;
				if (string.equalsIgnoreCase("Q"))
					type = 1;
				if (string.equalsIgnoreCase("T"))
					type = 2;
				if (string.equalsIgnoreCase("W"))
					type = 3;
			} catch (NullPointerException e) {
				if (string.equalsIgnoreCase("Quality Controller"))
					type = 1;
				if (string.equalsIgnoreCase("Technical Staff"))
					type = 2;
				if (string.equalsIgnoreCase("Quality"))
					type = 1;
				if (string.equalsIgnoreCase("Technical"))
					type = 2;
				if (string.equalsIgnoreCase("Worker"))
					type = 3;
				if (string.equalsIgnoreCase("Q"))
					type = 1;
				if (string.equalsIgnoreCase("T"))
					type = 2;
				if (string.equalsIgnoreCase("W"))
					type = 3;
			}
		}

		// Once type is found
		// If Quality Controller
		// Ask for Type of Product Checked
		// If Technical Staff
		// Ask for education and position
		// If Worker
		// Ask for Max Products Produced Per Day
		String typeOfProductChecked = "";
		String education = "";
		String position = "";
		String maxProducedPerDay = "";
		String transactionBuilder = "";
		if (type == 1) {
			System.out.println("Type of Product Checked: ");
			typeOfProductChecked = input.nextLine().toUpperCase();
			transactionBuilder = String.format(
					"INSERT INTO quality_controller (name, address, type_of_product_checked) VALUES ('%s', '%s', %s);",
					name, address, typeOfProductChecked);
		} else if (type == 2) {
			System.out.println("Education: ");
			education = input.nextLine().toUpperCase();
			System.out.println("Position: ");
			position = input.nextLine().toUpperCase();
			transactionBuilder = String.format(
					"INSERT INTO technical_staff (name, address, education, position) VALUES ('%s', '%s', '%s', '%s');",
					name, address, education, position);
		} else {
			System.out.println("Max Products Produced/Day: ");
			maxProducedPerDay = input.nextLine().toUpperCase();
			transactionBuilder = String.format(
					"INSERT INTO worker (name, address, max_produced_per_day) VALUES ('%s', '%s', %s);", name, address,
					maxProducedPerDay);
		}

		// Build Query Strings for execution and an additional String in case of
		// specialization error
		final String transaction1 = String.format("INSERT INTO employee (name, address) VALUES ('%s', '%s');", name,
				address);
		final String transaction2 = transactionBuilder;
		final String transaction1Rewind = String.format("DELETE FROM employee WHERE name='%s' AND address='%s';", name,
				address);

		// Print strings if debug
		if (debug) {
			System.out.println(transaction1);
			System.out.println(transaction2);
			System.out.println(transaction1Rewind);
		}

		// Access the database via url
		try (final Connection connection = DriverManager.getConnection(url)) {
			try {
				// Try to execute statement 1. If 1 fails return
				final Statement statement1 = connection.createStatement();
				statement1.execute(transaction1);
			} catch (SQLException e) {
				System.err.println("Entry already Exists.");
				return;
			}
			try {
				// If statement 1 passes, try statement 2. If 2 fails, remove the entry from
				// employee table
				final Statement statement2 = connection.createStatement();
				statement2.execute(transaction2);
			} catch (SQLException e) {
				System.err.println("Invalid education or type of product checked");
				try {
					// If rewind fails, database is out of sync
					final Statement statement3 = connection.createStatement();
					statement3.execute(transaction1Rewind);
				} catch (SQLException s) {
					System.err.println("Critical, Unable to correct Database.");
				}
			}
		}
	}

	/**
	 * Selection #2: Enter a new product associated with the person who made the
	 * product, repaired the product if it is repaired, or checked the product
	 * (400/day). Build the correct specialized product and product # and add it to
	 * the database with the information queried from the user.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select2(Scanner input) throws SQLException {

		// Query Product ID
		System.out.println("Product ID: ");
		String productID = input.nextLine().toUpperCase();

		// Query Time Spent
		System.out.println("Time Spent making the Product: ");
		String timeSpent = input.nextLine().toUpperCase();

		// Query Worker Name
		System.out.println("Worker Name: ");
		String workerName = input.nextLine().toUpperCase();

		// Query Worker Address
		System.out.println("Worker Address: ");
		String workerAddress = input.nextLine().toUpperCase();

		// Query Controller Name
		System.out.println("Quality Controller Name: ");
		String qualityControllerName = input.nextLine().toUpperCase();

		// Query Controller Address
		System.out.println("Quality Controller Address: ");
		String qualityControllerAddress = input.nextLine().toUpperCase();

		// Query isRepaired
		System.out.println("Did the product need repaired?(yes/no)");
		String isRepaired = input.nextLine().toUpperCase();

		// Query for Staff name and address
		// and date for product and repair tables
		String technicalStaffName = "";
		String technicalStaffAddress = "";
		String date = "";
		if (isRepaired.equalsIgnoreCase("yes") || isRepaired.equalsIgnoreCase("ye")
				|| isRepaired.equalsIgnoreCase("y")) {
			System.out.println("Technical Staff Name: ");
			technicalStaffName = input.nextLine().toUpperCase();

			System.out.println("Technical Staff Address: ");
			technicalStaffAddress = input.nextLine().toUpperCase();

			System.out.println("Date Repaired(yyyy-MM-dd): ");
			date = input.nextLine().toUpperCase();
		}

		// Query size of product
		System.out.println("Size of Product: ");
		String size = input.nextLine().toUpperCase();

		String defect = "";
		if (isRepaired.equalsIgnoreCase("yes") || isRepaired.equalsIgnoreCase("ye")
				|| isRepaired.equalsIgnoreCase("y")) {
			defect = "yes";
		} else {
			defect = "no";
		}

		// Query isCertified
		String certified = "";
		System.out.println("Is the Product certified?(yes/no)");
		String isCertified = input.nextLine().toUpperCase();
		if (isCertified.equalsIgnoreCase("yes") || isCertified.equalsIgnoreCase("ye")
				|| isCertified.equalsIgnoreCase("y")) {
			certified = "yes";
		} else {
			certified = "no";
		}

		// Query Type of product for future queries
		System.out.println("Type of Product (1,2, or 3): ");
		String type = input.nextLine().toUpperCase();

		// Query Get the specific attributes of the records for the corresponding
		// product#
		String nameOfMajorSoftware = "";
		String color = "";
		String weight = "";
		String transactionBuilder = "";
		if (type.equals("1")) {
			System.out.println("Name of Major Software: ");
			nameOfMajorSoftware = input.nextLine().toUpperCase();
			transactionBuilder = String.format(
					"INSERT INTO product_1 (product_ID, NAME_OF_MAJOR_SOFTWARE) VALUES (%s, '%s');", productID,
					nameOfMajorSoftware);
		} else if (type.equals("2")) {
			System.out.println("Color: ");
			color = input.nextLine().toUpperCase();
			transactionBuilder = String.format("INSERT INTO product_2 (product_ID, color) VALUES (%s, '%s');",
					productID, color);
		} else {
			System.out.println("Weight: ");
			weight = input.nextLine().toUpperCase();
			transactionBuilder = String.format("INSERT INTO product_3 (product_ID, weight) VALUES (%s, %s);", productID,
					weight);
		}

		// Build Product table insertion Statement
		final String transaction1 = String.format(
				"INSERT INTO product (ID, time_spent, worker_name, worker_address, quality_controller_name, quality_controller_address, technical_staff_name, technical_staff_address, size, defect, certified) VALUES (%s, %s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
				productID, timeSpent, workerName, workerAddress, qualityControllerName, qualityControllerAddress,
				technicalStaffName, technicalStaffAddress, size, defect, certified);

		// Build Product # table insertion statement
		final String transaction2 = transactionBuilder;

		// Open connection to database
		try (final Connection connection = DriverManager.getConnection(url)) {

			// Execute first statement
			final Statement statement1 = connection.createStatement();
			statement1.execute(transaction1);
			// Execute second statement
			final Statement statement2 = connection.createStatement();
			statement2.execute(transaction2);

			// If is repaired, execute third statement updating repaired table
			if (isRepaired.equalsIgnoreCase("yes") || isRepaired.equalsIgnoreCase("ye")
					|| isRepaired.equalsIgnoreCase("y")) {
				final String transaction3 = String.format(
						"INSERT INTO repair (product_ID, quality_controller_name, quality_controller_address, technical_staff_name, technical_staff_address, date) VALUES (%s, '%s', '%s', '%s', '%s', '%s');",
						productID, qualityControllerName, qualityControllerAddress, technicalStaffName,
						technicalStaffAddress, date);
				final Statement statement3 = connection.createStatement();
				statement3.execute(transaction3);
				if (debug)
					System.out.println(transaction3);

			}
		}

		if (debug) {
			System.out.println(transaction1);
			System.out.println(transaction2);
		}

	}

	/**
	 * Selection #3: Enter a customer associated with some products (50/day). Add a
	 * customer and associate them with the products they bought in the database by
	 * the purchase table.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select3(Scanner input) throws SQLException {

		// Get customer name
		System.out.println("Customer Name: ");
		String name = input.nextLine().toUpperCase();

		// Get customer address
		System.out.println("Customer Address: ");
		String address = input.nextLine().toUpperCase();

		// Query all products associated with customer for priming statement
		System.out.println("Which products is the customer associated?");
		System.out.println("Enter Product ID or leave blank to finish.");
		String products = input.nextLine().toUpperCase();

		// Build first query
		final String transaction1 = String.format("INSERT INTO customer (name, address) VALUES ('%s', '%s');", name,
				address);

		// Loop through all products entered with built query to insert into purchase
		// table while keeping database connection open
		try (final Connection connection = DriverManager.getConnection(url)) {
			final Statement statement1 = connection.createStatement();
			statement1.execute(transaction1);
			while (!products.equals("") || !products.isEmpty()) {
				final String transaction2 = String.format(
						"INSERT INTO purchase (name, address, product_ID) VALUES ('%s', '%s', %s);", name, address,
						products);
				if (debug) {
					System.out.println(transaction2);
				}
				final Statement statement2 = connection.createStatement();
				statement2.execute(transaction2);

				System.out.println("Enter Product ID or leave blank to finish.");
				products = input.nextLine().toUpperCase();
			}
		}
	}

	/**
	 * Selection #4: Create a new account associated with a product (40/day). Create
	 * a new account for a product and add it to the account table.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select4(Scanner input) throws SQLException {

		// Get account number
		System.out.println("Account Number: ");
		String number = input.nextLine().toUpperCase();

		// Get product ID
		System.out.println("Product ID: ");
		String ID = input.nextLine().toUpperCase();

		// Get date
		System.out.println("Date Established(yyyy-MM-dd): ");
		String date = input.nextLine().toUpperCase();

		// Get cost
		System.out.println("Cost of Product: ");
		String cost = input.nextLine().toUpperCase();

		// Build the statement string
		final String transaction = String.format(
				"INSERT INTO account (account_number, product_ID, date_established, cost_to_make) VALUES (%s, %s, '%s', %s);",
				number, ID, date, cost);
		if (debug) {
			System.out.println(transaction);
		}

		// Open database connection and execute the statement
		try (final Connection connection = DriverManager.getConnection(url)) {
			final Statement statement = connection.createStatement();
			statement.execute(transaction);
		}
	}

	/**
	 * Selection #5: Enter a complaint associated with a customer and product
	 * (30/day). Add a new complaint tuple to the complaint table with the product
	 * and the customer.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select5(Scanner input) throws SQLException {

		// Get complaint ID
		System.out.println("Complaint ID: ");
		String complaintID = input.nextLine().toUpperCase();

		// Get customer Name
		System.out.println("Customer Name: ");
		String name = input.nextLine().toUpperCase();

		// Get customer Address
		System.out.println("Customer Address: ");
		String address = input.nextLine().toUpperCase();

		// Get product ID
		System.out.println("Product ID: ");
		String productID = input.nextLine().toUpperCase();

		// Get Date
		System.out.println("Date (yyyy-MM-dd): ");
		String date = input.nextLine().toUpperCase();

		// Get description of complaint
		System.out.println("Description of Issue (200 char MAX): ");
		String description = input.nextLine().toUpperCase();

		// Get treatment expected
		System.out.println("Treatment Expected (200 char MAX): ");
		String treatment = input.nextLine().toUpperCase();

		// Build string to insert into complaint table
		final String transaction1 = String.format(
				"INSERT INTO complaint (ID, customer_name, customer_address, product_ID, date, description, treatment_expected) VALUES (%s, '%s', '%s', %s, '%s', '%s', '%s');",
				complaintID, name, address, productID, date, description, treatment);
		if (debug) {
			System.out.println(transaction1);
		}

		// Update corresponding product as defective
		final String transaction2 = String.format("UPDATE product SET defect = 'yes' WHERE ID = %s", productID);

		// Open connection and run both statements
		try (final Connection connection = DriverManager.getConnection(url)) {
			final Statement statement1 = connection.createStatement();
			statement1.execute(transaction1);
			final Statement statement2 = connection.createStatement();
			statement2.execute(transaction2);
		}
	}

	/**
	 * Selection #6: Enter an accident associated with appropriate employee and
	 * product (1/week). Create a new incident with the employee that got harmed and
	 * the product he/she was making, repairing, or checking.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select6(Scanner input) throws SQLException {

		// Accident Number
		System.out.println("Accident Number: ");
		String accidentNumber = input.nextLine().toUpperCase();

		// Employee Name
		System.out.println("Employee Name: ");
		String name = input.nextLine().toUpperCase();

		// Employee Address
		System.out.println("Employee Address: ");
		String address = input.nextLine().toUpperCase();

		// Product ID
		System.out.println("Product ID: ");
		String productID = input.nextLine().toUpperCase();

		// Date
		System.out.println("Date(yyyy-MM-dd): ");
		String date = input.nextLine().toUpperCase();

		// Expected Days Lost
		System.out.println("Expected Number of Work Days Lost: ");
		String expectedDaysLost = input.nextLine().toUpperCase();

		// Build statement to insert a record into accident
		final String transaction = String.format(
				"INSERT INTO accident (accident_number, employee_name, employee_address, product_ID, date, num_work_days_lost) VALUES (%s, '%s', '%s', %s, '%s', %s);",
				accidentNumber, name, address, productID, date, expectedDaysLost);
		if (debug) {
			System.out.println(transaction);
		}

		// Open the connection and execute the statement
		try (final Connection connection = DriverManager.getConnection(url)) {
			final Statement statement = connection.createStatement();
			statement.execute(transaction);
		}
	}

	/**
	 * Selection #7: Retrieve the date produced and time spent to produce a
	 * particular product (100/day). Show the data produced by isolating the
	 * particular product and just showing date and time spent.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select7(Scanner input) throws SQLException {

		// User a string builder to build a transaction
		StringBuilder query = new StringBuilder("EXEC sel7Func ");

		// Add product ID
		System.out.println("Product ID: ");
		query.append("@product_ID=");
		String id = input.nextLine().toUpperCase();
		query.append(id);

		// Build the statement
		final String transaction = query.toString();
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection and execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				System.out.printf("Date and Time Spent of Product %s:\n", id);
				while (resultSet.next()) {

					// Print results
					System.out.println(String.format("%s | %s", resultSet.getString(1), resultSet.getString(2)));
				}
			}
		}
	}

	/**
	 * Selection #8: Retrieve all products made by a particular worker (2000/day).
	 * Show all products in product table with certain worker name and address.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select8(Scanner input) throws SQLException {

		// Build transaction string
		StringBuilder query = new StringBuilder("EXEC sel8Func ");
		System.out.println("Worker Name: ");
		query.append("@worker_name='");
		String name = input.nextLine().toUpperCase();
		query.append(name);
		System.out.println("Worker Address: ");
		query.append("', @worker_address='");
		String address = input.nextLine().toUpperCase();
		query.append(address);
		query.append("'");
		final String transaction = query.toString();
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection to database and execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				System.out.printf("Products produced by %s:\n", name);
				while (resultSet.next()) {
					System.out.println(resultSet.getString(1));
				}
			}
		}
	}

	/**
	 * Selection #9: Retrieve the total number of errors a particular quality
	 * controller made. This is the total number of products certified by this
	 * controller and got some complaints (400/day). Show a QControllers errors by
	 * cross referencing certifications and complaints.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select9(Scanner input) throws SQLException {

		// Build transaction
		StringBuilder query = new StringBuilder("EXEC sel9Func ");
		System.out.println("Quality Controller Name: ");
		query.append("@quality_controller_name='");
		String name = input.nextLine().toUpperCase();
		query.append(name);
		System.out.println("Quality Controller Address: ");
		query.append("', @quality_controller_address='");
		String address = input.nextLine().toUpperCase();
		query.append(address);
		query.append("'");
		final String transaction = query.toString();
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection and execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				System.out.printf("Errors made by %s:\n", name);
				while (resultSet.next()) {
					System.out.println(String.format("%s", resultSet.getString(1)));
				}
			}
		}
	}

	/**
	 * Selection #10: Retrieve the total costs of the products in the product3
	 * category which were repaired at the request of a particular quality
	 * controller (40/day). Show the product 3's total costs with repaired = true by
	 * request of QC.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select10(Scanner input) throws SQLException {

		// Build transaction
		StringBuilder query = new StringBuilder("EXEC sel10Func ");

		// Add QC name to string
		System.out.println("Quality Controller Name: ");
		query.append("@quality_controller_name='");
		query.append(input.nextLine().toUpperCase());

		// Add QC address to string
		System.out.println("Quality Controller Address: ");
		query.append("', @quality_controller_address='");
		query.append(input.nextLine().toUpperCase());
		query.append("'");

		final String transaction = query.toString();
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection and execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				while (resultSet.next()) {
					System.out.printf("Total Cost: %s\n", resultSet.getString(1));
				}
			}
		}
	}

	/**
	 * Selection #11: Retrieve all customers who purchased all products of a
	 * particular color (5/month). Shows all customers that bought [color] products.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select11(Scanner input) throws SQLException {

		// Build transaction init
		StringBuilder query = new StringBuilder("EXEC sel11Func ");

		// Append color to transaction statement
		System.out.println("Product Color: ");
		query.append("@color='");
		String color = input.nextLine().toUpperCase();
		query.append(color);
		query.append("'");

		// Init final transaction
		final String transaction = query.toString();
		if (debug) {
			System.out.println(transaction);
		}

		// Execute Transaction after opening connection
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				System.out.printf("All customers that bought %s products:\n", color);
				while (resultSet.next()) {
					System.out.println(String.format("%s | %s", resultSet.getString(1), resultSet.getString(2)));
				}
			}
		}
	}

	/**
	 * Selection #12: Retrieve the total number of work days lost due to accidents
	 * in repairing the products which got complaints (1/month). Show all days lost
	 * due to error products that got out of the company.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select12(Scanner input) throws SQLException {

		// Build statement
		final String transaction = "EXEC sel12Func";
		if (debug) {
			System.out.println(transaction);
		}

		// Execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				while (resultSet.next()) {
					System.out.printf("Total Work Days Lost: %s\n", resultSet.getString(1));
				}
			}
		}
	}

	/**
	 * Selection #13: Retrieve all customers who are also workers (10/month). Show
	 * all customers that happen to just be worker type of employees.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select13(Scanner input) throws SQLException {

		// Build transaction
		final String transaction = "EXEC sel13Func";
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection and execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				System.out.printf("All customers that are also workers:\n");
				while (resultSet.next()) {
					System.out.println(String.format("%s | %s", resultSet.getString(1), resultSet.getString(2)));
				}
			}
		}
	}

	/**
	 * Selection #14: Retrieve all the customers who have purchased the products
	 * made or certified or repaired by themselves (5/day). Show all customers that
	 * had a part in producing the product that they purchased themselves.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select14(Scanner input) throws SQLException {

		// Build Statement
		final String transaction = "EXEC sel14Func";
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection and execute transaction
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				System.out
						.printf("All customers that purchaced produces made, certified, or repaired by themselves:\n");
				while (resultSet.next()) {
					System.out.println(String.format("%s | %s", resultSet.getString(1), resultSet.getString(2)));
				}
			}
		}
	}

	/**
	 * Selection #15: Retrieve the average cost of all products made in a particular
	 * year (5/day). Show the years average costs for making products.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select15(Scanner input) throws SQLException {

		// Get year
		System.out.println("Year: ");
		String year = input.nextLine().toUpperCase();

		// Build statement
		final String transaction = "EXEC sel15Func @year=" + year;
		if (debug) {
			System.out.println(transaction);
		}

		// Open connection and execute
		try (final Connection connection = DriverManager.getConnection(url)) {
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(transaction)) {
				while (resultSet.next()) {
					System.out.printf("Average Cost of products made in %s: %s\n", year, resultSet.getString(1));
				}
			}
		}
	}

	/**
	 * Selection #16: Switch the position between a technical staff and a quality
	 * controller (1/ 3 months). Switch the a QC with a TS. I think of this as both
	 * are switching, not just one to the other or vice versa.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select16(Scanner input) throws SQLException {

		// Get QC -> TS name
		System.out.println("Former Quality Controller Name: ");
		String qcName = input.nextLine().toUpperCase();

		// Get QC -> TS address
		System.out.println("Former Quality Controller Address: ");
		String qcAddress = input.nextLine().toUpperCase();

		// Get QC -> TS education
		System.out.println("Former Quality Controller Education: ");
		String education = input.nextLine().toUpperCase();

		// Get QC -> TS position
		System.out.println("Former Quality Controller Position: ");
		String position = input.nextLine().toUpperCase();

		// Get QC <- TS name
		System.out.println("Former Technical Staff Name: ");
		String tsName = input.nextLine().toUpperCase();

		// Get QC <- TS address
		System.out.println("Former Technical Staff Address: ");
		String tsAddress = input.nextLine().toUpperCase();

		// Get QC <- TS type of product checked
		System.out.println("Type of Product Checked: ");
		String type = input.nextLine().toUpperCase();

		// Build string to delete from QC
		final String transaction1 = String
				.format("DELETE FROM quality_controller WHERE name = '%s' AND address = '%s';", qcName, qcAddress);
		if (debug) {
			System.out.println(transaction1);
		}

		// Build string to delete from TS
		final String transaction2 = String.format("DELETE FROM technical_staff WHERE name = '%s' AND address = '%s';",
				tsName, tsAddress);
		if (debug) {
			System.out.println(transaction2);
		}

		// Build string to insert into QC
		final String transaction3 = String.format(
				"INSERT INTO quality_controller (name, address, type_of_product_checked) VALUES ('%s', '%s', %s);",
				tsName, tsAddress, type);
		if (debug) {
			System.out.println(transaction3);
		}

		// Build String to insert into TS
		final String transaction4 = String.format(
				"INSERT INTO technical_staff (name, address, education, position) VALUES ('%s', '%s', '%s', '%s');",
				qcName, qcAddress, education, position);
		if (debug) {
			System.out.println(transaction4);
		}

		// Open connection and execute all four queries
		try (final Connection connection = DriverManager.getConnection(url)) {
			final Statement statement1 = connection.createStatement();
			statement1.execute(transaction1);
			final Statement statement2 = connection.createStatement();
			statement2.execute(transaction2);
			final Statement statement3 = connection.createStatement();
			statement3.execute(transaction3);
			final Statement statement4 = connection.createStatement();
			statement4.execute(transaction4);
		}
	}

	/**
	 * Selection #17: Delete all accidents whose dates are in some range (1/day).
	 * Delete Statement to delete all accidents within a range of dates.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select17(Scanner input) throws SQLException {

		// Get start date
		System.out.println("Start Date(yyyy-MM-dd): ");
		String startDate = input.nextLine().toUpperCase();

		// Get end date
		System.out.println("End Date(yyyy-MM-dd): ");
		String endDate = input.nextLine().toUpperCase();

		// Build manipulation statement
		final String transaction = String.format("DELETE FROM accident WHERE date > '%s' AND date < '%s';", startDate,
				endDate);
		if (debug) {
			System.out.println(transaction);
		}

		// Execute
		try (final Connection connection = DriverManager.getConnection(url)) {
			final Statement statement = connection.createStatement();
			statement.execute(transaction);
		}
	}

	/**
	 * Selection #18: Import customers from a data file to the database.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select18(Scanner input) throws SQLException {

		// Query file for import
		System.out.println("Enter the name of Customer File as Import: ");
		String file = input.nextLine().toUpperCase();

		// Read from file and skip header line
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			br.readLine();

			// Loop until end of file and open connection
			try (final Connection connection = DriverManager.getConnection(url)) {
				while ((line = br.readLine()) != null) {

					// Tokenize the whole line by , delimitors
					String[] attributes = line.split(",");

					// Build the transaction to add to customer table
					final String transaction = String.format("INSERT INTO customer (name, address) VALUES ('%s', '%s')",
							attributes[0], attributes[1]);
					if (debug) {
						System.out.println(transaction);
					}

					// Execute statement
					final Statement statement = connection.createStatement();
					statement.execute(transaction);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

	/**
	 * Selection #19: Export customers to a data file from the database.
	 * 
	 * @param input The scanner used to read the keyboard
	 * @throws SQLException The exception to catch access issues.
	 */
	public static void select19(Scanner input) throws SQLException, IOException {

		// Ask for import file
		System.out.println("Enter Name of Customer File to Export:");
		String file = input.nextLine().toUpperCase();

		// Open connection
		try (final Connection connection = DriverManager.getConnection(url)) {
			final String selectSql = "SELECT * FROM Customer;";
			try (final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(selectSql)) {

				// Write first line as header line
				String line = "Customer_Name,Customer_Address";
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				try {
					writer.write(line);
					writer.write("\n");
				} catch (IOException e) {
					System.err.println("Cannot write to file");
				}

				// Loop through all tuples
				while (resultSet.next()) {

					// Build each line as name,address
					line = resultSet.getString(1) + "," + resultSet.getString(2);

					// Write to line or show error
					try {
						writer.write(line);
						writer.write("\n");
					} catch (IOException e) {
						System.err.println("Cannot write to file");
					}
				}

				// Close the buffered writer
				writer.close();
			}
		}
	}

	/**
	 * Project For DBMS. Program to query and manipulate the database system for
	 * Future, INC. There are 20 selections overall for individuals to choose from
	 * to query the database.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		System.out.println("WELCOME TO THE DATABASE SYSTEM OF FUTURE, INC.\n");
		System.out.println("(1)  Enter a new employee.");
		System.out.println("(2)  Enter a new product.");
		System.out.println("(3)  Enter a customer.");
		System.out.println("(4)  Create a new account.");
		System.out.println("(5)  Enter a complaint.");
		System.out.println("(6)  Enter an accident.");
		System.out.println("(7)  Show date produced and time spent of a particular product.");
		System.out.println("(8)  Show all products made by a particular worker.");
		System.out.println("(9)  Show number of errors a by quality controller. ");
		System.out.println("(10) Show total costs of product3's repaired at request of a quality controller.");
		System.out.println("(11) Show all customers who purchased products of a particular color.");
		System.out.println("(12) Show total work days lost due to accidents in repairing complaint products");
		System.out.println("(13) Show all customers who are also workers.");
		System.out.println("(14) Show all the customers who have bought products they have produced.");
		System.out.println("(15) Show average cost of all products made in a particular year.");
		System.out.println("(16) Switch the position between a technical staff and a quality controller.");
		System.out.println("(17) Delete all accidents whose dates are in some range (1/day).");
		System.out.println("(18) Import");
		System.out.println("(19) Export");
		System.out.println("(20) Quit.");

		// Open scanner for keyboard input from user
		Scanner input = new Scanner(System.in);
		int selection = 0;

		// Loop through user input until 20 is typed
		while (selection != 20) {

			// Allow for user read data and prompt and let errors print first
			TimeUnit.SECONDS.sleep(1);

			// Query for number 1-20 and execute the corresponding select function
			System.out.println();
			System.out.println("Please input 1:20 and press enter or Enter 0 to see options again.");
			selection = Integer.parseInt(input.nextLine().toUpperCase());
			try {
				switch (selection) {
				case 0:
					System.out.println("(1)  Enter a new employee.");
					System.out.println("(2)  Enter a new product.");
					System.out.println("(3)  Enter a customer.");
					System.out.println("(4)  Create a new account.");
					System.out.println("(5)  Enter a complaint.");
					System.out.println("(6)  Enter an accident.");
					System.out.println("(7)  Show date produced and time spent of a particular product.");
					System.out.println("(8)  Show all products made by a particular worker.");
					System.out.println("(9)  Show number of errors a by quality controller. ");
					System.out.println(
							"(10) Show total costs of product3's repaired at request of certain quality controller.");
					System.out.println("(11) Show all customers who purchased products of a particular color.");
					System.out
							.println("(12) Show total work days lost due to accidents in repairing complaint products");
					System.out.println("(13) Show all customers who are also workers.");
					System.out.println("(14) Show all the customers who have bought products they have produced.");
					System.out.println("(15) Show average cost of all products made in a particular year.");
					System.out.println("(16) Switch the position between a technical staff and a quality controller.");
					System.out.println("(17) Delete all accidents whose dates are in some range (1/day).");
					System.out.println("(18) Import");
					System.out.println("(19) Export");
					System.out.println("(20) Quit.");
					break;
				case 1:
					select1(input);
					break;
				case 2:
					select2(input);
					break;
				case 3:
					select3(input);
					break;
				case 4:
					select4(input);
					break;
				case 5:
					select5(input);
					break;
				case 6:
					select6(input);
					break;
				case 7:
					select7(input);
					break;
				case 8:
					select8(input);
					break;
				case 9:
					select9(input);
					break;
				case 10:
					select10(input);
					break;
				case 11:
					select11(input);
					break;
				case 12:
					select12(input);
					break;
				case 13:
					select13(input);
					break;
				case 14:
					select14(input);
					break;
				case 15:
					select15(input);
					break;
				case 16:
					select16(input);
					break;
				case 17:
					select17(input);
					break;
				case 18:
					select18(input);
					break;
				case 19:
					select19(input);
					break;
				case 20:
					break;
				case 21:

					break;
				default:
					System.out.println("Invalid selection");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Cannot read/write to file");
			}
		}

		// Close down
		System.out.println("THANK YOU FOR USING DATABASE SYSTEM OF FUTURE, INC.");
		input.close();
	}
}
