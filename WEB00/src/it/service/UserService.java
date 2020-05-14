package it.service;

import java.sql.SQLException;

public interface UserService {

	boolean checkNameByUserName(String username) throws SQLException;
	
}
