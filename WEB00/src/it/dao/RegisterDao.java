package it.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import it.domain.User;
import it.utils.DataSourceUtils;

public class RegisterDao {

	public void registe(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName()
				,user.getEmail(),null,user.getBirthday(),user.getSex(),user.getState(),null);
	}

}
