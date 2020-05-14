package it.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {
	public static <T> T fillBean(HttpServletRequest request,Class<T> clazz) throws Exception {
		T bean = clazz.newInstance();
		Map<String, String[]> properties = request.getParameterMap();
		BeanUtils.populate(bean, properties);
		return bean;
	}
}
