package cn.abc.def.mapper;

import org.springframework.stereotype.Repository;

import cn.abc.def.entity.User;

@Repository
public interface UserMapper {
	
	Integer register(User user);
	
	User findUserByUsername(String username);
}
