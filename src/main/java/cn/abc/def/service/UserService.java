package cn.abc.def.service;
import cn.abc.def.entity.User;
import cn.abc.def.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
	
	@Override
	public List<User> getAllPhone() {
		return userMapper.getAllPhone();
	}

	@Override
	public String toString() {
		return "I'm impl no.1";
	}
}
