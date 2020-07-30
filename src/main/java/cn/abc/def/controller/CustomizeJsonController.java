package cn.abc.def.controller;

import cn.abc.def.entity.JsonCusUser;
import cn.abc.def.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * json返回字段处理等
 * @author Administrator
 *
 */
@RestController
public class CustomizeJsonController {

	/**
	 * 演示定制json字段返回的格式, 以date类型返回时间戳为例
	 * @param name
	 * @return
	 */
	@RequestMapping("/json_cus")
	public Object jsonCustomize(String name) {
		JsonCusUser user = new JsonCusUser();
		user.setName(name);
		user.setSignUpDate(new Date());
		return user;
	}
	
	/**
	 * 根据注解给不同的接口设置指定返回的字段.
	 * 没什么用, 只有直接返回实体类才生效, 放进ResponseResult返回会得到一个{}
	 * @return user
	 */
	@RequestMapping("/fromAdmin")
	@JsonView(User.AdminInterface.class)
	public User fromAdmin() {
		User u = new User();
		u.setId(123);
		u.setUsername("mingzi");
		u.setPassword("123456");
		u.setPhone("12345678901");
		return u;
	}
	
	/**
	 * 根据注解给不同的接口设置指定返回的字段
	 * 同上
	 * @return user
	 */
	@RequestMapping("/fromApp")
	@JsonView(User.UserInterface.class)
	public User fromApp() {
		User u = new User();
		u.setId(123);
		u.setUsername("mingzi");
		u.setPassword("123456");
		u.setPhone("12345678901");
		return u;
	}
}
