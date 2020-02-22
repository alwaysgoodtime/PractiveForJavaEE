package cookie;

import javafx.scene.input.DataFormat;
import sun.text.resources.cldr.af.FormatData_af;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 返回上次访问时间和首次访问提示
 * @author goodtime
 * @create 2019-12-27 4:42 下午
 */
@WebServlet("/cookieServlet")
public class CookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");//注意分号

        Cookie[] cookies = request.getCookies();
        Date date = new Date();
        Boolean flag = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = simpleDateFormat.format(date);
        time = URLEncoder.encode(time, "utf-8");

        if(cookies != null && cookies.length != 0) {
                for (Cookie c : cookies) {
                  if ("lasttime".equals(c.getName())) {
                    flag = true;
                    PrintWriter writ = response.getWriter();
                    writ.write("您上次访问的时间是"+URLDecoder.decode(c.getValue()));
                    c.setValue(time);
//                    c.setMaxAge(60 * 60 * 24);
                    break;
                }
            }
        }

        if(cookies == null || cookies.length == 0 || flag == false) {
            Cookie cookie = new Cookie("lasttime", time);
            response.addCookie(cookie);
            cookie.setMaxAge(60*60);
            PrintWriter writer = response.getWriter();
            writer.write("欢迎您首次访问");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
