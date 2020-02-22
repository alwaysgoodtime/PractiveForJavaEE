package com.wode.crud.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author goodtime
 * @create 2020-01-01 12:19 上午
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/login") || requestURI.contains("/loginServlet") || requestURI.contains("/css/") || requestURI.contains("/fonts/") || requestURI.contains("/js/") || requestURI.contains("/checkCodeServlet")){
            chain.doFilter(request,resp);
        }
        else{
            HttpSession session = request.getSession();
            if (session.getAttribute("msg") != null){
                chain.doFilter(req, resp);
            }
            else{
                request.setAttribute("msg","请先登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
