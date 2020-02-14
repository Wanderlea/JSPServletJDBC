package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

import beans.BeanCategory;
import beans.BeanProductJsp;
import connection.SingleConnection;

public class DaoProduct {

	private Connection connection;

	public DaoProduct() {
		connection = SingleConnection.getcoConnection();
	}

	public void saveProduct(BeanProductJsp product) {

		try {
			String sql = "INSERT INTO public.product(name, quantity, value, category_id) VALUES (?, ?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, product.getName());
			insert.setDouble(2, product.getQuantity());
			insert.setDouble(3, product.getValue());
			insert.setLong(4, product.getCategory_id());
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

	public List<BeanProductJsp> list() throws Exception {
		List<BeanProductJsp> list = new ArrayList<BeanProductJsp>();

		String sql = "select * from public.product";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			BeanProductJsp beanProductJsp = new BeanProductJsp();
			beanProductJsp.setId(resultSet.getLong("id"));
			beanProductJsp.setName(resultSet.getString("name"));
			beanProductJsp.setQuantity(resultSet.getDouble("quantity"));
			beanProductJsp.setValue(resultSet.getDouble("value"));
			beanProductJsp.setCategory_id(resultSet.getLong("category_id"));
			list.add(beanProductJsp);
		}

		return list;
	}
	
	public List<BeanCategory> listCategory() throws Exception{
		List<BeanCategory> list = new ArrayList<BeanCategory>();
		String sql = "select * from public.category";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()){
			BeanCategory benBeanCategory = new BeanCategory();
			benBeanCategory.setId(resultSet.getLong("id"));
			benBeanCategory.setName(resultSet.getString("name"));
			list.add(benBeanCategory);
		}
		
		return list;
	}

	public void delete(String id) {

		try {
			String sql = "delete from public.product where id = '" + id + "'";
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

	public BeanProductJsp consult(String id) throws Exception{
		
		String sql = "select * from public.product where id = '" + id + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()){
			BeanProductJsp beanProductJsp = new BeanProductJsp();
			beanProductJsp.setId(resultSet.getLong("id"));
			beanProductJsp.setName(resultSet.getString("name"));
			beanProductJsp.setQuantity(resultSet.getDouble("quantity"));
			beanProductJsp.setValue(resultSet.getDouble("value"));
			beanProductJsp.setCategory_id(resultSet.getLong("category_id"));
			return beanProductJsp;
		}
		
		return null;
	}

	public void update(BeanProductJsp product) {
		
		try{
		String sql = "update public.product set name = ?, quantity = ?, value = ?, category_id = ? where  id = "+ product.getId();
		PreparedStatement update = connection.prepareStatement(sql);
		update.setString(1, product.getName());
		update.setDouble(2, product.getQuantity());
		update.setDouble(3, product.getValue());
		update.setLong(4, product.getCategory_id());
		update.executeUpdate();
		connection.commit();
		
		}catch(Exception e){
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public boolean validateName(String name) throws Exception{
		
		String sql = "select count(1) as qtd from public.product where name = '" + name + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()){
			
			return resultSet.getInt("qtd") <= 0;//return true
		}
		
		return false;
	}
	
	public boolean validateNameUpdate(String name, String id) throws Exception{
		
		String sql = "select count(1) as qtd from public.product where name = '" + name + "' and id <> " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()){
			
			return resultSet.getInt("qtd") <= 0;//return true
		}
		
		return false;
	}

}
