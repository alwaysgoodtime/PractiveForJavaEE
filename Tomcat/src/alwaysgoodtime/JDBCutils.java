package alwaysgoodtime;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**封装获取连接和关闭资源的方法
 * @author goodtime
 * @create 2019-12-25 2:51 下午
 */
public class JDBCutils{
    /**
     * 调此静态方法获取连接
     * @return返回一个Druid中的连接
     *
     */
    private static DataSource source;

    static{
        try {
            Properties pro = new Properties();
            pro.load(JDBCutils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //这里不要用ClassLoader.getSystemClassLoader.getResourceAsStream()这个方法本地测试时候可以用，部署到tomcat上就会报错，输入流无法创建。
            source = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection  getConnection(){
        Connection con = null;
        try {
            con = source.getConnection();
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回Druid创建的连接池
     * @return 连接池
     */

    public static DataSource getDataSource(){
        return source;
    }

    /**
     * 使用此方法关闭连接
     */
    public static void close(){

    }

}
