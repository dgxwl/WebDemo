package cn.abc.def.util;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * 三方支付相关工具类
 */
public class PayUtil {

	public PayUtil() {}
	
	public static String parseRequestXml(HttpServletRequest request) {
		try {
			StringBuilder xml = new StringBuilder();
			InputStream in = request.getInputStream();
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) > 0) {
				xml.append(new String(buffer, 0, len));
			}
			return xml.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 微信支付回调响应内容
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String wxNotifyResponseXml(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA["
                + return_msg + "]]></return_msg></xml>";
    }
}
