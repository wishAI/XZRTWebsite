package com.wishai.xzrtw.service;


import com.wishai.xzrtw.exception.UserNotFound;
import com.wishai.xzrtw.model.User;
import com.wishai.xzrtw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUser(Integer id) throws UserNotFound {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFound();
        }
        return user;
    }

    private User findSecureUserByPair(User userKey) throws UserNotFound {
        Object[] cache = (Object[]) userRepository.findIdAndNameAndPermissionByNameAndPassword(userKey.getName(), userKey.getPassword())[0];
        if (cache == null) {
            throw new UserNotFound();
        }
        User user = new User();
        user.setId((Integer) cache[0]);
        user.setName((String) cache[1]);
        user.setPermission((String) cache[2]);
        return user;
    }

    @Override
    public String findNameById(Integer id) throws UserNotFound {
        String userName = userRepository.findNameById(id);
        if (userName == null) {
            throw new UserNotFound();
        }
        return userName;
    }

    @Override
    public User findSecureUserByUserKey(User userKey) throws UserNotFound {
        // filter the invalid info first
//        String name = userKey.getName();
//        if (name == null || name.equals("") || !(name.matches("^[a-zA-Z0-9]*$")) || name.length() > 15) {
//            throw new UserNotFound("用户名必须为1-15个英文字母和数字组成");
//        }
//        String password = userKey.getPassword();
//        if (password == null || password.equals("") || !(password.matches("^[a-zA-Z0-9]*$")) || password.length() > 20 || password.length() < 5) {
//            throw new UserNotFound("密码必须为5-20个英文字母和数字组成");
//        }
        // find the user in database
        try {
            return findSecureUserByPair(userKey);
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            throw new UserNotFound("用户名或密码错误");
        }
    }
}
