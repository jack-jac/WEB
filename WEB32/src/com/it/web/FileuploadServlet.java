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
		//�����Ϣ��
		//����������������
		//����һ�����̹���
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//��ʱ�ļ�·��
		String path_temp = this.getServletContext().getRealPath("temp");
		factory.setSizeThreshold(1024*1024);
		factory.setRepository(new File(path_temp));
		//����һ����������
		ServletFileUpload upload = new ServletFileUpload(factory);
		//���ñ����
		upload.setHeaderEncoding("UTF-8");
		//�ֽ⴫������form��
		boolean multipartContent = upload.isMultipartContent(request);
		
		//�ж��Ƿ����ϴ��ļ������Ƿ�����ͨ�ı�
		if(multipartContent) {
			//���ϴ��ļ�
			//�����ļ�
			List<FileItem> fileItem = null;
			try {
				fileItem = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			//�ж��Ƿ����ϴ���
			if(fileItem!=null) {
				for(FileItem item : fileItem) {
					boolean formField = item.isFormField();
					if(formField) {
						//��ͨ��
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						System.out.println(name+"::"+value);
					}else {
						String name = item.getName();
						InputStream inputStream = item.getInputStream();
						//��ȡ��Ϣ
						String realPath = this.getServletContext().getRealPath("upload");
						OutputStream out = new FileOutputStream(realPath+"/"+name);
						IOUtils.copy(inputStream, out);
						inputStream.close();
						out.close();
						//ʹ�ò����ļ��Ķ������ɾ����ʱ���ļ�
						item.delete();
					}
				}
			}
			
		}else {
			//��ͨ��ʽ�Ļ�ȡ����
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}