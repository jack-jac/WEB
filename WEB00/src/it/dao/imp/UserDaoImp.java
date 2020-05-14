package it.dao.imp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import it.dao.UserDao;
import it.domain.User;
import it.utils.DataSourceUtils;

public class UserDaoImp implements UserDao {
	//根据名字查是否存在
	public boolean checkNameByUserName(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=?";
		User u = runner.query(sql,new BeanHandler<User>(User.class),username);
		return u==null?false:true;
	}

}
