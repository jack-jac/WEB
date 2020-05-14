package it.web.servlet.product;

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

public class ProductListFenServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//分页查找,数据多时，或需要时，就创建一个对象用于存储数据
		//根据数据库的总数，封装一个pageBean对象，用于传给jsp页面显示
		//根据当前页数，与当前页显示的个数查询
		String currentPage_str = request.getParameter("currentPage");
		String currentCount_str = request.getParameter("currentCount");
		
		int currentPage = 1;
		int currentCount = 12;
		if(currentPage_str!=null) {
			currentPage = Integer.parseInt(currentPage_str);
		}
		if(currentCount_str!=null) {
			currentCount = Integer.parseInt(currentCount_str);
		}
		ProductService service = new ProductServiceImp();
		PageBean<Product> pageBean = null;
		try {
			pageBean = service.findProductListByFen(currentPage,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("pageBean", pageBean);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}