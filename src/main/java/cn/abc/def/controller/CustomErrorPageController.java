package cn.abc.def.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorPageController {

	@RequestMapping("/err/{code}")
	public String err(@PathVariable String code, ModelMap modelMap) {
		modelMap.addAttribute("code", code);
		return "error";
	}
}
