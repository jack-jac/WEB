package it.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.service.ProductService;
import it.service.imp.ProductServiceImp;

public class DelAllProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得参数
		String pids = request.getParameter("pids");
		//System.out.println(pids);
		String[] pid = pids.split(",");
		for(int i=0;i<pid.length;i++) {
			//System.out.println(pid[i]);
			//根据pid删除数据
			ProductService service = new ProductServiceImp();
			try {
				service.delProductByPid(pid[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect(request.getContextPath()+"/adminProductListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}