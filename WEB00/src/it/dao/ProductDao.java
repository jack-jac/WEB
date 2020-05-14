package it.dao;

import java.sql.SQLException;
import java.util.List;

import it.domain.Category;
import it.domain.Product;
import it.vo.Condition;

public interface ProductDao {
	//查找所有产品，返回集合
	List<Product> findAllProduct() throws SQLException;
	//根据pid查询产品
	Product findProductById(String pid) throws SQLException;
	//根据角标局部查询数据
	List<Product> findProductListByFen(int currenPage, int currenCount) throws SQLException;
	
	//查询数据库中product的总数
	int findProductTotalCount() throws SQLException;
	/*
	 * 根据name进行模糊查询
	 */
	List<Object> searchProductNameByName(String pname) throws SQLException;
	//查找所有类型
	List<Category> findProductCategory() throws SQLException;
	//添加product
	void addProduct(Product product) throws SQLException;
	//根据pid删除产品
	void delProductByPid(String pid) throws SQLException;
	//根据pid修改信息
	void editProductByPid(Product product, String pid) throws SQLException;
	//根据pname查询产品
	Product findProductByPname(String pname) throws SQLException;
	//条件查询
	List<Product> searchProductListByCondition(Condition condition) throws SQLException;
	
}
