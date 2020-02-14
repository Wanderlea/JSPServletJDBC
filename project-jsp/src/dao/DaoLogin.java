package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.SingleConnection;

public class DaoLogin {
	
	private Connection connection;
	
	public DaoLogin() {
		connection = SingleConnection.getcoConnection();		
	}
	
	public boolean validateLogin(String login, String password) throws Exception{	
		String sql = "select * from  public.users where login = '" + login + "' and password = '" + password + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()){
			return true;
			
		}else{
			return false;
		}
	}
}
