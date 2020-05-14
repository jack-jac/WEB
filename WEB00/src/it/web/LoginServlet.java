package it.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.domain.User;
import it.service.LoginService;
import it.utils.CheckUtil;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//验证码校验
		boolean checkImg = CheckUtil.checkImg(request, "checkcode_session");
		if(!checkImg) {
			request.setAttribute("checkImgInfo", "验证码错误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}
		//获取需要查询的信息
		HttpSession session = request.getSession();
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		//调用service层
		LoginService service = new LoginService();
		User user = null;
		try {
			user = service.login(name,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user!=null) {
			//登录成功
				//判断是否是自动登录
				String auto = request.getParameter("autoLogin");
				//System.out.println(auto);
				if(auto!=null) {
					//自动登录
					//创建cookie 把密码与名字传给客户，然后可以通过过滤器自动登录
					//不创建session，可以节省空间
					//1，因为cookie不能存中文所以要进行编码，然后再过滤器中再次解码
					String En_name = URLEncoder.encode(name,"UTF-8");
					//2，创建cookie
					Cookie cookie_username = new Cookie("cookie_username", En_name);
					Cookie cookie_password = new Cookie("cookie_password", password);
					//3,设置cookie的存在时间
					cookie_username.setMaxAge(60*60);
					cookie_password.setMaxAge(60*60);
					//4,设置cookie跟随的路径
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					//5，发送cookie，添加cookie
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);
				}
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}else {
			//登录失败
			request.setAttribute("loginInfo", "您的用户名或者密码错误!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}