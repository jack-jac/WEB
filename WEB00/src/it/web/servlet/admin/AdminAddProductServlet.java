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
		
		//����һ��map���Ͻ�������
		
		
		//�ȴ���һ�����̹�����
		String path_temp = this.getServletContext().getRealPath("temp");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//������ʱ��λ�ã����С
		factory.setSizeThreshold(1024*1024);
		factory.setRepository(new File(path_temp));
		//��÷����ϴ��ļ��Ķ���
		ServletFileUpload upload = new ServletFileUpload();
		upload.setHeaderEncoding("UTF-8");
		boolean multipartContent = upload.isMultipartContent(request);
		if(multipartContent) {
			//���ϴ��ļ�
			//����request
			List<FileItem> parseRequest = null;
			try {
				parseRequest = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			if(parseRequest!=null) {
				for(FileItem item : parseRequest) {
					//�ж��Ƿ����ϴ���
					boolean formField = item.isFormField();
					if(formField) {
						//����ͨ����
						
					}else {
						//�ϴ���
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
			//�����ϴ��ļ�
			//��ȡ��Ϣ����װ�ɶ��󣬴���service��
			Product product = null;
			try {
				product = BeanUtil.fillBean(request, Product.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//��������ر�����ԡ�
			//1,id
			product.setPid(UUID.randomUUID().toString());
			//2,img
			//products/1/c_0001.jpg
			product.setPimage("products/hao/test.jpg");
			//3,�ϼܣ����¼ܵ���Ϣ,һ�����ϼ�.
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String pdate = format.format(new Date());
			product.setPdate(pdate);
			//�����¼ܱ�־
			product.setPflag(0);
			//����service��
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