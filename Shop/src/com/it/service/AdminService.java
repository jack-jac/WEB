package com.it.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.it.dao.AdminDao;
import com.it.domain.Order;
import com.it.domain.Product;

public class AdminService {

	public void addProduct(Product product) {
		//调用dao层
		AdminDao dao = new AdminDao();
		try {
			dao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> findAllOrderItem(String oid) {
		AdminDao dao = new AdminDao();
		List<Map<String, Object>> map = null;
		try {
			map = dao.findAllOrderItem(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public List<Order> findAllOrder() {
		AdminDao dao = new AdminDao();
		List<Order> list = null;
		try {
			list = dao.findAllOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
