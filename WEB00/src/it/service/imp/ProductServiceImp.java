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
	//�������в�Ʒ�����ؼ���
	public List<Product> findAllProduct() throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findAllProduct();
	}
	//����pid��ѯ��Ʒ
	public Product findProductById(String pid) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findProductById(pid);
	}
	//���ݷ�ҳ������Ϣ
	public PageBean<Product> findProductListByFen(int currentPage, int currentCount) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		PageBean<Product> pageBean = new PageBean<Product>();
		//��Ҫ����ҵ�񣬰ѽǱ괫��dao��
		//��Ҫ��pageBean��װ�󷵻ظ�servlet��
		pageBean.setCurrentCount(currentCount);
		pageBean.setCurrentPage(currentPage);
		int index = (currentPage-1)*currentCount;
		List<Product> productList = dao.findProductListByFen(index,currentCount);
		pageBean.setList(productList);
		//������
		int totalCount = dao.findProductTotalCount();
		pageBean.setTotalCount(totalCount);
		//int totalPage = totalCount/currentCount+1;
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		return pageBean;
	}
	//����name����ģ����ѯ
	public List<Object> searchProductNameByName(String pname) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.searchProductNameByName(pname);
	}
	//������������
	public List<Category> findProductCategory() throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findProductCategory();
	}
	//���product
	public void addProduct(Product product) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		dao.addProduct(product);
	}
	//����pidɾ����Ʒ
	public void delProductByPid(String pid) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		dao.delProductByPid(pid);
	}
	//����pid�޸���Ϣ
	public void editProductByPid(Product product, String pid) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		dao.editProductByPid(product,pid);
	}
	//����pname��ѯ��Ʒ
	public Product findProductByPname(String pname) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.findProductByPname(pname);
	}
	//������ѯ
	public List<Product> searchProductListByCondition(Condition condition) throws SQLException {
		ProductDao dao = new ProductDaoImp();
		return dao.searchProductListByCondition(condition);
	}

}
