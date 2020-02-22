import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**简单查询练习
 * @author goodtime
 * @create 2019-12-25 1:23 下午
 */
public class QueryPractice {


    public static void main(String[] args) {

        Connection con = JDBCutils.getConnection();
        if (con != null){
            System.out.println("连接建立");
        }

        String sql = "select id from user where username = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,"zhangsan");

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                int i = resultSet.getInt(1);
                System.out.println(i);
                int j = resultSet.getInt("id");
                System.out.println(j);
            }
            else{
                System.out.println("没有查询到结果");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
