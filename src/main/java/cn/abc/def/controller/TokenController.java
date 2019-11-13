package cn.abc.def.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.abc.def.util.TokenUtil;

/**
 * token机制
 * @author Administrator
 *
 */
@RestController
public class TokenController {

	/**
	 * 假设登录时获得token
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(String username, String password) {
		//Verify username and password
		//...
		
		String token = TokenUtil.createToken(username);
		return "{\"token\": \"" + token + "\"}";
	}
	
	/**
	 * 登录后, 后续调用接口都要带上token参数
	 * @param request
	 * @return
	 */
	@RequestMapping("/check_token")
	@ResponseBody
	public String checkToken(HttpServletRequest request) {
		return TokenUtil.checkToken(request) + "";
	}
}
