package com.it.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.it.domain.Category;
import com.it.domain.Product;
import com.it.service.AdminService;
import com.it.utils.CommonsUtils;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Commons;

public class AdminAddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 封装数据，
			// 创建磁盘管理的对象
			Map<String,Object> map = new HashMap<String,Object>();
			Product product = new Product();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 1024);
			String path_temp = this.getServletContext().getRealPath("temp");
			factory.setRepository(new File(path_temp));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			
			List<FileItem> parseRequest = upload.parseRequest(request);
			if(parseRequest!=null) {
				for(FileItem item:parseRequest) {
					//判断是否是普通 的表单
					boolean formField = item.isFormField();
					if(formField) {
						//是普通的
						String fieldName = item.getFieldName();
						String value = item.getString("UTF-8");
						System.out.println(fieldName+":"+value);
						map.put(fieldName, value);
					}else {
						//是上传的
						String fileName = item.getName();
						String path_upload = this.getServletContext().getRealPath("upload");
						InputStream input = item.getInputStream();
						OutputStream output = new FileOutputStream(path_upload+"/"+fileName);
						IOUtils.copy(input, output);
						input.close();
						output.close();
						item.delete();
						map.put("pimage", "upload/"+fileName);
					}
				}
			}
			BeanUtils.populate(product, map);
			//手动封装信息
			product.setPid(CommonsUtils.getUUID());
			product.setPdate(new Date());
			product.setPflag(0);
			Category category = new Category();
			String cid = (String) map.get("cid");
			category.setCid(cid);
			product.setCategory(category);
			//调用service层
			AdminService service = new AdminService();
			service.addProduct(product);
			//跳转到首页
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}