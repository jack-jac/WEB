package it.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.domain.User;
import it.service.LoginService;

public class EncodeFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//设置接收编码
		HttpServletRequest req = (HttpServletRequest) request;
		EnhanceRequest enRequest = new EnhanceRequest(req);
		//设置相应编码
		response.setContentType("text/html;charset=UTF-8");
		//判断是否是自动登录
		Cookie[] cookies = enRequest.getCookies();
		String username = null;
		String password = null;
		if(cookies!=null) {
			//找到需要的cookie
			for(Cookie cookie:cookies ) {
				if("cookie_username".equals(cookie.getName())) {
					//获得cookie中的值，并解码
					String value = cookie.getValue();
					//解码,并赋值
					username = URLDecoder.decode(value,"UTF-8");
					//username = cookie.getName();
				}
				if("cookie_password".equals(cookie.getName())) {
					//password = cookie.getName();
					//获得值,密码不用解码
					password = cookie.getValue();
				}
			}
		}
		if(username!=null && password!=null) {
			//登录
			LoginService service = new LoginService();
			User user = null;
			try {
				user = service.login(username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			HttpSession session = enRequest.getSession();
			session.setAttribute("user", user);
		}
		chain.doFilter(enRequest, response);
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}


	public void destroy() {
	}

}
