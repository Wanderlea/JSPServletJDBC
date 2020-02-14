//Wanderléa Lodi
//27/10/2017

package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {	
	private static String database = "jdbc:postgresql://localhost:5432/javajsp?autoRecconect=true";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;	
	static{
		conectar();	}	
	public SingleConnection() {
		conectar();	}	
	private static void conectar(){
		try {
			if (connection == null){
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(database, user, password);
				connection.setAutoCommit(false);
				System.out.println("Connect OK!");			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error connecting to database");		}
	}	
	public static Connection getcoConnection(){
		return connection;	}
}
