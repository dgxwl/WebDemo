package cn.abc.def.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.abc.def.component.RedisDistributedLock;
import cn.abc.def.mapper.TestMapper;

@Service
public class TestService implements ITestService {

	@Resource
    private RedisDistributedLock redisDistributedLock;
	@Resource
	private TestMapper testMapper;

	@Transactional
	@Override
	public String testLock(Integer id, Boolean hasImg) {
		//加锁防止重复提交
        String lockKey = "testlock_" + id;
        if (!redisDistributedLock.setLock(lockKey, RedisDistributedLock.DEFAULTLOCKTIME)) {
            return "正在保存数据,请勿重复提交";
        }
        String requestId = redisDistributedLock.get(lockKey);
		
		try {
			//检查数据是否已存在(当前方法加了事务,未提交前再请求一次会通过这个校验,仍导致数据重复)
			if (testMapper.countId(id) > 0) {
				return "提交失败,该数据已存在";
			}
			
			testMapper.addId(id);
			testMapper.addIdInB(id);
			
			if (hasImg) {  //模拟保存配图(耗时的操作)
				Thread.sleep(2000);
			}
		} catch (Exception e) {
		} finally {
			//释放锁
			redisDistributedLock.releaseLock(lockKey, requestId);
		}
		
		return "提交成功";
	}
	
	@Override
	public String testLock(Integer id, Integer tryTimes) {
		//加锁防止重复提交
		String lockKey = "testtrylock_" + id;
		if (!redisDistributedLock.tryLock(lockKey, tryTimes == null ? 2 : tryTimes)) {
			return "正在保存数据,请勿重复提交";
		}
		String requestId = redisDistributedLock.get(lockKey);

		try {
			if (tryTimes == null) {
				Thread.sleep(20000);  //模拟耗时的操作
			}
		} catch (Exception e) {
		} finally {
			//释放锁
			redisDistributedLock.releaseLock(lockKey, requestId);
		}

		return "提交成功";
	}
}
