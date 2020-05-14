package it.web.servlet.product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.domain.Product;
import it.service.ProductService;
import it.service.imp.ProductServiceImp;

public class ProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//显示列表，所以直接获取信息，传给相应的jsp显示
		ProductService service = new ProductServiceImp();
		List<Product> productList = null;
		try {
			productList = service.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}