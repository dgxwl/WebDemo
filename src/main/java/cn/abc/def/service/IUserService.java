package cn.abc.def.service;
import java.util.List;

import cn.abc.def.entity.User;

public interface IUserService {
	Integer register(User user);
	
	List<User> getAllPhone();
}
