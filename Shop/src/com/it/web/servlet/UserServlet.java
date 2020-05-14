package com.it.web.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.it.domain.User;
import com.it.service.UserService;

public class UserServlet extends BaseServlet {

	// 注销
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 需要把session中的对象删除，为了防止自动登录的filter，所以要把cookie中的内容清空
		session.removeAttribute("user");
		Cookie username_cookie = new Cookie("username_cookie", "");
		username_cookie.setMaxAge(0);
		Cookie password_cookie = new Cookie("password_cookie", "");
		password_cookie.setMaxAge(0);
		response.addCookie(username_cookie);
		response.addCookie(password_cookie);
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	// 用户登录
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获得信息
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String autoLogin = request.getParameter("autoLogin");
		UserService service = new UserService();
		User user = service.login(username, password);
		if (user != null) {
			// 登录成功
			// 判断是否是自动登录
			if (autoLogin != null) {
				// 自动登录
				// 设置cookie
				// cookie不能存入中文所以要进项编码
				String en_username = URLEncoder.encode(username, "UTF-8");
				Cookie username_cookie = new Cookie("username_cookie", en_username);
				Cookie password_cookie = new Cookie("password_cookie", password);
				username_cookie.setMaxAge(60 * 5);
				password_cookie.setMaxAge(60 * 5);
				username_cookie.setPath(request.getContextPath());
				password_cookie.setPath(request.getContextPath());
				// 5，发送cookie，添加cookie
				response.addCookie(username_cookie);
				response.addCookie(password_cookie);
			}
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/product?method=index");
		} else {
			// 登录失败
			request.setAttribute("loginInfo", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}