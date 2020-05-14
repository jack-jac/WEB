package it.service;

import java.sql.SQLException;

import it.dao.LoginDao;
import it.domain.User;

public class LoginService {

	public User login(String name, String password) throws SQLException {
		//µ÷ÓÃdao²ã
		LoginDao dao = new LoginDao();
		return dao.login(name,password);
	}
	
}
