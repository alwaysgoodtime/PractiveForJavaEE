package com.wode.crud.web;

import com.wode.crud.domain.User;
import com.wode.crud.service.UserService;
import com.wode.crud.service.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author goodtime
 * @create 2019-12-29 9:03 上午
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String verifycode = (String) request.getParameter("verifycode");
        HttpSession session = request.getSession();
        String checkCode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
//        try {
//            if(!checkCode.equalsIgnoreCase(verifycode)){
//                request.setAttribute("msg","验证码错误");
//                request.getRequestDispatcher("/login.jsp").forward(request,response);
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.getRequestDispatcher("/login.jsp").forward(request,response);
//        }
            if(!verifycode.equalsIgnoreCase(checkCode)){
                request.setAttribute("msg","验证码错误");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
                return;
            }

        Map<String, String[]> maps = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,maps);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService userService = new UserServiceImpl();

        User loginuser = userService.login(user);

        if(loginuser == null){

            request.setAttribute("msg","账号密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);

        }
        else{
            session.setAttribute("msg",loginuser);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
