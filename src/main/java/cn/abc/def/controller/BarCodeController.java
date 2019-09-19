package cn.abc.def.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 演示生成条形码; 通过前端引入JsBarCode.js实现
 * 参考 https://github.com/lindell/JsBarcode
 * @author Administrator
 *
 */
@Controller
public class BarCodeController {

	@RequestMapping("/barcode")
	public String barcode(ModelMap modelMap, String code) {
		if (StringUtils.isEmpty(code)) {
			code = "hello world";
		}
		modelMap.addAttribute("code", code);
		return "barcode";
	}
	
	@RequestMapping("/vue")
	@ResponseBody
	public String vue() {
		return "hello";
	}
}
