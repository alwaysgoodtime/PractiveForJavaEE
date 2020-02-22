package com.wode.crud.web;

import com.wode.crud.domain.User;
import com.wode.crud.service.UserService;
import com.wode.crud.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

/**
 * @author goodtime
 * @create 2019-12-28 3:57 下午
 */
@WebServlet("/listServlet")
public class ListServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //查询所有用户
        UserService userService = new UserServiceImpl();
        List<User> users = userService.findAll();
        //转发间共享结果
        request.setAttribute("users",users);
        //转发到list.jsp页面
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }
}
