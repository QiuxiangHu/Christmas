package com.amily.Christmas.base;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class RequestUtil {
	
	public static String getViewPath(HttpServletRequest request) {
		return getViewPath(request.getServletPath());
	}
	
	/**
	 * 根据url，计算对应的html文件位置
	 * 
	 * @param servletPath
	 *  					
	 * @exception 
	 * 1. servletPath is empty
	 * @exception 
	 * 2. servletPath not begin with "/"
	 * @exception
	 * 3. length of servletPath less than two
	 * 
	 * @Example
	 * /home => home
	 * @Example
	 * /stat/jiaowu/teachplancourse/list => stat/jiaowu/teachplancourse/teachplancourse_list
	 */
	public static String getViewPath(String servletPath) {
		Assert.hasText(servletPath, "servletPath must not be empty");
		Assert.isTrue(servletPath.startsWith("/"), "servletPath must starts with '/'");
		Assert.isTrue(servletPath.length() > 1, "servletPath'length must gt 1");
		
		servletPath = servletPath.replaceFirst("/", "");
		
		if (servletPath.contains("/")) {
			String[] pathComponents = servletPath.split("/");
			String viewPath = "";
			for (int i = 0; i < pathComponents.length - 1; i++) {
				viewPath = viewPath.concat(pathComponents[i]).concat("/");
			}
			viewPath = viewPath.concat(pathComponents[pathComponents.length - 2]).concat("_").concat(pathComponents[pathComponents.length - 1]);
			return viewPath;
		} else {
			return servletPath;
		}
	}
	
	public static String GetString(ServletRequest request, String paramName, String alternativeValue) {
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isNoneBlank(paramValue)) {
			return paramValue;
		} else {
			return alternativeValue;
		}
	}
	
	public static Integer GetInteger(ServletRequest request, String paramName, Integer alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (StringUtils.isNoneBlank(paramValue)) {
			try {
				alternativeValue = Integer.parseInt(paramValue);
			} catch (Exception e) {
				// do nothing.
			}
		}
		return alternativeValue;
	}
	
	public static float getFloat(ServletRequest request, String paramName, float alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (StringUtils.isNoneBlank(paramValue)) {
			try {
				alternativeValue = Float.parseFloat(paramValue);
			} catch (Exception e) {
				// do nothing.
			}
		}
		return alternativeValue;
	}
	
	public static Integer GetPositiveInteger(ServletRequest request, String paramName, Integer alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (StringUtils.isNoneBlank(paramValue)) {
			try {
				int tempValue = Integer.parseInt(paramValue);
				if (tempValue >= 0) {
					alternativeValue = tempValue;
				}
			} catch (Exception e) {
				// do nothing.
			}
		}
		return alternativeValue;
	}
	
	public static Boolean GetBoolean(ServletRequest request, String paramName, Boolean alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (StringUtils.isNoneBlank(paramValue)) {
			try {
				alternativeValue = Boolean.parseBoolean(paramValue);
			} catch (Exception e) {
				// do nothing
			}
		}
		
		return alternativeValue;
	}
}
