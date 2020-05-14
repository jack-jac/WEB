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
		// ��֤���У��
		boolean checkImg = CheckUtil.checkImg(request, "checkcode_session");
		if (!checkImg) {
			request.setAttribute("checkImgInfo", "��֤�����");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		// System.out.println(request.getParameter("name"));
		// ������
		User user = null;
		try {
			user = BeanUtil.fillBean(request, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �ֶ���װuid
		user.setUid(UUID.randomUUID().toString());
		// ����service��
		RegisterService service = new RegisterService();
		try {
			service.registe(user);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// ��ת����¼ҳ��
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}