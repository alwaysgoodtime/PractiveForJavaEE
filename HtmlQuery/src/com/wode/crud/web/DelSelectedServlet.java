package com.wode.crud.web;

import com.wode.crud.service.UserService;
import com.wode.crud.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author goodtime
 * @create 2019-12-30 1:14 下午
 */
@WebServlet("/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String[] uids = request.getParameterValues("uid");
        UserService userService = new UserServiceImpl();
        userService.delSelectedService(uids);
        response.sendRedirect(request.getContextPath()+"/pageControlServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
