package com.it.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.it.domain.User;
import com.it.service.UserService;


public class AutoLoginFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//强转成HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		
		User user = (User) req.getSession().getAttribute("user");
		
		if(user==null){
			String username_cookie = null;
			String password_cookie = null;
			
			//获取携带用户名和密码cookie
			Cookie[] cookies = req.getCookies();
			if(cookies!=null){
				for(Cookie cookie:cookies){
					//获得想要的cookie
					if("username_cookie".equals(cookie.getName())){
						username_cookie = URLDecoder.decode(cookie.getValue(),"UTF-8");
					}
					if("password_cookie".equals(cookie.getName())){
						password_cookie = cookie.getValue();
					}
				}
			}
			
			if(username_cookie!=null&&password_cookie!=null){
				//去数据库校验该用户名和密码是否正确
				UserService service = new UserService();
				try {
					user = service.login(username_cookie,password_cookie);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//完成自动登录 
				req.getSession().setAttribute("user", user);
				
			}
		}
		
		
		//放行
		chain.doFilter(req, response);
		
	}
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	

	@Override
	public void destroy() {
		
	}

}
