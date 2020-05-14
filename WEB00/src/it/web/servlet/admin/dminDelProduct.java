package it.web.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.service.ProductService;
import it.service.imp.ProductServiceImp;

public class dminDelProduct extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//根据pid删除产品
		String pid = request.getParameter("pid");
		ProductService service = new ProductServiceImp();
		try {
			service.delProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminProductListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}