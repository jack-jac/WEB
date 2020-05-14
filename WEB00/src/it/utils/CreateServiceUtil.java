package it.utils;

import java.util.ResourceBundle;

import it.service.ProductService;

public class CreateServiceUtil {
	public static ProductService createProductServiceImp() {
		
		try {
			ResourceBundle rb = ResourceBundle.getBundle("service");
			String className = rb.getString("productServiceImp");
			return (ProductService) Class.forName(className).newInstance();
		} catch (Exception e) {
			 throw new RuntimeException(e);
		} 
	}
}
