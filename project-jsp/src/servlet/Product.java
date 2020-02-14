package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProductJsp;
import dao.DaoProduct;

@WebServlet("/saveProduct")
public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoProduct daoProduct = new DaoProduct();

	public Product() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "allList";
			String product = request.getParameter("product");
			
			RequestDispatcher view = request.getRequestDispatcher("/registerProduct.jsp");

			if (acao.equalsIgnoreCase("delete")) {
				daoProduct.delete(product);
				request.setAttribute("products", daoProduct.list());
			}else if (acao.equalsIgnoreCase("edit")){
				BeanProductJsp beanProductJsp = daoProduct.consult(product);
				request.setAttribute("product", beanProductJsp);
			}else if(acao.equalsIgnoreCase("allList")){
				request.setAttribute("products", daoProduct.list());
			}
			request.setAttribute("categories", daoProduct.listCategory());
			view.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && acao.equalsIgnoreCase("reset")){
			try {
				RequestDispatcher view = request.getRequestDispatcher("/registerProduct.jsp");
				request.setAttribute("products", daoProduct.list());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
		
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String quantity = request.getParameter("quantity");
			String value = request.getParameter("value");
			String category = request.getParameter("category_id");
	
			BeanProductJsp product = new BeanProductJsp();
			product.setId(!id.isEmpty()? Long.parseLong(id) : null);
			product.setName(name);
			product.setCategory_id(Long.parseLong(category));
			
			if(quantity != null && !quantity.isEmpty())
				product.setQuantity(!quantity.isEmpty()? Double.parseDouble(quantity) : null);
			if(value != null && !value.isEmpty()){
				String parseValue = value.replaceAll("\\.", "");
				parseValue = parseValue.replaceAll("\\,", ".");
				product.setValue(!value.isEmpty()? Double.parseDouble(parseValue) : null);
			}

			try {
				
				//Required fields
				if (name == null || name.isEmpty()){
					request.setAttribute("msg", "Name required!");
					request.setAttribute("product", product);
				}else if (quantity == null || quantity.isEmpty()){
					request.setAttribute("msg", "Quantity required!");
					request.setAttribute("product", product);
				}else if (value == null || value.isEmpty()){
					request.setAttribute("msg", "Value required!");
					request.setAttribute("product", product);
				}
				
				//Insert
				else if(id == null || id.isEmpty() && !daoProduct.validateName(name)){ 
					request.setAttribute("msg","There is already a product with this name!");
					request.setAttribute("product", product);
				}else if (id == null || id.isEmpty() &daoProduct.validateName(name)) {
					daoProduct.saveProduct(product);
				}
				
				//Update
				else if (id != null && !id.isEmpty() && !daoProduct.validateNameUpdate(name, id)) { 
					request.setAttribute("msg","There is already a product with this name!");
					request.setAttribute("product", product);
				} else{
					daoProduct.update(product);
				}
								
				RequestDispatcher view = request.getRequestDispatcher("/registerProduct.jsp");
				request.setAttribute("products", daoProduct.list());
				request.setAttribute("categories", daoProduct.listCategory());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
