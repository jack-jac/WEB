package com.it.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.it.domain.Cart;
import com.it.domain.CartItem;
import com.it.domain.Category;
import com.it.domain.Order;
import com.it.domain.OrderItem;
import com.it.domain.PageBean;
import com.it.domain.Product;
import com.it.domain.User;
import com.it.service.ProductService;
import com.it.utils.CommonsUtils;
import com.it.utils.JedisPoolUtils;
import com.it.utils.PaymentUtil;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Commons;

import redis.clients.jedis.Jedis;
@SuppressWarnings("all")
public class PrdocutServlet extends BaseServlet {

	/*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获得请求的哪个方法的method
		String methodName = request.getParameter("method");
		if("productList".equals(methodName)){
			productList(request,response);
		}else if("categoryList".equals(methodName)){
			categoryList(request,response);
		}else if("index".equals(methodName)){
			index(request,response);
		}else if("productInfo".equals(methodName)){
			productInfo(request,response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	 */
	//我的订单，显示订单
	public void myOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//先判断用户是否已经登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null) {
			//跳转到登录页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return ;
		}
		//封装orderlist集合，显示到相应的jsp页面
		//查询数据库，获得相应的信息
		ProductService service = new ProductService();
		List<Order> orderList = service.findAllOrder(user.getUid());
		//封装内部信息
		if(orderList!=null) {
			for(Order order : orderList) {
				//对内部信息进行封装
				//查询数据库，获得orderitem 
				//service.findOrderItem(); 可以使用单表一个一个查，也可以直接使用多表查询
				List<Map<String, Object>> mapList = service.findMapListByOid(order.getOid());
				//list内部有多个orderitem与product的信息
				for(Map<String, Object> map:mapList) {
					//可以单个的进行填充数据，也可以直接填充所有的数据
					try {
						OrderItem orderItem = new OrderItem();
						Product product = new Product();
						BeanUtils.populate(product, map);
						BeanUtils.populate(orderItem, map);
						//填充后，把数据放入order内部
						orderItem.setProduct(product);
						order.getOrderItems().add(orderItem);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//填充完毕，把orderlist传入jsp进行显示
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	
	}
	//确认订单
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order order = new Order();
		/*String name = request.getParameter("name");
		String address = request.getParameter("address");
		String telephone = request.getParameter("telephone");*/
		/*order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);*/
		//填充对象
		Map<String, String[]> properties = request.getParameterMap();
		try {
			BeanUtils.populate(order, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		ProductService service = new ProductService();
		service.addAddress(order);
		
		//在线支付
		//跳转到页面，支付，然后改变原本的支付状态
		/*String orderid = request.getParameter("orderid");
		String money = order.getTotal()+"";
		// 银行
		String pd_FrpId = request.getParameter("pd_FrpId");

		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = orderid;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		
		
		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
						"&p0_Cmd="+p0_Cmd+
						"&p1_MerId="+p1_MerId+
						"&p2_Order="+p2_Order+
						"&p3_Amt="+p3_Amt+
						"&p4_Cur="+p4_Cur+
						"&p5_Pid="+p5_Pid+
						"&p6_Pcat="+p6_Pcat+
						"&p7_Pdesc="+p7_Pdesc+
						"&p8_Url="+p8_Url+
						"&p9_SAF="+p9_SAF+
						"&pa_MP="+pa_MP+
						"&pr_NeedResponse="+pr_NeedResponse+
						"&hmac="+hmac;

		//重定向到第三方支付平台
		response.sendRedirect(url);*/
		response.sendRedirect(request.getContextPath()+"/callback.jsp");
	}
	//提交订单
	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//先判断是否登录，若没登录，就需要先登录
		User user = (User) session.getAttribute("user");
		if(user==null) {
			//跳转到登录页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return ;
		}
		//封装对象,把数据封装到数据库中
		Cart cart = (Cart) session.getAttribute("cart");
		Order order = new Order();
		//封装数据
		order.setOid(CommonsUtils.getUUID());
		order.setOrdertime(new Date());
		double total = cart.getTotal();
		order.setTotal(total);
		order.setState(0);
		order.setUser(user);
		order.setAddress(null);
		order.setName(null);
		order.setTelephone(null);
		//封装集合,把cartitem转为orderitem
		Map<String, CartItem> cartItems = cart.getCartItems();
		Set<Entry<String, CartItem>> entrySet = cartItems.entrySet();
		for(Map.Entry<String, CartItem> entry:entrySet) {
			CartItem cartItem = entry.getValue();
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setItemid(CommonsUtils.getUUID());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setOrder(order);
			order.getOrderItems().add(orderItem);
		}
		//封装数据完毕
		ProductService service = new ProductService();
		service.submitOrder(order);
		
		session.setAttribute("order", order);
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	}	
	//清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	//删除单一商品
	public void delCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		//获得cart对象
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//根据pid删除
		if(cart!=null) {
			Map<String, CartItem> cartItems = cart.getCartItems();
			//先把总钱数删除
			cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());
			cartItems.remove(pid);
		}
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}


	//将商品添加到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得信息
		HttpSession session = request.getSession();
		String pid = request.getParameter("pid");
		String buyNumStr = request.getParameter("buyNum");
		int buyNum = Integer.parseInt(buyNumStr);
		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		//获得cartItme 购物项
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setBuyNum(buyNum);
		double subtotal = buyNum*product.getShop_price();
		cartItem.setSubtotal(subtotal);
		
		//封装购物车
		//看session中是否已经存在数据
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null) {
			cart=new Cart();
		}
		Map<String, CartItem> cartItems = cart.getCartItems();
		//判断是否是相同的
		if(cartItems.containsKey(pid)) {
			//包含，让其数量相加
			CartItem oldCartItem = cartItems.get(pid);
			int oldBuyNum = oldCartItem.getBuyNum();
			double oldSubtotal = oldCartItem.getSubtotal();
			cartItem.setBuyNum(buyNum+oldBuyNum);
			cartItem.setSubtotal(subtotal+oldSubtotal);
		}
		cartItems.put(pid, cartItem);
		double total = cart.getTotal()+subtotal;
		cart.setTotal(total);
		//把对象存放到session域对象中
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	//显示商品的类别的的功能
	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();

		//先从缓存中查询categoryList 如果有直接使用 没有在从数据库中查询 存到缓存中
		//1、获得jedis对象 连接redis数据库
		//Jedis jedis = JedisPoolUtils.getJedis();
		//String categoryListJson = jedis.get("categoryListJson");
		//2、判断categoryListJson是否为空
		//if(categoryListJson==null){
			//System.out.println("缓存没有数据 查询数据库");
			//准备分类数据
			List<Category> categoryList = service.findAllCategory();
			Gson gson = new Gson();
			String categoryListJson = gson.toJson(categoryList);
			//categoryListJson = gson.toJson(categoryList);
			//jedis.set("categoryListJson", categoryListJson);
		//}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categoryListJson);
	}

	//显示首页的功能
	//显示商品的类别的的功能
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();

		//准备热门商品---List<Product>
		List<Product> hotProductList = service.findHotProductList();

		//准备最新商品---List<Product>
		List<Product> newProductList = service.findNewProductList();

		//准备分类数据
		//List<Category> categoryList = service.findAllCategory();

		//request.setAttribute("categoryList", categoryList);
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);

		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	//显示商品的详细信息功能
	public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获得当前页
		String currentPage = request.getParameter("currentPage");
		//获得商品类别
		String cid = request.getParameter("cid");

		//获得要查询的商品的pid
		String pid = request.getParameter("pid");

		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);

		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);


		//获得客户端携带cookie---获得名字是pids的cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie : cookies){
				if("pids".equals(cookie.getName())){
					pids = cookie.getValue();
					//1-3-2 本次访问商品pid是8----->8-1-3-2
					//1-3-2 本次访问商品pid是3----->3-1-2
					//1-3-2 本次访问商品pid是2----->2-1-3
					//将pids拆成一个数组
					String[] split = pids.split("-");//{3,1,2}
					List<String> asList = Arrays.asList(split);//[3,1,2]
					LinkedList<String> list = new LinkedList<String>(asList);//[3,1,2]
					//判断集合中是否存在当前pid
					if(list.contains(pid)){
						//包含当前查看商品的pid
						list.remove(pid);
						list.addFirst(pid);
					}else{
						//不包含当前查看商品的pid 直接将该pid放到头上
						list.addFirst(pid);
					}
					//将[3,1,2]转成3-1-2字符串
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<list.size()&&i<7;i++){
						sb.append(list.get(i));
						sb.append("-");//3-1-2-
					}
					//去掉3-1-2-后的-
					pids = sb.substring(0, sb.length()-1);
				}
			}
		}


		Cookie cookie_pids = new Cookie("pids",pids);
		response.addCookie(cookie_pids);

		request.getRequestDispatcher("/product_info.jsp").forward(request, response);

	}

	//根据商品的类别获得商品的列表
	public void productListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获得cid
		String cid = request.getParameter("cid");

		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null) currentPageStr="1";
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;

		ProductService service = new ProductService();
		PageBean pageBean = service.findProductListByCid(cid,currentPage,currentCount);

		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		//定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();

		//获得客户端携带名字叫pids的cookie
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("pids".equals(cookie.getName())){
					String pids = cookie.getValue();//3-2-1
					String[] split = pids.split("-");
					for(String pid : split){
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}
		}

		//将历史记录的集合放到域中
		request.setAttribute("historyProductList", historyProductList);

		request.getRequestDispatcher("/product_list.jsp").forward(request, response);


	}

}