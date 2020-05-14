package it.service.imp;

import java.sql.SQLException;
import java.util.List;

import it.dao.ProductDao;
import it.dao.imp.ProductDaoImp;
import it.domain.Category;
import it.domain.Product;
import it.service.ProductService;
import it.vo.Condition;
import it.vo.PageBean;

public class ProductServiceImp implements ProductService {
	//查找所有产品，返回集合
	public List<Product> findAllProduct() throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findAllProduct();
	}
	//根据pid查询产品
	public Product findProductById(String pid) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findProductById(pid);
	}
	//根据分页处理信息
	public PageBean<Product> findProductListByFen(int currentPage, int currentCount) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		PageBean<Product> pageBean = new PageBean<Product>();
		//需要处理业务，把角标传给dao层
		//需要把pageBean封装后返回给servlet层
		pageBean.setCurrentCount(currentCount);
		pageBean.setCurrentPage(currentPage);
		int index = (currentPage-1)*currentCount;
		List<Product> productList = dao.findProductListByFen(index,currentCount);
		pageBean.setList(productList);
		//查总数
		int totalCount = dao.findProductTotalCount();
		pageBean.setTotalCount(totalCount);
		//int totalPage = totalCount/currentCount+1;
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		return pageBean;
	}
	//根据name进行模糊查询
	public List<Object> searchProductNameByName(String pname) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.searchProductNameByName(pname);
	}
	//查找所有类型
	public List<Category> findProductCategory() throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findProductCategory();
	}
	//添加product
	public void addProduct(Product product) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		dao.addProduct(product);
	}
	//根据pid删除产品
	public void delProductByPid(String pid) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		dao.delProductByPid(pid);
	}
	//根据pid修改信息
	public void editProductByPid(Product product, String pid) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		dao.editProductByPid(product,pid);
	}
	//根据pname查询产品
	public Product findProductByPname(String pname) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findProductByPname(pname);
	}
	//条件查询
	public List<Product> searchProductListByCondition(Condition condition) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.searchProductListByCondition(condition);
	}

}
