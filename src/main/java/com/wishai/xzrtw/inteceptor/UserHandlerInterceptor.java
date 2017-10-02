package com.wishai.xzrtw.inteceptor;

import com.wishai.xzrtw.model.User;
import com.wishai.xzrtw.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class UserHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        // get the session
        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute(UserService.SESSION_KEY_USER) != null) {
            if(Objects.equals(((User) (httpSession.getAttribute(UserService.SESSION_KEY_USER))).getPermission(), UserService.USER_PERMISSION_BOSS)) {
                return true;
            }
        }

        // if the user is not in boss permission, refuse request
        String result = "You don't have the permission to do that.";
        response.getOutputStream().write(result.getBytes());
        response.setStatus(HttpStatus.OK.value());
        return false;
    }
}
