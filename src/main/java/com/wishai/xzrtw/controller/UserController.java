package com.wishai.xzrtw.controller;

import com.wishai.xzrtw.exception.UserNotFound;
import com.wishai.xzrtw.model.User;
import com.wishai.xzrtw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    private final HttpSession httpSession;

    @Autowired
    public UserController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "LoginPage";
    }

    @GetMapping(value = "/sessionInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public User sessionInfo() {
        return (User) httpSession.getAttribute(UserService.SESSION_KEY_USER);
    }

    @PostMapping(
            value = "doLogin",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public String doLogin(@RequestBody User userKey) {
//        return userKey;
        try {
            // get the user without the password for safety
            User secureUser = userService.findSecureUserByUserKey(userKey);
            httpSession.setAttribute(UserService.SESSION_KEY_USER, secureUser);
            return "success";
        } catch (UserNotFound userNotFound) {
            return userNotFound.getErrorInfo();
        }
    }

    @PostMapping("/doLogout")
    @ResponseBody
    public String doLogout(SessionStatus status) {
//        userService.doLogout(status);
        status.setComplete();
        return "success";
    }

}
