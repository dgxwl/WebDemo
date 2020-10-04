package cn.abc.def.component.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * spring自带的定时器@Scheduled. 需要放在bean中
 * 配置步骤参考 https://blog.csdn.net/sd4000784/article/details/7745947
 * @author Administrator
 *
 */
@Component
public class ScheduleTask {
	
	/**
	 * 在方法上加@@Scheduled注解即可定时执行; 该方法返回值应为void
	 * cron的表达式: 秒 分 时 日 月 周.(年加上为什么报错)
	 * 参考https://www.cnblogs.com/junrong624/p/4239517.html
	 * cron生成器: https://www.pppet.net/
	 */
	@Scheduled(cron = "0,20,40 * * * * ?")
	public void myScheduleTask() {
//		System.out.println("test scheduled");  //一直输出有点烦...注释掉了
	}
}
