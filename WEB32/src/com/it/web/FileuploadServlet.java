package com.it.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

public class FileuploadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得信息，
		//解析传过来的数据
		//制造一个磁盘工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//临时文件路径
		String path_temp = this.getServletContext().getRealPath("temp");
		factory.setSizeThreshold(1024*1024);
		factory.setRepository(new File(path_temp));
		//创建一个操作对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置编码表
		upload.setHeaderEncoding("UTF-8");
		//分解传进来的form表单
		boolean multipartContent = upload.isMultipartContent(request);
		
		//判断是否是上传文件，即是否是普通的表单
		if(multipartContent) {
			//是上传文件
			//解析文件
			List<FileItem> fileItem = null;
			try {
				fileItem = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			//判断是否是上传项
			if(fileItem!=null) {
				for(FileItem item : fileItem) {
					boolean formField = item.isFormField();
					if(formField) {
						//普通的
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						System.out.println(name+"::"+value);
					}else {
						String name = item.getName();
						InputStream inputStream = item.getInputStream();
						//读取信息
						String realPath = this.getServletContext().getRealPath("upload");
						OutputStream out = new FileOutputStream(realPath+"/"+name);
						IOUtils.copy(inputStream, out);
						inputStream.close();
						out.close();
						//使用操作文件的对象进行删除临时的文件
						item.delete();
					}
				}
			}
			
		}else {
			//普通形式的获取内容
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}