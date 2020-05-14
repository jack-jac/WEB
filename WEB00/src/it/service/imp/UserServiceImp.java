package it.service.imp;

import java.sql.SQLException;

import it.dao.UserDao;
import it.dao.imp.UserDaoImp;
import it.service.UserService;

public class UserServiceImp implements UserService {

	public boolean checkNameByUserName(String username) throws SQLException {
		//µ÷ÓÃdao²ã
		UserDao dao = new UserDaoImp();
		return dao.checkNameByUserName(username);
	}
	
}
