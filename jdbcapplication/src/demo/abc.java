package demo;
import java.util.*;
import java.sql.*;
public class abc {
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
			
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","Root@123");
			
			Statement stm = con.createStatement();
			
			int k=stm.executeUpdate("insert into employee values("+empno+",'"+empId+"','"+empname+"')");
			System.out.println(k);
		}

	}



