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
import it.utils.BeanUtil;
import it.vo.Condition;

public class AdminSearchProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//把信息封装成一个对象，condition 条件,条件查询
		Condition condition = null;
		try {
			condition = BeanUtil.fillBean(request, Condition.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProductService service = new ProductServiceImp();
		List<Product> productList = null;
		try {
			productList = service.searchProductListByCondition(condition);
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
		request.setAttribute("condition", condition);
//		System.out.println(productList.size());
//		System.out.println(condition);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}