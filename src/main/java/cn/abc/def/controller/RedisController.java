package cn.abc.def.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.abc.def.service.ITestService;
import cn.abc.def.util.RedisUtil;

/**
 * 使用RedisUtil工具类操作redis
 * 
 * @author Administrator
 *
 */
@RestController
public class RedisController {
	
	@Resource
	private ITestService testService;

	@RequestMapping("/test_redis/{value}")
	public String testRedis(@PathVariable String value) {
		RedisUtil.set("test", value);
		String test = RedisUtil.get("test");
		RedisUtil.delete("test");
		
		return test;
	}
	
	/**
	 * 模拟上传订单评价
	 * 用户有上传配图时处理比较耗时, 而且处理保存的service方法加了事务(因为保存评价到评价实体表和保存点赞菜品到评价与菜品关系表是原子操作),
	 * 如果前端没有约束用户一直点提交, 即当一次请求在处理途中还没响应时又请求一次(此时事务没提交,一般的查询检查重复数据没用), 会导致数据重复提交, 加锁解决.
	 * @param id test_id
	 * @param hasImg 模拟有上传配图
	 * @return result
	 */
	@RequestMapping("/test_lock")
	public String testLock(Integer id, Boolean hasImg) {
		try {
			return testService.testLock(id, hasImg);
		} catch (Exception e) {
			return "服务器异常";
		}
	}
}
