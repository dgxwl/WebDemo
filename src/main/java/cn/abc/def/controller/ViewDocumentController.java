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

	@RequestMapping("/viewOffice")
	public String viewOffice(ModelMap modelMap, String url) {
		return "viewOffice";
	}
}
