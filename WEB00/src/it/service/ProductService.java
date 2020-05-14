package it.service;

import java.sql.SQLException;
import java.util.List;

import it.domain.Category;
import it.domain.Product;
import it.vo.Condition;
import it.vo.PageBean;

public interface ProductService {
	//�������в�Ʒ�����ؼ���
	List<Product> findAllProduct() throws SQLException;
	//����pid��ѯ��Ʒ
	Product findProductById(String pid) throws SQLException;
	//���ݷ�ҳ������Ϣ
	PageBean<Product> findProductListByFen(int currenPage, int currenCount) throws SQLException;
	//����name����ģ����ѯ
	List<Object> searchProductNameByName(String pname) throws SQLException;
	//������������
	List<Category> findProductCategory() throws SQLException;
	//���product
	void addProduct(Product product) throws SQLException;
	//����pidɾ����Ʒ
	void delProductByPid(String pid) throws SQLException;
	//����pid�޸Ĳ�Ʒ��Ϣ
	void editProductByPid(Product product, String pid) throws SQLException;
	//����pname��ѯ��Ʒ
	Product findProductByPname(String pname) throws SQLException;
	//������ѯ
	List<Product> searchProductListByCondition(Condition condition) throws SQLException;

}
