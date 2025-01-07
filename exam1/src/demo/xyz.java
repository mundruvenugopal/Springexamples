package demo;
import java.util.*;
import java.sql.*;
public class xyz {
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the phoneno");
		long empno=Long.parseLong(sc.nextLine());
		
		System.out.println("Enter the employeeId");
		long empId=Long.parseLong(sc.nextLine());
		
		System.out.println("Enter the employeeName");
		String empname=sc.nextLine();
		Class.forName("org.postgresql.Driver");
		
		Connection con=DriverManager.getConnection("jdbc:postgresql://192.168.1.170:8080/postgres?ssl=true","postgres","Root@123");
		
		Statement stm = con.createStatement();
		
		int k=stm.executeUpdate("insert into employee values("+empno+",'"+empId+"','"+empname+"')");
		System.out.println(k);
	}

}
