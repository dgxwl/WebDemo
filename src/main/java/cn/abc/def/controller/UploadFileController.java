package cn.abc.def.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping(value = "/upload")
	@ResponseBody
	public String upload(MultipartFile data, HttpSession session) {
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
}
