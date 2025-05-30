//********************************************************************
//
//  Author:        Levi Yoder
//
//  Program #:     Five
//
//  File Name:     Program5.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date: 	   5/12/2025
//
//  Instructor:    Prof. Fred Kumi 
//
//  Description:   Driver class for database assignment
//
//********************************************************************
import java.sql.*;
import java.util.Scanner;

public class Program5 {

	
	Connection connection = null;
	Statement statement = null;
	ConnectToOracleDB dbConnect;
	Scanner userInput = new Scanner(System.in);
	
	RunSQLQueries queryObj = new RunSQLQueries();
	
	// ***************************************************************
		//
		// Method:      main
		//
		// Description: The main method of the program
		//
		// Parameters:  String array
		//
		// Returns:     N/A
		//
		// **************************************************************
		public static void main(String args[])
		{
			Program5 object = new Program5();
			object.developerInfo();
			object.createConnectionAndStatement();
	        object.setConnectAndStatement();
	              
			// Run main menu
	        object.menuLoop();
	        
			//Terminate connection
			object.closeConnection();
	    }
		
		//***************************************************************
		//
		// Method:      menuLoop
		//
		// Description: This method presents menu options to the user
		//              and calls menuChoice to process selection
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void menuLoop()
		{
			int choice = 0;
			
			while (choice != 10)
			{
				
				System.out.println("MENU CHOICES");
				System.out.println("");
				System.out.println("1: Add an employee to the employee table.");
				System.out.println("2: Add payroll information for an employee.");
				System.out.println("3: Increase base salary by 15% for all base-plus-commission employees.");
				System.out.println("4: If the employee’s birthday is in the current month, add a $250.00 bonus.");
				System.out.println("5: For all commission employees with gross sales over $10,500.00, add a $210.00\r\n"
						+ "bonus.");
				System.out.println("6: Display information about an employee");
				System.out.println("7: Display the records matching a last name sorted by first name.");
				System.out.println("8: Display the records of all the employees whose birthdays are in a given month");
				System.out.println("9: Display the records of all the employees between two given last names.");
				System.out.println("10: Exit");
				
				try
				{
					if(userInput.hasNextInt())
					{
					
					choice = userInput.nextInt();
					userInput.nextLine();
					menuChoice(choice);
					}
					else {
				        System.out.println("Invalid input. Please enter an integer.");
				        userInput.nextLine(); // Consume the invalid input
				    }
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong" + e.getMessage());
				}
				
			}
		}
		//***************************************************************
		//
		// Method:      menuChoice
		//
		// Description: this method calls corresponding method to user selection
		//
		// Parameters:  int representing user selection
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void menuChoice(int selection)
		{
			switch(selection)
			{
			case 1: insertOptionOne();
				break;
			case 2: insertOptionTwo();
				break;
			case 3: updateOptionThree();
				break;
			case 4: updateOptionFour();
				break;
			case 5: updateOptionFive();
				break;
			case 6: selectOptionSix();
				break;
			case 7: selectOptionSeven();
				break;
			case 8: selectOptionEight();
				break;
			case 9: selectOptionNine();
				break;
			case 10: System.out.println("Terminating program, goodbye.");
				break;
			default:
					System.out.println("Invalid entry.");
					break;
			}
		}
		// ***************************************************************
		//
		// Method:      createConnection
		//
		// Description: Creates a database connection
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		// **************************************************************
		public void createConnectionAndStatement() 
		{
			try {
				dbConnect = new ConnectToOracleDB();
				
				dbConnect.loadDrivers();
				connection = dbConnect.connectDriver();
		
			    if (connection != null)
			    {
				   System.out.println("\nReceived success connection handle in main TestApp");
				   statement = dbConnect.createQueryStatement();
				   
				   if (statement != null)
				      System.out.println("\nQuery statement created successfully.");
			    }
			}
			catch(Exception exp)
			{
	            System.out.println("Something terrible went wrong "  + exp.getMessage());
				System.exit(1);
	        }
		}
		
		//***************************************************************
		//
		// Method:      setConnectionAndStatement
		//
		// Description: This method calls setConnection and setStatement
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************
		public void setConnectAndStatement() 
		{
			queryObj.setConnection(connection);
			queryObj.setStatement(statement);
	    }
		
		//***************************************************************
		//
		// Method:      selectOptionNine
		//
		// Description: This method calls runStandardSelect to run a query
		//				for records between listed names
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************
		public void selectOptionNine() 
		{
		    String query = "SELECT * FROM EMPLOYEES WHERE LAST_NAME BETWEEN 'Landry' AND 'Zilart' ORDER BY FIRST_NAME";
			queryObj.runStandardSelect(query);
	    }
		//***************************************************************
		//
		// Method:      selectOptionEight
		//
		// Description: This method calls runStandardSelect to run a query
		//              of records where birthday falls in current month
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************
		public void selectOptionEight()
		{
			String query = "SELECT * FROM EMPLOYEES WHERE EXTRACT(MONTH FROM BIRTHDAY) = EXTRACT(MONTH FROM SYSDATE)";
			queryObj.runStandardSelect(query);
		}
		//***************************************************************
		//
		// Method:      selectOptionSeven
		//
		// Description: This method calls runStandardSelect to run a query
		//              of records where the last name is a match
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************
		public void selectOptionSeven()
		{
			String query = "SELECT * FROM EMPLOYEES WHERE LAST_NAME = 'Smith' ORDER BY FIRST_NAME";
			queryObj.runStandardSelect(query);
		}
		//***************************************************************
		//
		// Method:      updateOptionFive
		//
		// Description: This method calls runStandardUpdate to run a query
		//              to update bonus based on gross sales above threshold
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void updateOptionFive()
		{
			int count = 0;
			String query = "UPDATE COMMISSION_EMPLOYEES SET BONUS = 210.00 WHERE GROSS_SALES > 10500 AND WEEK_NUMBER = 2";
			System.out.println("\nRunning the update query ...");	        
			   count = queryObj.runStandardUpdate(query);
			   System.out.println("Number of records updated for query is " + count);
		}
		//***************************************************************
		//
		// Method:      insertOptionOne
		//
		// Description: This method calls runEmployeeInsert to run a query
		//              to insert new employee into primary table
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void insertOptionOne()
		{
			String query = "INSERT INTO EMPLOYEES(SSN, FIRST_NAME, LAST_NAME, BIRTHDAY, EMPLOYEE_TYPE, DEPARTMENT_NAME)" +
		"VALUES(?, ?, ?, ?, ?, ?)";
			System.out.println("Running insert query ...");
			
			queryObj.runEmployeeInsert(query);
		}
		//***************************************************************
		//
		// Method:      insertOptionTwo
		//
		// Description: this method gets user input to pass to switch statement
		//              to determine type of insert method to use
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void insertOptionTwo()
		{
			
			String type;
			System.out.println("Enter type of employee.");
			System.out.println("Valid types: Hourly, Salary, Commission, BaseCommission, Piece");
			type = userInput.nextLine();
			
			optionTwoSwitch(type);
			
			
		}
		//***************************************************************
		//
		// Method:      optionTwoSwitch
		//
		// Description: This method uses a switch statement to call
		//              corresponding insert method to employee type
		//
		// Parameters:  string representing type of employee
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void optionTwoSwitch(String type)
		{
			String query;
			int count;
			
			switch (type.toUpperCase())
			{
			case "HOURLY": 
				query = "INSERT INTO HOURLY_EMPLOYEES(SSN, WEEK_NUMBER, HOURS_WORKED, PAY_RATE, BONUS)" +
			" VALUES(?, ?, ?, ?, ?)";
							count = queryObj.runHourlyInsert(query);
							System.out.println("Number of records updated for query is " + count);
							break;
			case "SALARY": 
				query = "INSERT INTO SALARIED_EMPLOYEES (SSN, WEEK_NUMBER, WEEKLY_SALARY, BONUS)" +
						" VALUES(?, ?, ?, ?)";
							count = queryObj.runSalaryInsert(query);
							System.out.println("Number of records updated for query is " + count);
							break;
			case "COMMISSION": 
				query = "INSERT INTO COMMISSION_EMPLOYEES (SSN, WEEK_NUMBER, GROSS_SALES, COMMISSION_RATE, BONUS)"
					+ "VALUES(?, ?, ?, ?, ?)";
							count = queryObj.runCommissInsert(query);
							System.out.println("Number of records updated for query is " + count);
							break;
			case "BASECOMMISSION": 
				query = "INSERT INTO PLUS_COMMISSION_EMPLOYEES (SSN, WEEK_NUMBER, GROSS_SALES, COMMISSION_RATE, BASE_SALARY, BONUS"
					+ " VALUES(?, ?, ?, ?, ?, ?)";
							count = queryObj.runBasePlusInsert(query);
							System.out.println("Number of records updated for query is " + count);
							break;
			case "PIECE" :
				query = "INSERT INTO PIECE_EMPLOYEES (SSN, WEEK_NUMBER, PIECE_RATE, NUMBER_PIECES, BONUS " +
			" VALUES(?, ?, ?, ?, ?)";
							count = queryObj.runPieceInsert(query);
							System.out.println("Number of records updated for query is " + count);
							break;
			default: System.out.println("Invalid entry.");
							break;
			}
		}
		//***************************************************************
		//
		// Method:      updateOptionThree
		//
		// Description: This method calls runStandardUpdate to run a query
		//              to grant raise to basePlusCommission employees
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void updateOptionThree()
		{
			int count = 0;
			String query = "UPDATE PLUS_COMMISSION_EMPLOYEES SET BASE_SALARY = BASE_SALARY * 1.15 WHERE WEEK_NUMBER = 2";
			System.out.println("\nRunning the update query ...");	        
			   count = queryObj.runStandardUpdate(query);
			   System.out.println("Number of records updated for query is " + count);
		}
		//***************************************************************
		//
		// Method:      updateOptionFour
		//
		// Description: This method calls runStandardUpdate to run a query
		//              grants bonus to employees if birthday in current month
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void updateOptionFour()
		{
			int count = 0;
			String hourlyQuery = queryBuilder("HOURLY_EMPLOYEES");
			count = queryObj.runStandardUpdate(hourlyQuery);
			System.out.println("Number of records updated for HOURLY_EMPLOYEES is " + count);
			   
			String salariedQuery = queryBuilder("SALARIED_EMPLOYEES");
			count = queryObj.runStandardUpdate(salariedQuery);
			System.out.println("Number of records updated for SALARIED_EMPLOYEES is " + count);
			
			String commissionQuery = queryBuilder("COMMISSION_EMPLOYEES");
			count = queryObj.runStandardUpdate(commissionQuery);
			System.out.println("Number of records updated for COMMISSION_EMPLOYEES is " + count);
			
			String basePlusQuery = queryBuilder("PLUS_COMMISSION_EMPLOYEES");
			count = queryObj.runStandardUpdate(basePlusQuery);
			System.out.println("Number of records updated for BASE_PLUS_EMPLOYEES is " + count);
			
			String pieceQuery = queryBuilder("PIECE_EMPLOYEES");
			count = queryObj.runStandardUpdate(pieceQuery);
			System.out.println("Number of records updated for PIECE_EMPLOYEES is " + count);	
		}
		//***************************************************************
		//
		// Method:      queryBuilder
		//
		// Description: This method builds query based on employee type
		//
		// Parameters:  None
		//
		// Returns:     constructed string query
		//
		//**************************************************************		
		public String queryBuilder(String employeeType)
		{
			String builtQuery = "UPDATE " + employeeType + " SET BONUS = BONUS + 250" + 
					 "WHERE SSN IN (SELECT SSN FROM EMPLOYEES WHERE EXTRACT(MONTH FROM BIRTHDAY) = EXTRACT(MONTH FROM SYSDATE))"
					+ "AND WEEK_NUMBER = 2";
			
			return builtQuery;
		}
		
		
		//***************************************************************
		//
		// Method:      updateOptionFive
		//
		// Description: This method calls runCustomSelect to run a query
		//              of employee records based on user input
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************		
		public void selectOptionSix()
		{
			String ssn;
			String columns;
			String ssnQuery = "SELECT SSN FROM EMPLOYEES";
			queryObj.runSsnSelect(ssnQuery);
			
			System.out.println("Enter SSN for display: ");
			ssn = userInput.nextLine();
			
			System.out.println("Select columns to display separated by a comma.");
			System.out.println("Supported colums are: SSN, FIRST_NAME, LAST_NAME, BIRTHDAY, EMPLOYEE_TYPE, DEPARTMENT_NAME");
			columns = userInput.nextLine().toUpperCase();
			
			String selectQuery = "SELECT " + columns + " FROM EMPLOYEES WHERE SSN = " + "'" + ssn + "'";
			
			queryObj.runCustomSelect(selectQuery);
		}
		
		//***************************************************************
		//
		// Method:      closeConnection
		//
		// Description: Calls closeDBConnection() to close the query
		//              statement and database connection.
		//
		// Parameters:  None
		//
		// Returns:     N/A
		//
		//**************************************************************
		public void closeConnection() 
		{
			try {
				dbConnect.closeDBConnection();
			} catch (Exception e) {
				System.out.println("\nClosing the Database Connection failed");
				System.exit(1);
			}
		}	
		//***************************************************************
	    //
	    //  Method:       developerInfo
	    // 
	    //  Description:  The developer information method of the program
	    //
	    //  Parameters:   None
	    //
	    //  Returns:      N/A 
	    //
	    //**************************************************************
	    public void developerInfo()
	    {
	       System.out.println("Name:     Levi Yoder");
	       System.out.println("Course:   COSC 4301 Modern Programming");
	       System.out.println("Program:  5");
	       System.out.println("Due Date: 05/15/2025\n");
	    } // End of the developerInfo method
}
