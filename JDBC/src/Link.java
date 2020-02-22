import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**测试用properties文件的一般连接方式
 * @author goodtime
 * @create 2019-12-25 1:23 下午
 */
public class Link{


    public static void main(String[] args) throws Exception{

        Properties wode = new Properties();
        InputStream in = Link.class.getClassLoader().getResourceAsStream("JDBC.properties");
        wode.load(in);
        String driver = wode.getProperty("driver");
        String user = wode.getProperty("user");
        String password = wode.getProperty("password");
        String url = wode.getProperty("url");

        Class.forName(driver);


        Connection haha = DriverManager.getConnection(url, user, password);

        System.out.println(haha);


    }



}
