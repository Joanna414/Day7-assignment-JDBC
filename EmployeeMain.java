package com.montran.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.montran.pojo.Employee;
import com.montran.util.EmployeeUtil;

public class EmployeeMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "montran";
		String password = "joanna";
		String sql = "";
		PreparedStatement preparedStatement;
		ResultSet resultset;
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee employee;
		Employee[] aaraay;
		int employeeId;
		String name;
		double salary;
		int choice;
		boolean result;
		int numberOfEmployee;
		double newSalary;
		String contchoice;

		try {
			// 1. Load jdbc driver
			Class.forName(driver);
			System.out.println("Driver loaded successfully.");

			// 2. Connect to database
			Connection connection = DriverManager.getConnection(url, user, password);
			if (connection != null) {
				System.out.println("Connection Success !! ");
				EmployeeUtil util = new EmployeeUtil(connection);

				do {
					sql = "select * from employee_master order by employee_id";
					preparedStatement = connection.prepareStatement(sql);
					resultset = util.getAllEmployees();
					while (resultset.next()) {
						System.out.println("EmployeeId :: " + resultset.getInt("employee_id"));
						System.out.println("Name :: " + resultset.getString("name"));
						System.out.println("Salary :: " + resultset.getDouble("salary"));
						System.out.println("-------------------------");
					}

					System.out.println("Menu");
					System.out.println("1. Add Single Employee	");
					System.out.println("2. Add Multiple Employee");
					System.out.println("3. Update Existing Employee Salary");
					System.out.println("4. Delete Existing Employee");
					System.out.println("5. Print Single Employee Details");
					System.out.println("6. Exit");

					System.out.println("Enter your choice");
					choice = scanner.nextInt();

					switch (choice) {
					case 1: {

						System.out.println("Enter employeeId");
						employeeId = scanner.nextInt();
						scanner.nextLine();
						System.out.println("Enter Name");
						name = scanner.nextLine();

						System.out.println("Enter Salary");
						salary = scanner.nextDouble();

						employee = new Employee(employeeId, name, salary);
						result = util.addNewEmployee(employee);

						if (result)
							System.out.println("Employee is added successfully !!");
						else
							System.out.println("Fail to add employee");
						break;
					}
					case 2: {
						System.out.println("How many employees do you want to add ?");
						numberOfEmployee = scanner.nextInt();
						aaraay = new Employee[numberOfEmployee];
						for (int i = 0; i < numberOfEmployee; i++) {
							System.out.println("Enter employeeId");
							employeeId = scanner.nextInt();
							scanner.nextLine();
							System.out.println("Enter Name");
							name = scanner.nextLine();
							System.out.println("Enter Salary");
							salary = scanner.nextDouble();
							employee = new Employee(employeeId, name, salary);
							aaraay[i] = employee;
						}
						result = util.addAllEmployees(aaraay);
						if (result)
							System.out.println("Employee is added successfully !!");
						else
							System.out.println("Fail to add employee");
						break;
					}
					case 3: {
						System.out.println("Enter EmployeeId");
						employeeId = scanner.nextInt();
						System.out.println("Enter new Salary");
						newSalary = scanner.nextDouble();

						if (util.updateEmployeeSalary(employeeId, newSalary))
							System.out.println("Employee salary is updated successfully !!");
						else
							System.out.println("Failed to update Salary ");

						break;
					}

					case 4: {
						System.out.println("Enter EmployeeId");
						employeeId = scanner.nextInt();
						result = util.deleteEmployee(employeeId);
						System.out.println(result);
						if (result)
							System.out.println("Employee entry is deleted successfully !!");
						else
							System.out.println("Failed to delete employee ");

						break;
					}
					case 5: {
						System.out.println("Enter EmployeeId");
						employeeId = scanner.nextInt();
						if (util.getEmployeeByEmployeeId(employeeId))
							System.out.println("Employee found");
						else
							System.out.println("Employee not found");

						break;
					}
					case 6: {
						System.out.println("Thank you");
						System.exit(0);
						break;
					}
					default:
						System.out.println("Invalid choice");
						break;
					}
					System.out.println("Do you want to continue yes- no ??");
					contchoice = scanner.next();

				} while (contchoice.equals("yes"));
				System.out.println("Thank you");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}