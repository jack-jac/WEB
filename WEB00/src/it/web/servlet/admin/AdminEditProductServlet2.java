package it.web.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.domain.Product;
import it.service.ProductService;
import it.service.imp.ProductServiceImp;
import it.utils.BeanUtil;

public class AdminEditProductServlet2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//根据pid，改写内容
		String pid = request.getParameter("pid");
		//把数据封装
		Product product = null;
		try {
			product = BeanUtil.fillBean(request, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//填充信息
		product.getPname();
		//1,img
		product.setPimage("products/1/cao.jpg");
		//2,上架与下架信息
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(pdate);
		//设置下架标志
		product.setPflag(0);
		//调用service层
		ProductService service = new ProductServiceImp();
		try {
			service.editProductByPid(product,pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminProductListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}