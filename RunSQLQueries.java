//***************************************************************
//
//  Developer:    Levi Yoder
//
//  Program #:    Five
//
//  File Name:    RunSQLQueries.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     5/12/2025  
//
//  Instructor:   Fred Kumi 
//
//  Description:  Handles SQL response sets for program5
//                heavily modified version of provided source code
//
//***************************************************************

import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class RunSQLQueries
{
	private Connection connection;
	private Statement statement;
    private ResultSet resultSet = null;
    
    Scanner input = new Scanner(System.in);
	
	//***************************************************************
	//
	// Method:      RunQueries
	//
	// Description: Constructor
	//
	// Parameters:  Connection
    //
	// Returns:     N/A
	//
	//**************************************************************
	public RunSQLQueries()
	{
		connection = null;
		statement = null;
	}
	
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}
	
	public void setStatement(Statement statement)
	{
		this.statement = statement;
	}
	
	
	
	//***************************************************************
	//
	// Method:      runStandardSelect
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runStandardSelect(String queryToRun)
	{
		
        String ssn;
        String firstName;
        String lastName;
        Date birthday;
        String employeeType;
        String department;
        
        
 
		// Execute the query and get our result
		try {
            System.out.println("Creating statement...");
		    resultSet = statement.executeQuery(queryToRun);
			
			System.out.printf("%12s%10s%10s%15s%10s%20s%n", "SSN", "First", "Last", "Birthday", 
					"Type", "Department");
			while (resultSet.next())
			{
				ssn = resultSet.getString("SSN");
				firstName = resultSet.getString("FIRST_NAME");
				lastName = resultSet.getString("LAST_NAME");
				birthday = resultSet.getDate("BIRTHDAY");
				employeeType = resultSet.getString("EMPLOYEE_TYPE");
				department = resultSet.getString("DEPARTMENT_NAME");
				System.out.printf("%12s%10s%10s%15s%20s%10s%n", ssn, firstName, lastName, birthday,
						employeeType, department);
			} // End of while loop
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
	}
	//***************************************************************
	//
	// Method:      runSsnSelect
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public void runSsnSelect(String queryToRun)
	{
		String ssn;
		
		try {
            System.out.println("Creating statement...");
		    resultSet = statement.executeQuery(queryToRun);
		    
		    
			
			System.out.println("SSN");
			while (resultSet.next())
			{
				
				ssn = resultSet.getString("SSN");
				System.out.println(ssn);
			} // End of while loop
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
	}
	//***************************************************************
	//
	// Method:      runCustomSelect
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runCustomSelect(String queryToRun)
	{
		
		try {
            System.out.println("Creating statement...");
		    resultSet = statement.executeQuery(queryToRun);
		    ResultSetMetaData metaData = resultSet.getMetaData();
	        int columnCount = metaData.getColumnCount();  
	        
	        List<String> availableColumns = new ArrayList<>();
	        for (int i = 1; i <= columnCount; i++) 
	        {
	            availableColumns.add(metaData.getColumnLabel(i).toUpperCase());
	            
	        }

	        // Header
	        if (availableColumns.contains("SSN")) 
	        	System.out.printf("%12s", "SSN");
	        if (availableColumns.contains("FIRST_NAME")) 
	        	System.out.printf("%10s", "First");
	        if (availableColumns.contains("LAST_NAME")) 
	        	System.out.printf("%10s", "Last");
	        if (availableColumns.contains("BIRTHDAY")) 
	        	System.out.printf("%15s", "Birthday");
	        if (availableColumns.contains("EMPLOYEE_TYPE")) 
	        	System.out.printf("%15s", "Type");
	        if (availableColumns.contains("DEPARTMENT_NAME")) 
	        	System.out.printf("%20s", "Department");
	        System.out.println();
	        
	        
			while (resultSet.next())
			{
				if (availableColumns.contains("SSN")) 
					System.out.printf("%12s", resultSet.getString("SSN"));
	            if (availableColumns.contains("FIRST_NAME")) 
	            	System.out.printf("%10s", resultSet.getString("FIRST_NAME"));
	            if (availableColumns.contains("LAST_NAME")) 
	            	System.out.printf("%10s", resultSet.getString("LAST_NAME"));
	            if (availableColumns.contains("BIRTHDAY")) 
	            	System.out.printf("%15s", resultSet.getDate("BIRTHDAY"));
	            if (availableColumns.contains("EMPLOYEE_TYPE")) 
	            	System.out.printf("%15s", resultSet.getString("EMPLOYEE_TYPE"));
	            if (availableColumns.contains("DEPARTMENT_NAME")) 
	            	System.out.printf("%20s", resultSet.getString("DEPARTMENT_NAME"));
	            System.out.println();
				
				System.out.println();
			} // End of while loop
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
	}
		
	//***************************************************************
	//
	// Method:      runEmployeeInsert
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public int runEmployeeInsert(String queryToRun)
	{
		int count = 0;
		
		System.out.println("Enter SSN(###-##-####): ");
		String ssn = input.nextLine();
		while (!ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
		{
			System.out.println("Invalid entry, try again.");
			ssn = input.nextLine();
		}
		
		System.out.println("Enter first name: ");
		String firstName = input.nextLine();
		while(!firstName.matches("[a-zA-Z]+") || firstName.length() > 15)
		{
			System.out.println("Invalid entry, try again.");
			firstName = input.nextLine();
		}
		
		System.out.println("Enter last name: ");
		String lastName = input.nextLine();
		while(!lastName.matches("[a-zA-Z]+") || lastName.length() > 15)
		{
			System.out.println("Invalid entry, try again.");
			lastName = input.nextLine();
		}
		
		System.out.println("Enter birthday(yyyy-mm-dd): ");
		String birthday = input.nextLine();
		while (!birthday.matches("\\d{4}-\\d{2}-\\d{2}"))
		{
			System.out.println("Invalid entry, try again.");
			birthday = input.nextLine();
		}
		
		System.out.println("Enter employee type: ");
		String employeeType = input.nextLine();
		while (!employeeType.matches("[a-zA-Z]+"))
		{
			System.out.println("Invalid entry, try again.");
			employeeType = input.nextLine();
		}
		
		System.out.println("Enter department: ");
		String department = input.nextLine();
		while(!department.matches("[a-zA-Z]+"))
		{
			System.out.println("Invalid entry, try again.");
			department = input.nextLine();
		}
		
		
		try {
            System.out.println("Creating prepare statement...");
            PreparedStatement prepStatement = connection.prepareStatement(queryToRun);

			prepStatement.setString(1, ssn);
			prepStatement.setString(2, firstName);
			prepStatement.setString(3, lastName);
			prepStatement.setDate(4, Date.valueOf(birthday));
			prepStatement.setString(5, employeeType);
			prepStatement.setString(6, department);
			
			count = prepStatement.executeUpdate();
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message 1: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
		
		
		return count;
	}
	//***************************************************************
	//
	// Method:      runHourlyInsert
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************
	public int runHourlyInsert(String queryToRun)
	{
		int count = 0;
		System.out.println("Enter SSN(###-##-####): ");
		String ssn = input.nextLine();
		while (!ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
		{
			System.out.println("Invalid entry, try again.");
			ssn = input.nextLine();
		}
		
		System.out.println("Enter week number(1-52): ");
		String weekNumber = input.nextLine();
		while(!weekNumber.matches("[0-9]+") || Integer.valueOf(weekNumber) < 1 || Integer.valueOf(weekNumber) >  52)
		{
			System.out.println("Invalid entry, try again.");
			weekNumber = input.nextLine();
		}
		
		System.out.println("Enter hours worked: ");
		String hoursWorked = input.nextLine();
		while(!hoursWorked.matches("[0-9]+") || Integer.valueOf(hoursWorked) < 0 || Integer.valueOf(hoursWorked) > 80)
		{
			System.out.println("Invalid entry, try again.");
			hoursWorked = input.nextLine();
		}
		
		System.out.println("Enter pay rate: ");
		String payRate = input.nextLine();
		while (!payRate.matches("[0-9.]+"))
		{
			System.out.println("Invalid entry, try again.");
			payRate = input.nextLine();
		}
		
		System.out.println("Enter bonus: ");
		String bonus = input.nextLine();
		while(!bonus.matches("[0-9]+"))
		{
			System.out.println("Invalid entry, try again.");
			bonus= input.nextLine();
		}
		try {
            System.out.println("Creating prepare statement...");
            PreparedStatement prepStatement = connection.prepareStatement(queryToRun);

			prepStatement.setString(1, ssn);
			prepStatement.setInt(2, Integer.valueOf(weekNumber));
			prepStatement.setDouble(3, Double.valueOf(hoursWorked));
			prepStatement.setDouble(4, Double.valueOf(payRate));
			prepStatement.setDouble(5, Double.valueOf(bonus));
			
			count = prepStatement.executeUpdate();
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message 1: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
		return count;
	}
	//***************************************************************
	//
	// Method:      runSalaryInsert
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public int runSalaryInsert(String queryToRun)
	{
		int count = 0;
		System.out.println("Enter SSN(###-##-####): ");
		String ssn = input.nextLine();
		while (!ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
		{
			ssn = input.nextLine();
		}
		
		System.out.println("Enter week number(1-52): ");
		String weekNumber = input.nextLine();
		while(!weekNumber.matches("[0-9]+") || Integer.valueOf(weekNumber) < 1 || Integer.valueOf(weekNumber) >  52)
		{
			weekNumber = input.nextLine();
		}
		
		System.out.println("Enter weekly salary: ");
		String weeklySalary = input.nextLine();
		while(!weeklySalary.matches("[0-9.]+"))
		{
			weeklySalary = input.nextLine();
		}
		
		System.out.println("Enter bonus: ");
		String bonus = input.nextLine();
		while(!bonus.matches("[0-9.]+"))
		{
			bonus= input.nextLine();
		}

		try {
            System.out.println("Creating prepare statement...");
            PreparedStatement prepStatement = connection.prepareStatement(queryToRun);

			prepStatement.setString(1, ssn);
			prepStatement.setInt(2, Integer.valueOf(weekNumber));
			prepStatement.setDouble(3, Double.valueOf(weeklySalary));
			prepStatement.setDouble(4, Double.valueOf(bonus));
			
			count = prepStatement.executeUpdate();
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message 1: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
		return count;
	}
	//***************************************************************
	//
	// Method:      runCommissInsert
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public int runCommissInsert(String queryToRun)
	{
		int count = 0;
		System.out.println("Enter SSN(###-##-####): ");
		String ssn = input.nextLine();
		while (!ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
		{
			ssn = input.nextLine();
		}
		System.out.println("Enter week number(1-52): ");
		String weekNumber = input.nextLine();
		while(!weekNumber.matches("[0-9]+") || Integer.valueOf(weekNumber) < 1 || Integer.valueOf(weekNumber) >  52)
		{
			weekNumber = input.nextLine();
		}
		
		System.out.println("Enter gross sales: ");
		String grossSales = input.nextLine();
		while(!grossSales.matches("[0-9]+") || Integer.valueOf(grossSales) < 0)
		{
			grossSales = input.nextLine();
		}
		
		
		System.out.println("Enter commission rate: ");
		String commissionRate = input.nextLine();
		while(!commissionRate.matches("[0-9.]+") || Double.valueOf(commissionRate) < 0 || Double.valueOf(commissionRate) > 1)
		{
			commissionRate = input.nextLine();
		}
		
		System.out.println("Enter bonus: ");
		String bonus = input.nextLine();
		while(!bonus.matches("[0-9.]+"))
		{
			bonus= input.nextLine();
		}
		
		try {
            System.out.println("Creating prepare statement...");
            PreparedStatement prepStatement = connection.prepareStatement(queryToRun);

			prepStatement.setString(1, ssn);
			prepStatement.setInt(2, Integer.valueOf(weekNumber));
			prepStatement.setDouble(3, Integer.valueOf(grossSales));
			prepStatement.setDouble(4, Double.valueOf(commissionRate));
			prepStatement.setDouble(5, Double.valueOf(bonus));
			
			count = prepStatement.executeUpdate();
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message 1: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
		return count;
	}
	//***************************************************************
	//
	// Method:      runBasePlusInsert
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public int runBasePlusInsert(String queryToRun)
	{
		int count = 0;
		System.out.println("Enter SSN(###-##-####): ");
		String ssn = input.nextLine();
		while (!ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
		{
			System.out.println("Invalid entry, try again.");
			ssn = input.nextLine();
		}
		System.out.println("Enter week number(1-52): ");
		String weekNumber = input.nextLine();
		while(!weekNumber.matches("[0-9]+") || Integer.valueOf(weekNumber) < 1 || Integer.valueOf(weekNumber) >  52)
		{
			System.out.println("Invalid entry, try again.");
			weekNumber = input.nextLine();
		}
		System.out.println("Enter gross sales: ");
		String grossSales = input.nextLine();
		while(!grossSales.matches("[0-9]+") || Integer.valueOf(grossSales) < 0)
		{
			System.out.println("Invalid entry, try again.");
			grossSales = input.nextLine();
		}
		
		
		System.out.println("Enter commission rate: ");
		String commissionRate = input.nextLine();
		while(!commissionRate.matches("[0-9.]+") || Double.valueOf(commissionRate) < 0 || Double.valueOf(commissionRate) > 1)
		{
			System.out.println("Invalid entry, try again.");
			commissionRate = input.nextLine();
		}
		
		System.out.println("Enter Base salary: ");
		String baseSalary = input.nextLine();
		while(!baseSalary.matches("[0-9.]+"))
		{
			System.out.println("Invalid entry, try again.");
			baseSalary = input.nextLine();
		}
		
		System.out.println("Enter bonus: ");
		String bonus = input.nextLine();
		while(!bonus.matches("[0-9.]+") || Double.valueOf(bonus) < 0)
		{
			System.out.println("Invalid entry, try again.");
			bonus= input.nextLine();
		}
		try {
            System.out.println("Creating prepare statement...");
            PreparedStatement prepStatement = connection.prepareStatement(queryToRun);

			prepStatement.setString(1, ssn);
			prepStatement.setInt(2, Integer.valueOf(weekNumber));
			prepStatement.setDouble(3, Integer.valueOf(grossSales));
			prepStatement.setDouble(4, Double.valueOf(commissionRate));
			prepStatement.setDouble(3, Double.valueOf(baseSalary));
			prepStatement.setDouble(6, Double.valueOf(bonus));
			
			count = prepStatement.executeUpdate();
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message 1: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
		
		return count;
	}
	//***************************************************************
	//
	// Method:      runPieceInsert
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public int runPieceInsert(String queryToRun)
	{
		int count = 0;
		System.out.println("Enter SSN(###-##-####): ");
		String ssn = input.nextLine();
		while (!ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
		{
			System.out.println("Invalid entry, try again.");
			ssn = input.nextLine();
		}
		System.out.println("Enter week number(1-52): ");
		String weekNumber = input.nextLine();
		while(!weekNumber.matches("[0-9]+") || Integer.valueOf(weekNumber) < 1 || Integer.valueOf(weekNumber) >  52)
		{
			System.out.println("Invalid entry, try again.");
			weekNumber = input.nextLine();
		}
		
		System.out.println("Enter wage per piece: ");
		String pieceRate = input.nextLine();
		while(!pieceRate.matches("[0-9.]+") || Integer.valueOf(pieceRate) < 0)
		{
			System.out.println("Invalid entry, try again.");
			pieceRate = input.nextLine();
		}
		
		System.out.println("Enter number of pieces made: ");
		String numberPieces = input.nextLine();
		while(!numberPieces.matches("[0-9]+") || Integer.valueOf(numberPieces) < 0 || numberPieces.length() > 8) 
		{
			System.out.println("Invalid entry, try again.");
			numberPieces = input.nextLine();
		}
		
		
		System.out.println("Enter bonus: ");
		String bonus = input.nextLine();
		while(!bonus.matches("[0-9.]+") || bonus.length() > 8)
		{
			System.out.println("Invalid entry, try again.");
			bonus= input.nextLine();
		}
		try {
            System.out.println("Creating prepare statement...");
            PreparedStatement prepStatement = connection.prepareStatement(queryToRun);

			prepStatement.setString(1, ssn);
			prepStatement.setInt(2, Integer.valueOf(weekNumber));
			prepStatement.setDouble(3, Double.valueOf(pieceRate));
			prepStatement.setInt(4, Integer.valueOf(numberPieces));
			prepStatement.setDouble(5, Double.valueOf(bonus));
			
			count = prepStatement.executeUpdate();
		}
		catch (SQLException exp)
		{
		   System.out.println("SQL Error Message 1: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.out.println("Failed to run query. \n" + exp.getMessage());
        }
		return count;
		
	}

	//***************************************************************
	//
	// Method:      runStandardUpdate
	//
	// Description: Runs the query
	//
	// Parameters:  queryToRun the query to run
    //
	// Returns:     N/A
	//
	//**************************************************************	
	public int runStandardUpdate(String queryToRun)
	{
		int count = 0;
		
		try {
		    count = statement.executeUpdate(queryToRun);
		}
		catch (SQLException exp)
		{
		   System.err.println("SQL Error Message: " + exp.getMessage());
		} 
        catch(Exception exp)
		{
           System.err.println("Failed to run query. \n" + exp.getMessage());
        }
		
		return count;
	}
	


}