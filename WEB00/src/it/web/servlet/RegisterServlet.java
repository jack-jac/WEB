package it.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import it.domain.User;
import it.service.RegisterService;
import it.utils.BeanUtil;
import it.utils.CheckUtil;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 验证码的校验
		boolean checkImg = CheckUtil.checkImg(request, "checkcode_session");
		if (!checkImg) {
			request.setAttribute("checkImgInfo", "验证码错误！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		// System.out.println(request.getParameter("name"));
		// 填充对象
		User user = null;
		try {
			user = BeanUtil.fillBean(request, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 手动封装uid
		user.setUid(UUID.randomUUID().toString());
		// 调用service层
		RegisterService service = new RegisterService();
		try {
			service.registe(user);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// 跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}