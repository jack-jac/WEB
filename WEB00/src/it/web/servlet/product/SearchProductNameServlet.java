package it.web.servlet.product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.service.ProductService;
import it.service.imp.ProductServiceImp;

public class SearchProductNameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取信息，模糊查询
		String pname = request.getParameter("pname");
		//调用service层
		ProductService service = new ProductServiceImp();
		List<Object> nameList = null;
		try {
			nameList = service.searchProductNameByName(pname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//借用工具把对应的对象转为json对象字符串，然后传输
		Gson g = new Gson();
		String json = g.toJson(nameList);
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}