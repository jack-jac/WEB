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
		//��֤��У��
		boolean checkImg = CheckUtil.checkImg(request, "checkcode_session");
		if(!checkImg) {
			request.setAttribute("checkImgInfo", "��֤�����");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}
		//��ȡ��Ҫ��ѯ����Ϣ
		HttpSession session = request.getSession();
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		//����service��
		LoginService service = new LoginService();
		User user = null;
		try {
			user = service.login(name,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user!=null) {
			//��¼�ɹ�
				//�ж��Ƿ����Զ���¼
				String auto = request.getParameter("autoLogin");
				//System.out.println(auto);
				if(auto!=null) {
					//�Զ���¼
					//����cookie �����������ִ����ͻ���Ȼ�����ͨ���������Զ���¼
					//������session�����Խ�ʡ�ռ�
					//1����Ϊcookie���ܴ���������Ҫ���б��룬Ȼ���ٹ��������ٴν���
					String En_name = URLEncoder.encode(name,"UTF-8");
					//2������cookie
					Cookie cookie_username = new Cookie("cookie_username", En_name);
					Cookie cookie_password = new Cookie("cookie_password", password);
					//3,����cookie�Ĵ���ʱ��
					cookie_username.setMaxAge(60*60);
					cookie_password.setMaxAge(60*60);
					//4,����cookie�����·��
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					//5������cookie�����cookie
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);
				}
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}else {
			//��¼ʧ��
			request.setAttribute("loginInfo", "�����û��������������!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}