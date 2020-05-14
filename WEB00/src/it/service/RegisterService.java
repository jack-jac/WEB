package it.service;

import java.sql.SQLException;

import it.dao.RegisterDao;
import it.domain.User;

public class RegisterService {

	public void registe(User user) throws SQLException {
		//µ÷ÓÃdao²ã
		RegisterDao dao = new RegisterDao();
		dao.registe(user);
	}

}
