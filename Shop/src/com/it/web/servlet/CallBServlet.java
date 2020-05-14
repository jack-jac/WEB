package com.it.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.it.domain.Order;
import com.it.service.ProductService;

public class CallBServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//把相应的数据改为已经支付
		ProductService service = new ProductService();
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");
		service.updateState(order.getOid());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}