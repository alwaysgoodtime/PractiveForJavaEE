import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**测试Druid连接池
 * @author goodtime
 * @create 2019-12-25 1:23 下午
 */
public class LinkPlus {


    public static void main(String[] args) throws Exception{
        Properties pro = new Properties();
        pro.load(ClassLoader.getSystemClassLoader().getResourceAsStream("ForDruid.properties"));
        //注意，德鲁伊的proprties文件要写username，而非user
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pro);
        Connection conn = dataSource.getConnection();
        System.out.println(conn);

    }



}
