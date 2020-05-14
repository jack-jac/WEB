package it.dao;

import java.sql.SQLException;

public interface UserDao {

	boolean checkNameByUserName(String username) throws SQLException;

}
