package com.wode.crud.test;

import com.wode.crud.domain.User;
import com.wode.crud.service.UserServiceImpl;

/**
 * @author goodtime
 * @create 2019-12-29 12:25 下午
 */
public class LoginTest {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        User user = new User();
        user.setUsername("lisi");
        user.setPassword("123");
        User login = userService.login(user);
        System.out.println(login);

    }
}
