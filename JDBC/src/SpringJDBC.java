import com.alibaba.druid.sql.ast.statement.SQLExprHint;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**JDBC工具测试
 * @author goodtime
 * @create 2019-12-25 1:23 下午
 */
public class SpringJDBC {


    public static void main(String[] args) {

        DataSource ds = JDBCutils.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        String sql = "update user set username = 'zhangmazi' where id = ?";
        int count = jt.update(sql,1);

        String sqld = "select * from user where id = ?";
        Map<String, Object> map = jt.queryForMap(sqld, 1);

        System.out.println(map);//只能返回一条数据，不能0条，也不能多条

        String sqlt = "select * from user";
        List<Map<String, Object>> maps = jt.queryForList(sqlt);

//        for(Map<String, Object> a: maps){
//            System.out.println(a);
//        } //遍历list1

//        Iterator iterator = maps.iterator();
//        while(iterator.hasNext()){
//            Map<String,Object> next = (Map<String,Object>)iterator.next();
//            System.out.println(next);
//        }//遍历2


        maps.forEach(System.out::println); //遍历3

        Map<String, Object> string = maps.get(1);
        Set i = string.keySet();//遍历map
        Iterator iter = i.iterator();
        while(iter.hasNext()){
            Object next = iter.next();
            Object j = string.get(next);
            System.out.println(next+"="+j);
        }


    }
}


