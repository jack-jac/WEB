package it.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.service.UserService;
import it.service.imp.UserServiceImp;

public class ChecknameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得名字
		String username = request.getParameter("username");
		//System.out.println(username);
		//调用service层
		UserService service = new UserServiceImp();
		boolean boo = false;
		try {
			boo = service.checkNameByUserName(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(boo) {
			response.getWriter().write("{\"boo\":\"false\"}");
		}else {
			response.getWriter().write("{\"boo\":\"true\"}");
			
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}