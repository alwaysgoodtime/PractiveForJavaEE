package com.wode.crud.web;

import com.wode.crud.domain.PageBean;
import com.wode.crud.service.UserService;
import com.wode.crud.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.util.Map;

/**
 * 用于控制下面的分页栏
 * @author goodtime
 * @create 2019-12-30 3:16 下午
 */
@WebServlet("/pageControlServlet")
public class PageControlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        Map<String, String[]> map = (Map<String, String[]>)request.getParameterMap();
        if(currentPage == null || "" == currentPage){
            currentPage = "1";
        }
        if(rows == null || "" == rows){
            rows = "2";
        }
        UserService userService = new UserServiceImpl();
        PageBean pb = userService.jump(currentPage,rows,map);
        request.setAttribute("pb",pb);
        request.setAttribute("map",map);
        request.getRequestDispatcher("list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
