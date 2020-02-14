package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanPhoneJsp;
import beans.BeanProjectJsp;
import dao.DaoPhone;
import dao.DaoUser;

@WebServlet("/savePhones")
public class Phones extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUser daoUser = new DaoUser();
	private DaoPhone daoPhone = new DaoPhone();

	public Phones() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			String action = request.getParameter("acao");
			String user = request.getParameter("user");
			
			if (user != null) {

				BeanProjectJsp userSearch = daoUser.consult(user);

				if (action.equalsIgnoreCase("addPhone")) {
					request.getSession().setAttribute("userChosen", userSearch);
					request.setAttribute("userChosen", userSearch);
					RequestDispatcher view = request
							.getRequestDispatcher("/registerPhone.jsp");
					request.setAttribute("phones",
							daoPhone.list(userSearch.getId()));
					view.forward(request, response);

				} else if (action.equalsIgnoreCase("deletePhone")) {
					String phoneId = request.getParameter("phoneId");
					daoPhone.delete(phoneId);

					BeanProjectJsp beanProjectJsp = (BeanProjectJsp) request
							.getSession().getAttribute("userChosen");
					RequestDispatcher view = request
							.getRequestDispatcher("/registerPhone.jsp");
					request.setAttribute("userChosen", userSearch);
					request.setAttribute("phones",
							daoPhone.list(Long.parseLong(user)));
					request.setAttribute("msg", "Delete successfully!");
					view.forward(request, response);
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

		try {

			BeanProjectJsp beanProjectJsp = (BeanProjectJsp) request
					.getSession().getAttribute("userChosen");

			String number = request.getParameter("number");
			String type = request.getParameter("type");
			
			String action = request.getParameter("acao");
			if(action == null || !action.equalsIgnoreCase("return")){
			
			if (number == null || number.isEmpty()){
				RequestDispatcher view = request.getRequestDispatcher("/registerPhone.jsp");
				request.setAttribute("phones", daoPhone.list(beanProjectJsp.getId()));
				request.setAttribute("msg", "Phone number is required!");
				view.forward(request, response);	
			}else{
			
				BeanPhoneJsp beanPhoneJsp = new BeanPhoneJsp();
				beanPhoneJsp.setNumber(number);
				beanPhoneJsp.setType(type);
				beanPhoneJsp.setUser_id(beanProjectJsp.getId());

				daoPhone.savePhone(beanPhoneJsp);

				request.getSession().setAttribute("userChosen", beanProjectJsp);
				request.setAttribute("userChosen", beanProjectJsp);

				RequestDispatcher view = request.getRequestDispatcher("/registerPhone.jsp");
				request.setAttribute("phones",daoPhone.list(beanProjectJsp.getId()));
				request.setAttribute("msg", "Saved successfully!");
				view.forward(request, response);
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

}
