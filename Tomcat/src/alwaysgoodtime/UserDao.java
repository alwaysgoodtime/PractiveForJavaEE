package alwaysgoodtime;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author goodtime
 * @create 2019-12-27 9:18 上午
 */
public class UserDao {

    private static String username;

    private static String password;


    public static boolean find(User user) {

        DataSource dataSource = JDBCutils.getDataSource();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "select id from user where username = ? and password = ?";

        List<User> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        //虽然有了新的User对象，但其中只有user值，其他值都为null


        for(User a: query)
            {
                System.out.println(a);
        }

        return !query.isEmpty();
    }
}