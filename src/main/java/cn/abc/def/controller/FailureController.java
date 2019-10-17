package cn.abc.def.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.abc.def.domain.ResponseResult;
import cn.abc.def.entity.User;
import cn.abc.def.service.UserService;

/**
 * 失败的教训
 */
@RestController
@RequestMapping("/fail")
public class FailureController {
	
	@Resource
	private UserService userService;

	/**
	 * 使用实体类接收mybatis的结果集,如果查询的字段刚好是null,mybatis
	 * 不会跳过此条记录而是直接放个null进结果集列表里,一不小心空指针...
	 * 解决办法: SQL中筛选出不为null的记录,或者对null做判断
	 * @return
	 */
	@RequestMapping("/null_in_result_list")
	public ResponseResult getNullInResultList() {
		List<User> list = userService.getAllPhone();  //list里面可能有null元素!
		System.out.println(list);
//		for (User user : list) {
//			System.out.println(user);  npe
//		}
		
		ResponseResult rr = new ResponseResult();
		rr.setData(list);
		
		return rr;
	}
}
