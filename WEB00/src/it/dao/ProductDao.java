package it.dao;

import java.sql.SQLException;
import java.util.List;

import it.domain.Category;
import it.domain.Product;
import it.vo.Condition;

public interface ProductDao {
	//�������в�Ʒ�����ؼ���
	List<Product> findAllProduct() throws SQLException;
	//����pid��ѯ��Ʒ
	Product findProductById(String pid) throws SQLException;
	//���ݽǱ�ֲ���ѯ����
	List<Product> findProductListByFen(int currenPage, int currenCount) throws SQLException;
	
	//��ѯ���ݿ���product������
	int findProductTotalCount() throws SQLException;
	/*
	 * ����name����ģ����ѯ
	 */
	List<Object> searchProductNameByName(String pname) throws SQLException;
	//������������
	List<Category> findProductCategory() throws SQLException;
	//���product
	void addProduct(Product product) throws SQLException;
	//����pidɾ����Ʒ
	void delProductByPid(String pid) throws SQLException;
	//����pid�޸���Ϣ
	void editProductByPid(Product product, String pid) throws SQLException;
	//����pname��ѯ��Ʒ
	Product findProductByPname(String pname) throws SQLException;
	//������ѯ
	List<Product> searchProductListByCondition(Condition condition) throws SQLException;
	
}
