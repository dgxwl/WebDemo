package cn.adc.def.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import mybatis.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 输出xml和解析xml的工具类
 * 
 * @ClassName:XmlUtil
 * @author: chenyoulong Email: chen.youlong@payeco.com
 * @date :2012-9-29 上午9:51:28
 * @Description:TODO
 */
public class XmlUtil {

	private XmlUtil(){}

	private static volatile Map<Class<?>, XStream> cache = new ConcurrentHashMap<>();

	/*
	 * The XStream instance is thread-safe. That is, once the XStream instance has been created and configured,
	 * it may be shared across multiple threads allowing objects to be serialized/deserialized concurrently.
	 * https://www.cnblogs.com/qhj348770376/p/9346776.html
	 */
	private static XStream getXStream(Class<?> cls) {
		XStream xStream = cache.get(cls);
		if (xStream == null) {
			synchronized (cls) {
				xStream = cache.get(cls);
				if (xStream == null) {
					xStream = new XStream(new DomDriver("utf-8"));
					XStream.setupDefaultSecurity(xStream);
					xStream.allowTypes(new Class[]{cls});
					xStream.processAnnotations(cls);xStream.ignoreUnknownElements();
					cache.put(cls, xStream);
				}
			}
		}
		return xStream;
	}

	/**
	 * java 转换成xml
	 * @param obj 对象实例
	 * @return String xml字符串
	 */
	public static String toXml(Object obj) {
		XStream xstream = getXStream(obj.getClass());
		return xstream.toXML(obj);
	}

	/**
	 * 将传入xml文本转换成Java对象
	 * @param xmlStr
	 * @param cls xml对应的class类
	 * @return T xml对应的class类的实例对象
	 * 调用的方法实例：PersonBean person = XmlUtil.toBean(xmlStr, PersonBean.class);
	 */
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		XStream xstream = getXStream(cls);
		@SuppressWarnings("unchecked")
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

	/**
	 * 写到xml文件中去
	 * @param obj 对象
	 * @param absPath 绝对路径
	 * @param fileName 文件名
	 * @return boolean
	 * @throws IOException ioe
	 */

	public static boolean writeToFile(Object obj, String absPath, String fileName) throws IOException {
		String strXml = toXml(obj);
		String filePath = absPath + fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		try (OutputStream out = new FileOutputStream(file)) {
			out.write(strXml.getBytes());
			out.flush();
			return true;
		}
	}

}
