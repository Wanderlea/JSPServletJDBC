package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanPhoneJsp;
import connection.SingleConnection;

public class DaoPhone {

	private Connection connection;

	public DaoPhone() {
		connection = SingleConnection.getcoConnection();
	}

	public void savePhone(BeanPhoneJsp phone) {

		try {
			String sql = "INSERT INTO public.phone(number, type, user_id) VALUES (?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, phone.getNumber());
			insert.setString(2, phone.getType());
			insert.setLong(3, phone.getUser_id());
			
			insert.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public List<BeanPhoneJsp> list(Long user) throws Exception {
		List<BeanPhoneJsp> list = new ArrayList<BeanPhoneJsp>();

		String sql = "select * from public.phone where user_id = "+ user;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			BeanPhoneJsp beanPhoneJsp = new BeanPhoneJsp();
			beanPhoneJsp.setId(resultSet.getLong("id"));
			beanPhoneJsp.setNumber(resultSet.getString("number"));
			beanPhoneJsp.setType(resultSet.getString("type"));
			beanPhoneJsp.setUser_id(resultSet.getLong("user_id"));
			list.add(beanPhoneJsp);
		}

		return list;
	}

	public void delete(String id) {

		try {
			String sql = "delete from public.phone where id = '" + id + "'";
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
