package com.it.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	//需要有个集合装对象
	private Map<String,CartItem> cartItems = new HashMap<String,CartItem>();
	//总计
	private double total;
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
