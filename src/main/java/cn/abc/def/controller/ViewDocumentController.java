package cn.abc.def.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 在线预览各种文档(doc, xls, ppt, pdf)
 * @author Administrator
 *
 */
@Controller
public class ViewDocumentController {

	/**
	 * 看word, excel, ppt
	 * @param modelMap
	 * @param url 需要传入带域名/IP地址+端口号的完整url, 需要借助微软提供的服务
	 * @return
	 */
	@RequestMapping("/viewOffice")
	public String viewOffice(ModelMap modelMap, String url) {
		modelMap.addAttribute("url", url);
		return "viewOffice";
	}
	
	/**
	 * 看pdf
	 * @param modelMap
	 * @param url url传入项目内绝对路径即可
	 * @return
	 */
	@RequestMapping("/viewPdf")
	public String viewPdf(ModelMap modelMap, String url) {
		modelMap.addAttribute("url", url);
		return "viewPdf";
	}
}
