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
		//����pid����д����
		String pid = request.getParameter("pid");
		//�����ݷ�װ
		Product product = null;
		try {
			product = BeanUtil.fillBean(request, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//�����Ϣ
		product.getPname();
		//1,img
		product.setPimage("products/1/cao.jpg");
		//2,�ϼ����¼���Ϣ
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(pdate);
		//�����¼ܱ�־
		product.setPflag(0);
		//����service��
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