package alwaysgoodtime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author goodtime
 * @create 2019-12-26 11:45 下午
 */
@WebServlet("/logServlet")
public class logServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("denglu");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User logUser = new User();
        logUser.setUsername(username);
        logUser.setPassword(password);
        Boolean result = UserDao.find(logUser);
        System.out.println(username);
        System.out.println(password);
        if(result){
            request.setAttribute("user",logUser);
            request.getRequestDispatcher("/SuccessServlet").forward(request,response);
        }
        else {
            request.getRequestDispatcher("/FailServlet").forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
