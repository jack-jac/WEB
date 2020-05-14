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
	// �������в�Ʒ�����ؼ���
	public List<Product> findAllProduct() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	// ����pid��ѯ��Ʒ
	public Product findProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	// ���ݽǱ�ֲ���ѯ����,limit������һ���ķ�Χ��
	public List<Product> findProductListByFen(int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), index, currentCount);
	}

	// ��ѯ���ݿ��е�Product��������ʹ��scalar���췽�������long����
	public int findProductTotalCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product";
		Long totalCount = (Long) runner.query(sql, new ScalarHandler());
		// long���͵Ķ������תΪint���͵�ֵ����Ӧ�Ķ����ж�Ӧ�ķ�����
		return totalCount.intValue();

	}

	// ����name����ģ����ѯ
	public List<Object> searchProductNameByName(String pname) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname like ? limit 0,8";
		List<Object> list = runner.query(sql, new ColumnListHandler("pname"), "%" + pname + "%");
		return list;

	}

	// ������������
	public List<Category> findProductCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	// ���product
	public void addProduct(Product p) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, p.getPid(), p.getPname(), p.getMarket_price(), p.getShop_price(), p.getPimage(),
				p.getPdate(), p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCid());
	}

	// ����pidɾ����Ʒ
	public void delProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		runner.update(sql, pid);
	}

	// ����pid�޸���Ϣ
	public void editProductByPid(Product p, String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		runner.update(sql, p.getPname(), p.getMarket_price(), p.getShop_price(), p.getPimage(), p.getPdate(),
				p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCid(), pid);
	}

	// ����pname��ѯ��Ʒ
	public Product findProductByPname(String pname) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pname);
	}

	// ������ѯ,��ֹҪ�Ҽӿո�Ҫ���ַ����Ӹ�������ȥ��ǰ��Ŀո��ٽ����ж�
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
