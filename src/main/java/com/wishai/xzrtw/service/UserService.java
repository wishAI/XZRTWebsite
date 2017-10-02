package com.wishai.xzrtw.service;


import com.wishai.xzrtw.exception.UserNotFound;
import com.wishai.xzrtw.model.User;

public interface UserService {

    String SESSION_KEY_USER = "user";
    String USER_PERMISSION_BOSS = "boss";


    String findNameById(Integer id) throws UserNotFound;

    User findSecureUserByUserKey(User userKey) throws UserNotFound;
}
