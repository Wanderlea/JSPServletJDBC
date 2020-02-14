package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanProjectJsp;
import dao.DaoUser;

@WebServlet("/saveUser")
//Form with file
@MultipartConfig
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUser daoUser = new DaoUser();

	public User() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao!= null && acao.equalsIgnoreCase("delete")) {
				daoUser.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", daoUser.list());
				view.forward(request, response);
			}else if (acao!= null && acao.equalsIgnoreCase("edit")){
				BeanProjectJsp beanProjectJsp = daoUser.consult(user);
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("user", beanProjectJsp);
				view.forward(request, response);
			}else if(acao!= null && acao.equalsIgnoreCase("allList")){
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", daoUser.list());
				view.forward(request, response);
			}else if (acao!= null && acao.equalsIgnoreCase("download")){
				BeanProjectJsp userConsult = daoUser.consult(user);
				if (userConsult != null){
					String contentType = "";
					byte[] fileBytes = null;
					
					String type = request.getParameter("tipo");
					
					if (type.equalsIgnoreCase("image")){
						contentType = userConsult.getContentType();
						fileBytes = new Base64().decodeBase64(userConsult.getPhotoBase64());
					}else if(type.equalsIgnoreCase("cv")){
						contentType = userConsult.getContentTypeCV();
						fileBytes = new Base64().decodeBase64(userConsult.getCvBase64());
					}
					
					response.setHeader("Content-Disposition", "attachment;filename=file." + contentType.split("\\/")[1]);
					
					InputStream is = new ByteArrayInputStream(fileBytes);
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();
					
					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					
					os.flush();
					os.close();
				}
			}else{
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", daoUser.list());
				view.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && acao.equalsIgnoreCase("reset")){
			try {
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", daoUser.list());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
		
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String zipCode = request.getParameter("zipCode");
			String street = request.getParameter("street");
			String neighbor = request.getParameter("neighbor");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String ibge = request.getParameter("ibge");
			String sex = request.getParameter("sex");
			String profile = request.getParameter("profile");
			
			BeanProjectJsp user = new BeanProjectJsp();
			user.setId(!id.isEmpty()? Long.parseLong(id) : null);
			user.setLogin(login);
			user.setPassword(password);
			user.setName(name);
			user.setPhone(phone);
			user.setZipCode(zipCode);
			user.setStreet(street);
			user.setNeighbor(neighbor);
			user.setCity(city);
			user.setState(state);
			user.setIbge(ibge);
			user.setSex(sex);
			user.setProfile(profile);
			
			if(request.getParameter("active")!= null && request.getParameter("active").equalsIgnoreCase("on")){
				user.setActive(true);
			}else{
				user.setActive(false);
			}

			try {
				
				/*Begin File upload image and pdf */
				
				//valid form upload
				if (ServletFileUpload.isMultipartContent(request)) {

					// recover file list
					Part imagePhoto = request.getPart("photo");		

					if (imagePhoto != null && imagePhoto.getInputStream().available() > 0) {
						
						String photoBase64 = new Base64().encodeBase64String(convertStreamtoByte(imagePhoto.getInputStream()));
						user.setPhotoBase64(photoBase64);
						user.setContentType(imagePhoto.getContentType());
						
						//Create thumbnail picture.
						byte[] imageByteDecode = new Base64().decodeBase64(photoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
						
						BufferedImage resizeImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizeImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizeImage, "png", baos);
						
						String miniatureBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
						
						user.setPhotoBase64Miniature(miniatureBase64);
						
					}else{
						user.setUpdateImage(false);
					}
					
					/*Process PDF*/
					Part cvPdf = request.getPart("cv");		
					if(cvPdf != null && cvPdf.getInputStream().available() > 0){
						String cvBase64 = new Base64().encodeBase64String(convertStreamtoByte(cvPdf.getInputStream()));	
						user.setCvBase64(cvBase64);
						user.setContentTypeCV(cvPdf.getContentType());
					}else{
						user.setUpdatePdf(false);
					}
				}
				
				/*End File upload image and pdf */
			
				//Required fields
				if (login == null || login.isEmpty()){
					request.setAttribute("msg","Login required!");
					request.setAttribute("user", user);
				}else if (password == null || password.isEmpty()){
					request.setAttribute("msg","Password required!");
					request.setAttribute("user", user);
				}else if (name == null || name.isEmpty()){
					request.setAttribute("msg","Name required!");
					request.setAttribute("user", user);
				}
				
				//Insert
				else if(id == null || id.isEmpty() && !daoUser.validateLogin(login)){ 
					request.setAttribute("msg","There is already a user with this login!");
					request.setAttribute("user", user);
				}else if (id == null || id.isEmpty() && daoUser.validateLogin(login)) {
					daoUser.saveUser(user);
					RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
					request.setAttribute("users", daoUser.list());
					request.setAttribute("msg", "Saved successfully!");
				}
				
				//Update
				else if (id != null && !id.isEmpty() && !daoUser.validateLoginUpdate(login, id)) { 
					request.setAttribute("msg","There is already a user with this login!");
					request.setAttribute("user", user);
				} else{
					daoUser.update(user);
					RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
					request.setAttribute("users", daoUser.list());
					request.setAttribute("msg", "Saved successfully!");
				}
				
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", daoUser.list());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Convert img to array byte
	private byte[] convertStreamtoByte(InputStream image) throws Exception{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = image.read();
		while (reads != -1){
			baos.write(reads);
			reads = image.read();
		}
		
		return baos.toByteArray();
		
	}
}
