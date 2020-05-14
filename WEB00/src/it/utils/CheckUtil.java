package it.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CheckUtil {
	public static boolean checkImg(HttpServletRequest request,String check_session_name) {
		HttpSession session = request.getSession();
		String checkImg_s = (String) session.getAttribute(check_session_name);
		String checkImg = request.getParameter("checkImg");
		return checkImg_s.equals(checkImg);
	}
}
