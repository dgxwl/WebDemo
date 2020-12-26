package test;

import cn.abc.def.service.UserService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "transactionManager")
@Rollback(value = true)
@ContextConfiguration("classpath*:spring-service.xml")
@ActiveProfiles(profiles="dev")
public class ServiceBeanTest {
    
    @Resource
    private UserService userService;

    @Test
    public void test() {
        System.out.println(JSON.toJSONString(userService.getAllPhone()));
    }
}
