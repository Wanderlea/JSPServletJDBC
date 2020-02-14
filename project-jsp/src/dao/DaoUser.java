package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProjectJsp;
import connection.SingleConnection;

public class DaoUser {

	private Connection connection;

	public DaoUser() {
		connection = SingleConnection.getcoConnection();
	}

	public void saveUser(BeanProjectJsp user) {

		try {
			String sql = "INSERT INTO public.users(login, password, name, phone, zipCode, street, neighbor, city, state, ibge, photoBase64, contentType, cvbase64, contenttypecv, photoBase64Miniature, active, sex, profile)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, user.getLogin());
			insert.setString(2, user.getPassword());
			insert.setString(3, user.getName());
			insert.setString(4, user.getPhone());
			insert.setString(5, user.getZipCode());
			insert.setString(6, user.getStreet());
			insert.setString(7, user.getNeighbor());
			insert.setString(8, user.getCity());
			insert.setString(9, user.getState());
			insert.setString(10, user.getIbge());
			insert.setString(11, user.getPhotoBase64());
			insert.setString(12, user.getContentType());
			insert.setString(13, user.getCvBase64());
			insert.setString(14, user.getContentTypeCV());
			insert.setString(15, user.getPhotoBase64Miniature());
			insert.setBoolean(16, user.isActive());
			insert.setString(17, user.getSex());
			insert.setString(18, user.getProfile());
			
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
	
	public List<BeanProjectJsp> list(String searchDescription) throws Exception {
		String sql = "select * from public.users where login <> 'admin' and name like '%"+searchDescription+"%'";
		return searchUser(sql);
	}

	public List<BeanProjectJsp> list() throws Exception {
		String sql = "select * from public.users where login <> 'admin'";
		return searchUser(sql);
	}

	private List<BeanProjectJsp>  searchUser(String sql) throws SQLException {
		List<BeanProjectJsp> list = new ArrayList<BeanProjectJsp>();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			BeanProjectJsp beanProjectJsp = new BeanProjectJsp();
			beanProjectJsp.setId(resultSet.getLong("id"));
			beanProjectJsp.setLogin(resultSet.getString("login"));
			beanProjectJsp.setPassword(resultSet.getString("password"));
			beanProjectJsp.setName(resultSet.getString("name"));
			beanProjectJsp.setPhone(resultSet.getString("phone"));
			beanProjectJsp.setZipCode(resultSet.getString("zipCode"));
			beanProjectJsp.setStreet(resultSet.getString("street"));
			beanProjectJsp.setNeighbor(resultSet.getString("neighbor"));
			beanProjectJsp.setCity(resultSet.getString("city"));
			beanProjectJsp.setState(resultSet.getString("state"));
			beanProjectJsp.setIbge(resultSet.getString("ibge"));
			beanProjectJsp.setPhotoBase64Miniature(resultSet.getString("photoBase64Miniature"));
			beanProjectJsp.setContentType(resultSet.getString("contentType"));
			beanProjectJsp.setCvBase64(resultSet.getString("cvBase64"));
			beanProjectJsp.setContentTypeCV(resultSet.getString("contentTypeCV"));
			beanProjectJsp.setActive(resultSet.getBoolean("active"));
			beanProjectJsp.setSex(resultSet.getString("sex"));
			beanProjectJsp.setProfile(resultSet.getString("profile"));
			
			list.add(beanProjectJsp);
		}
		
		return list;
	}

	public void delete(String id) {

		try {
			String sql = "delete from public.users where id = '" + id + "' and login <> 'admin'";
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

	public BeanProjectJsp consult(String id) throws Exception{
		
		String sql = "select * from public.users where id = '" + id + "'  and login <> 'admin'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()){
			BeanProjectJsp beanProjectJsp = new BeanProjectJsp();
			beanProjectJsp.setId(resultSet.getLong("id"));
			beanProjectJsp.setLogin(resultSet.getString("login"));
			beanProjectJsp.setPassword(resultSet.getString("password"));
			beanProjectJsp.setName(resultSet.getString("name"));
			beanProjectJsp.setPhone(resultSet.getString("phone"));
			beanProjectJsp.setZipCode(resultSet.getString("zipCode"));
			beanProjectJsp.setStreet(resultSet.getString("street"));
			beanProjectJsp.setNeighbor(resultSet.getString("neighbor"));
			beanProjectJsp.setCity(resultSet.getString("city"));
			beanProjectJsp.setState(resultSet.getString("state"));
			beanProjectJsp.setIbge(resultSet.getString("ibge"));
			beanProjectJsp.setPhotoBase64(resultSet.getString("photoBase64"));
			beanProjectJsp.setPhotoBase64Miniature(resultSet.getString("photoBase64Miniature"));
			beanProjectJsp.setContentType(resultSet.getString("contentType"));
			beanProjectJsp.setCvBase64(resultSet.getString("cvBase64"));
			beanProjectJsp.setContentTypeCV(resultSet.getString("contentTypeCV"));
			beanProjectJsp.setActive(resultSet.getBoolean("active"));
			beanProjectJsp.setSex(resultSet.getString("sex"));
			beanProjectJsp.setProfile(resultSet.getString("profile"));
			return beanProjectJsp;
		}
		
		return null;
	}

	public void update(BeanProjectJsp user) {
		
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" update public.users set login = ?, password = ?, name = ?, phone = ?, ");
			sql.append(" zipCode = ?, street = ?, neighbor = ?, city = ?, state = ?, ibge = ?, active = ?, sex = ?, profile = ? ");
			if(user.isUpdateImage()){
				sql.append(", photoBase64 = ?, contentType = ?");
			}
			if(user.isUpdatePdf()){
				sql.append(", cvbase64 = ?, contenttypecv = ? ");
			}
			if(user.isUpdateImage()){
				sql.append(", photoBase64Miniature = ? ");  
			}
				sql.append(" where  id = " + user.getId());

			PreparedStatement update = connection.prepareStatement(sql.toString());
			update.setString(1, user.getLogin());
			update.setString(2, user.getPassword());
			update.setString(3, user.getName());
			update.setString(4, user.getPhone());
			update.setString(5, user.getZipCode());
			update.setString(6, user.getStreet());
			update.setString(7, user.getNeighbor());
			update.setString(8, user.getCity());
			update.setString(9, user.getState());
			update.setString(10, user.getIbge());
			update.setBoolean(11, user.isActive());
			update.setString(12, user.getSex());
			update.setString(13, user.getProfile());
			
			if(user.isUpdateImage()){
				update.setString(14, user.getPhotoBase64());
				update.setString(15, user.getContentType());
			}
			if(user.isUpdatePdf()){
				
				if(!user.isUpdateImage()){
					update.setString(14, user.getCvBase64());
					update.setString(15, user.getContentTypeCV());
				}else{
					update.setString(16, user.getCvBase64());
					update.setString(17, user.getContentTypeCV());
				}
			}else{
				if(user.isUpdateImage()){
					update.setString(16, user.getPhotoBase64Miniature());
				}
			}
			
			if(user.isUpdateImage() && user.isUpdatePdf()){
				update.setString(18, user.getPhotoBase64Miniature());
			}
			
			update.executeUpdate();
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
	
	
	public boolean validateLogin(String login) throws Exception{
		
		String sql = "select count(1) as qtd from public.users where login = '" + login + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()){
			
			return resultSet.getInt("qtd") <= 0;//return true
		}
		
		return false;
	}
	
	public boolean validateLoginUpdate(String login, String id) throws Exception{
		
		String sql = "select count(1) as qtd from public.users where login = '" + login + "' and id <> " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()){
			
			return resultSet.getInt("qtd") <= 0;//return true
		}
		
		return false;
	}
}
