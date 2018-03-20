package cn.abc.def.service;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.abc.def.entity.User;
import cn.abc.def.mapper.UserMapper;

@Service
public class UserService implements IUserService {

	@Resource
	private UserMapper userMapper;
	
	public Integer register(User user) {
		User u = userMapper.findUserByUsername(user.getUsername());
		if (u == null) {
			return userMapper.register(user);
		} else {
			throw new RuntimeException("注册失败：该用户已存在");
		}
	}
}
