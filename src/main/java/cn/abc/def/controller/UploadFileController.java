package cn.abc.def.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * 需要在spring xml文件中加入bean, 并配置允许上传的最大字节数
 * <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="maxUploadSize" value="104857600" />
	</bean>
	并引入依赖
	<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>${version}</version>
		</dependency>
 * @author Administrator
 *
 */
@Controller
public class UploadFileController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(MultipartFile data, String otherParam, String[] ids, HttpSession session) {
		System.out.println("跟文件一起接收到的普通参数: " + otherParam);
		System.out.println("跟文件一起接收到的数组参数: " + Arrays.toString(ids));
		String realPath = session.getServletContext().getRealPath("/upload");
		try (OutputStream out = new FileOutputStream(realPath + "/" + data.getOriginalFilename());
				BufferedOutputStream bos = new BufferedOutputStream(out)) {
			InputStream in = data.getInputStream();
			byte[] buf = new byte[4096];
			int len = -1;
			while ((len = in.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "上传成功";
	}
	
	@RequestMapping("/testupload")
	public String upload() {
		return "testupload";
	}
	
	@RequestMapping("/testupload2")
	public String upload2() {
		return "testupload2";
	}
	
	@RequestMapping("/batchupload")
	public String batchupload() {
		return "batchupload";
	}
	
	/**
	 * 批量上传
	 * 当没有传任何文件时,公司的项目会报NoSuchMethodException, 
	 * 需要加注解RequestParam(required = false)解决, 这个项目却不会, 搞不懂...
	 * @param img 数组
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/batch_upload")
	@ResponseBody
	public String batchUpload(@RequestParam(required = false) MultipartFile[] img, HttpSession session) {
		String realPath = session.getServletContext().getRealPath("/upload");
		
		if (img != null && img.length > 0) {
			for (MultipartFile data : img) {
				try (OutputStream out = new FileOutputStream(realPath + "/" + data.getOriginalFilename());
						BufferedOutputStream bos = new BufferedOutputStream(out)) {
					InputStream in = data.getInputStream();
					byte[] buf = new byte[4096];
					int len = -1;
					while ((len = in.read(buf)) != -1) {
						bos.write(buf, 0, len);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return "上传成功";
		} else {
			return "没有上传文件";
		}
	}
}
