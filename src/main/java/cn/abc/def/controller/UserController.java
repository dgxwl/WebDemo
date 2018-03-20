package cn.abc.def.controller;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.abc.def.entity.User;
import cn.abc.def.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping("/toRegister.do")
	public String toRegister() {
		return "register";
	}
	
	@RequestMapping("/toLogin.do")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping(value="/register.do", method=RequestMethod.POST)
	public String register(User user, ModelMap modelMap) {
		try {
			userService.register(user);
			modelMap.addAttribute("message", "注册成功!");
			return "message";
		} catch (RuntimeException e) {
			modelMap.addAttribute("message", e.getMessage());
			return "message";
		}
	}
}
