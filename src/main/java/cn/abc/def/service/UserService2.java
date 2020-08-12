package cn.abc.def.service;

import cn.abc.def.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService2 implements IUserService {
    @Override
    public Integer register(User user) {
        return null;
    }

    @Override
    public List<User> getAllPhone() {
        return null;
    }

    @Override
    public String toString() {
        return "I'm impl no.2";
    }
}
