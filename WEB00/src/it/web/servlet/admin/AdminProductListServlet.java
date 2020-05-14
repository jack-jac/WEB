package it.web.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.domain.Category;
import it.domain.Product;
import it.service.ProductService;
import it.service.imp.ProductServiceImp;

public class AdminProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用service层
		//查询所有的数据
		ProductService service = new ProductServiceImp();
		List<Product> productList = null;
		try {
			productList = service.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Category> categoryList = null;
		try {
			categoryList = service.findProductCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}