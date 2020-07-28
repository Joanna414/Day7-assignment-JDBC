package com.montran.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.montran.pojo.Employee;

public class EmployeeUtil {
	private List<Employee> employeeList = new ArrayList<Employee>();
	Connection connection= null;
	String sql="";
	PreparedStatement preparedStatement;
	private ResultSet resultset;
	
	public EmployeeUtil(Connection connection) {
		this.connection = connection;
	}
	

	public boolean addNewEmployee(Employee employee) throws SQLException {
		if(employeeList.add(employee)){
			sql = "insert into employee_master values(?,?,?)";
			
			//create query and pass it to preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, employee.getEmployeeId());
			preparedStatement.setString(2, employee.getName());
			preparedStatement.setDouble(3,employee.getSalary());
			preparedStatement.executeUpdate();
			return true;
		}
		
		return false;
		
	}

	public boolean addAllEmployees(Employee[] employee) throws SQLException {
		for (Employee emp : employee) {
			addNewEmployee(emp);

		}
		return true;
	}

	public boolean updateEmployeeSalary(int employeeId, double newSalary) throws SQLException {
		sql="select * from employee_master";
		preparedStatement = connection.prepareStatement(sql);
		
		ResultSet resultset = preparedStatement.executeQuery();
		while(resultset.next()) {		
		if (resultset.getInt("employee_Id") == employeeId) {
			
				sql = "update employee_master set salary=? where employee_id=?";
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setDouble(1, newSalary);
				preparedStatement.setInt(2, employeeId);
				preparedStatement.executeUpdate();
				return true;
			}
		}
		
		return false;
	}

	public boolean deleteEmployee(int employeeId) throws SQLException {
		for (Employee employee : employeeList) {
			if (employee.getEmployeeId() == employeeId) {
				sql = "delete from employee_master where employee_id=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, employeeId);
				preparedStatement.executeUpdate();
				return true;
			}
		}
		
		return false;
	}

	public boolean getEmployeeByEmployeeId(int employeeId) throws SQLException {
		sql="select * from employee_master";
		preparedStatement = connection.prepareStatement(sql);
		
		resultset = preparedStatement.executeQuery();
		while(resultset.next()) {		
		if (resultset.getInt("employee_Id") == employeeId) {
				
				
					System.out.println("EmployeeId :: "+ resultset.getInt("employee_id"));
					System.out.println("Name :: "+ resultset.getString("name"));
					System.out.println("Salary :: "+ resultset.getDouble("salary"));
					System.out.println("-------------------------");
					return true;
				}
				}
		
		return false;

	}

	public ResultSet getAllEmployees() throws SQLException {
		sql="select * from employee_master order by employee_id";
		preparedStatement = connection.prepareStatement(sql);
		
		resultset = preparedStatement.executeQuery();
		
		return resultset;
	}

}