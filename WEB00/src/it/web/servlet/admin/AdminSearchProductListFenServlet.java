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

public class AdminSearchProductListFenServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPage_str = request.getParameter("currentPage");
		String currentCount_str = request.getParameter("currentCount");

		int currentPage = 1;
		int currentCount = 4;
		if (currentPage_str != null) {
			currentPage = Integer.parseInt(currentPage_str);
		}
		if (currentCount_str != null) {
			currentCount = Integer.parseInt(currentCount_str);
		}
		ProductService service = new ProductServiceImp();
		PageBean<Product> pageBean = null;
		//调用的时候需要传进查找的条件，根据查找的条件查到的集合然后判断总数，然后填充pageBean。每次限制数量。
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
		// list集合
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