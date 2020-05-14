package it.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import it.dao.ProductDao;
import it.domain.Category;
import it.domain.Product;
import it.utils.DataSourceUtils;
import it.vo.Condition;

public class ProductDaoImp implements ProductDao {
	// 查找所有产品，返回集合
	public List<Product> findAllProduct() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	// 根据pid查询产品
	public Product findProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	// 根据角标局部查询数据,limit限制在一定的范围内
	public List<Product> findProductListByFen(int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), index, currentCount);
	}

	// 查询数据库中的Product的总数，使用scalar构造方法，获得long对象。
	public int findProductTotalCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product";
		Long totalCount = (Long) runner.query(sql, new ScalarHandler());
		// long类型的对象可以转为int类型的值，对应的对象有对应的方法。
		return totalCount.intValue();

	}

	// 根据name进行模糊查询
	public List<Object> searchProductNameByName(String pname) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname like ? limit 0,8";
		List<Object> list = runner.query(sql, new ColumnListHandler("pname"), "%" + pname + "%");
		return list;

	}

	// 查找所有类型
	public List<Category> findProductCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	// 添加product
	public void addProduct(Product p) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, p.getPid(), p.getPname(), p.getMarket_price(), p.getShop_price(), p.getPimage(),
				p.getPdate(), p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCid());
	}

	// 根据pid删除产品
	public void delProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		runner.update(sql, pid);
	}

	// 根据pid修改信息
	public void editProductByPid(Product p, String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		runner.update(sql, p.getPname(), p.getMarket_price(), p.getShop_price(), p.getPimage(), p.getPdate(),
				p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCid(), pid);
	}

	// 根据pname查询产品
	public Product findProductByPname(String pname) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pname);
	}

	// 条件查询,防止要乱加空格，要把字符串加个方法，去掉前后的空格，再进行判断
	public List<Product> searchProductListByCondition(Condition condition) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		StringBuilder sql = new StringBuilder("select * from product where 1=1");
		ArrayList<String> con = new ArrayList<String>();
		if (condition.getPname() != null && !condition.getPname().trim().equals("")) {
			sql.append(" and pname like ? ");
			con.add("%"+condition.getPname().trim()+"%");
		}
		if (condition.getIs_hot() != null && !condition.getIs_hot().trim().equals("")) {
			sql.append(" and is_hot=? ");
			con.add(condition.getIs_hot().trim());
		}
		if (condition.getCid() != null && !condition.getCid().trim().equals("")) {
			sql.append(" and cid=? ");
			con.add(condition.getCid().trim());
		}
		
		return runner.query(sql.toString(), new BeanListHandler<Product>(Product.class),con.toArray());
	}

}
