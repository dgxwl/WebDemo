package cn.abc.def.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	private WebUtil() {}
	
	/**
	 * 判断是否为ajax请求
	 * @param request
	 * @return isAjaxReuqest
	 */
	public static boolean isAjaxReuqest(HttpServletRequest request){
		String ajaxHeader = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(ajaxHeader);
	}
}
