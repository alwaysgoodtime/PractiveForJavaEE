package alwaysgoodtime;

import java.util.List;

/**
 * @author goodtime
 * @create 2019-12-27 11:04 上午
 */
public class UserDaoTest {

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("lisi");
        user.setPassword("sd");
        Boolean list = UserDao.find(user);
        System.out.println(list);
    }

}
