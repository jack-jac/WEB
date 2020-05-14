package it.web.servlet.product;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.domain.Product;
import it.service.ProductService;
import it.service.imp.ProductServiceImp;

public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ��Ҫ��ѯ����Ϣ�����ݴ�������pid���Ʒ����ʾ��Ʒ��Ϣ
		String pid = request.getParameter("pid");
		ProductService service = new ProductServiceImp();
		Product product = null;
		try {
			product = service.findProductById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("product", product);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}