package test;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.abc.def.entity.User;
import cn.abc.def.mapper.UserMapper;

public class UserTest {
	@Test
	public void testRegister() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-dao.xml");
		
		UserMapper userMapper = ac.getBean("userMapper", UserMapper.class);
		userMapper.register(new User(null, "test", "1234", "10086"));
		
		ac.close();
	}
}
