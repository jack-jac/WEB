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

public class AdminEditProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����pid�鵽��Ϣ��Ȼ������ݻ���
		String pid = request.getParameter("pid");
		ProductService service = new ProductServiceImp();
		Product product =null;
		try {
			product = service.findProductById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Category> categoryList = null;
		try {
			categoryList = service.findProductCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//����Ϣ���Ե�ҳ��
		request.setAttribute("product", product);
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}