//***************************************************************
//
//  Developer:    Instructor
//
//  Project #:    5
//
//  File Name:    ConnectToOracleDB.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     N/A
//
//  Instructor:   Fred Kumi 
//
//  Description:  Connects to the Oracle database at ACC or at home
//
//***************************************************************
import java.sql.*;
import java.util.Scanner;

public class ConnectToOracleDB
{
	// The Constant dbURL. 
	private final static String db_Home_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private final static String db_ACC_URL = "jdbc:oracle:thin:@coisor.cois.local:1521/cspdb.cois.local";

	Scanner input;

	// Declare a connection from the Interface Connection
	private Connection connection;
	private Statement statement;
	
	public ConnectToOracleDB()
	{
		connection = null;
		statement = null;
	}
	
	//***************************************************************
	//
	// Method:      loadDrivers
	//
	// Description: Loads the Oracle Drivers
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
	public void loadDrivers()
	{
		try {
			// Declare a connection from the Interface Connection
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException exp) {
			System.err.println("The SQL Driver class was not found." + exp);
			System.exit(1);
		} catch (Exception exp) {
			System.err.println("Failed to load SQL Driver." + exp);
			System.exit(1);
		}
	}

	//***************************************************************
	//
	// Method:      connectDriver
	//
	// Description: The main line routine of the program
	//              @throws Exception the exception
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
	public Connection connectDriver() throws Exception
	{
		String connectAcc = "N";
				
		input = new Scanner(System.in);
				
		System.out.print("Are you connecting to the ACC CIT Oracle database<N>? ");
		connectAcc = input.nextLine();
		
        if (connectAcc.equalsIgnoreCase("Y"))
        	connection = dbLocationACC();
		else
			connection = dbLocationHome();
        
        return connection;
	}

	//***************************************************************
	//
	// Method:      dbLocationACC
	//
	// Description: A method to connect to the ACC database
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
	private Connection dbLocationACC() throws SQLException
	{
		String dbURL = db_ACC_URL;
		
		System.out.print("\nEnter your ACC Oracle user name: ");
		String userName = input.next();

		System.out.print("Enter your ACC Oracle password: ");
		String userPassword = input.next();

		System.out.println("\nConnecting to the ACC CIT Oracle database...");
		connection = connectToDb(dbURL, userName, userPassword);
		
        return connection;
    }
	
	//***************************************************************
	//
	// Method:      dbLocationHome
	//
	// Description: A method to connect to your home database
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
	private Connection dbLocationHome() throws SQLException
	{
		String dbURL = db_Home_URL;
		
		System.out.print("\nEnter your home Oracle database user name: ");
		String userName = input.next();

		System.out.print("Enter your home Oracle database password: ");
		String userPassword = input.next();

		System.out.println("\nConnecting to your home Oracle database...");
		connection = connectToDb(dbURL, userName, userPassword);
		
        return connection;
    }

	//***************************************************************
	//
	// Method:      connectToDb
	//
	// Description: Connect to the Database.
	//
	// Parameters:  DB URL, User ID, and DB Password
	//
	// Returns:     connection
	//
	//**************************************************************
	private Connection connectToDb(String dbURL, String dbUser, String dbPasswd) throws SQLException
	{
		DatabaseMetaData databaseMetaData = null;
		
		connection = DriverManager.getConnection(dbURL, dbUser, dbPasswd);

		if (connection != null)
		{
			System.out.println("The database connection was successful\n");
		    databaseMetaData = connection.getMetaData();
		    System.out.println("Product Name    : " +  databaseMetaData.getDatabaseProductName());
		    System.out.println("Product Version : " +  databaseMetaData.getDatabaseProductVersion());
		    System.out.println("Driver Version  : " +  databaseMetaData.getDriverVersion());
		}
		else
		{
			System.err.println("\nThe database connection was not successful - Bye\n");
			System.exit(1);
		}
		
		return connection;
	}
	
	//***************************************************************
	//
	// Method:      createQueryStatement
	//
	// Description: Create a statement
    //
	// Parameters:  None
	//
	// Returns:     The statement
	//
	//**************************************************************
	public Statement createQueryStatement() throws Exception, SQLException
	{
		System.out.println("\nCreating a statement...");
        statement = connection.createStatement();
		
	    return statement;
	}
	
	//***************************************************************
	//
	// Method:      closeDBConnection
	//
	// Description: Close the Database connection
    //
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
	public void closeDBConnection() throws Exception, SQLException
	{
		System.out.println("\nClosing the Database Connection...");

		if (connection != null)
		{
			connection.close(); // Close the database connection
		}
	}
}