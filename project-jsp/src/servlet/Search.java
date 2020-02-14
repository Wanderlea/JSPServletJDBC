package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProjectJsp;
import dao.DaoUser;

@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUser daoUser = new DaoUser();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchDescription = request.getParameter("searchDescription");
		if(searchDescription != null && !searchDescription.trim().isEmpty()){
			try {
				List<BeanProjectJsp> listSearch = daoUser.list(searchDescription);
				
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", listSearch);
				view.forward(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				RequestDispatcher view = request.getRequestDispatcher("/registerUser.jsp");
				request.setAttribute("users", daoUser.list());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
