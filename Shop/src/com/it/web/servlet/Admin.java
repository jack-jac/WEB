package com.it.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.it.domain.Category;
import com.it.domain.Order;
import com.it.service.AdminService;
import com.it.service.ProductService;

public class Admin extends BaseServlet {
	
	//查询订单详情
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String oid = request.getParameter("oid");
		AdminService service = new AdminService();
		List<Map<String, Object>> map = service.findAllOrderItem(oid);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		
	}
	
	
	//查询订单
	public void findAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//直接调用service层
		AdminService service = new AdminService();
		List<Order> orderList = service.findAllOrder();
		//放到jsp显示
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}
	
	
	//查询种类
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从数据库查询数据
		ProductService service = new ProductService();
		List<Category> categoryList = service.findAllCategory();
		//把集合转为数组
		
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
	}
	

	
}