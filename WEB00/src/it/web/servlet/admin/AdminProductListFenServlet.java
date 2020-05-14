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
import it.vo.PageBean;

public class AdminProductListFenServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPage_str = request.getParameter("currentPage");
		String currentCount_str = request.getParameter("currentCount");

		int currentPage = 1;
		int currentCount = 12;
		if (currentPage_str != null) {
			currentPage = Integer.parseInt(currentPage_str);
		}
		if (currentCount_str != null) {
			currentCount = Integer.parseInt(currentCount_str);
		}
		ProductService service = new ProductServiceImp();
		PageBean<Product> pageBean = null;
		try {
			pageBean = service.findProductListByFen(currentPage, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// category
		List<Category> categoryList = null;
		try {
			categoryList = service.findProductCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// list¼¯ºÏ
		List<Product> productList = pageBean.getList();
		request.setAttribute("productList", productList);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("pageBean", pageBean);
		request.getRequestDispatcher("/admin/product/list2.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}