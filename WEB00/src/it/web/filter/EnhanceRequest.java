package it.web.filter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EnhanceRequest extends HttpServletRequestWrapper{
	HttpServletRequest request = null;
	public EnhanceRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	@Override
	public String getParameter(String name) {
		String name_no = request.getParameter(name);
		String name_y = null;
		if(name_no!=null) {
			try {
				//��Ϊ��ǰpost���ύû�н�����룬���Ա������󲻻����
				//������������ȡ�ˣ����Կ��Է���ʹ�á�
				name_y = new String(name_no.getBytes("iso8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
		}
		return name_y;
		
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		//parameterMap = new HashMap(request.getParameterMap());
		Map<String, String[]> parameterMap = new HashMap<String, String[]>(request.getParameterMap());
		//Map<String, String[]> parameterMap = null;
		for(String str:parameterMap.keySet()) {
			String[] value = parameterMap.get(str);
			//String[] value2 = new String[value.length];
			for(int i=0;i<value.length;i++) {
				try {
					value[i] = new String(value[i].getBytes("iso8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			parameterMap.put(str, value);
		}
		
		
		return parameterMap;
		
	}
	
}
