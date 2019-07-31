package cn.abc.def.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 当前网页分享到微信,QQ等(使用share.js)
 * 需要外网地址,否则分享失败
 * https://github.com/overtrue/share.js
 */
@Controller
public class ShareController {

	@RequestMapping("/share")
	public String page(String content, Model model) {
		model.addAttribute("content", content);
		return "share";
	}
}
