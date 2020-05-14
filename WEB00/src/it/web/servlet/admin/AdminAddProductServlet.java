package it.web.servlet.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import it.domain.Product;
import it.service.ProductService;
import it.service.imp.ProductServiceImp;
import it.utils.BeanUtil;

public class AdminAddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//创建一个map集合接收数据
		
		
		//先创建一个磁盘管理工厂
		String path_temp = this.getServletContext().getRealPath("temp");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//设置临时的位置，与大小
		factory.setSizeThreshold(1024*1024);
		factory.setRepository(new File(path_temp));
		//获得服务上传文件的对象
		ServletFileUpload upload = new ServletFileUpload();
		upload.setHeaderEncoding("UTF-8");
		boolean multipartContent = upload.isMultipartContent(request);
		if(multipartContent) {
			//是上传文件
			//解析request
			List<FileItem> parseRequest = null;
			try {
				parseRequest = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			if(parseRequest!=null) {
				for(FileItem item : parseRequest) {
					//判断是否是上传项
					boolean formField = item.isFormField();
					if(formField) {
						//是普通的项
						
					}else {
						//上传项
						String file_name = item.getName();
						InputStream input = item.getInputStream();
						String path_upload = this.getServletContext().getRealPath("upload");
						OutputStream output = new FileOutputStream(path_upload+"/"+file_name);
						IOUtils.copy(input, output);
						output.close();
						input.close();
						item.delete();
					
					}
				}
			}
		}else {
			//不是上传文件
			//获取信息，封装成对象，传给service层
			Product product = null;
			try {
				product = BeanUtil.fillBean(request, Product.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//单独填充特别的属性。
			//1,id
			product.setPid(UUID.randomUUID().toString());
			//2,img
			//products/1/c_0001.jpg
			product.setPimage("products/hao/test.jpg");
			//3,上架，与下架的信息,一般是上架.
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String pdate = format.format(new Date());
			product.setPdate(pdate);
			//设置下架标志
			product.setPflag(0);
			//传给service层
			ProductService service = new ProductServiceImp();
			try {
				service.addProduct(product);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath()+"/adminProductListServlet");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}