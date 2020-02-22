package com.wode.crud.dao;

import com.wode.crud.domain.User;
import com.wode.crud.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author goodtime
 * @create 2019-12-28 4:12 下午
 */
public class UserDaoImpl implements UserDao{

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        String sql = "select * from loguser";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;

    }

    @Override
    public User find(int id) {
        String sql = "select * from loguser where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),id);
        return user;
    }

    @Override
    public void update(User user) {
        String sql = "update loguser set gender = ?, age = ?,home = ?,qq = ?, mail = ?, name = ? where id = ?";
        jdbcTemplate.update(sql,user.getGender(),user.getAge(),user.getHome(),user.getQq(),user.getMail(),user.getName(),user.getId());
    }

    @Override
    public void add(User user) {
        String sql = "insert into loguser values (null,null,?,?,?,?,?,?,null)";
        jdbcTemplate.update(sql,user.getGender(),user.getAge(),user.getHome(),user.getQq(),user.getMail(),user.getName());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from loguser where id = ?";
        jdbcTemplate.update(sql,id);
    }


    @Override
    public User findUsernameAndPassword(String username,String password) {

        try {
            String sql = "select * from loguser where username = ? and password = ?";
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public int countAll(Map<String, String[]> map) {
        String sql = "select count(id) from loguser where 1 = 1";
        String[] name = map.get("name");
        String[] home = map.get("home");
        String[] mail = map.get("mail");
        if(name != null && name.length != 0){
            sql = sql + " and name like '%"+name[0]+"%'";
        }
        if(home != null && home.length != 0){
            sql = sql + " and home like '%"+home[0]+"%'";
        }
        if(mail != null && mail.length != 0){
            sql = sql + " and mail like '%"+mail[0]+"%'";
        }
        int aLong = jdbcTemplate.queryForObject(sql, Integer.class);
        return aLong;
    }

    @Override
    public List<User> multiquery(int currentPage, int rows, Map<String, String[]> map) {
        String sql = "select * from loguser where 1 = 1";
        String[] name = map.get("name");
        String[] home = map.get("home");
        String[] mail = map.get("mail");
//        System.out.println(home[0]);如果不写home查询的值，默认返回的数组长度为0，
//        所以这样会报空指针异常，但是jsp中不会报错，应该是jsp中如果没有值，就默认是0
        if(name != null && name.length != 0){
            sql = sql + " and name like '%"+name[0]+"%'";
        }
        if(home != null && home.length != 0){
            sql = sql + " and home like '%"+home[0]+"%'";
        }
        if(mail != null && mail.length != 0){
            sql = sql + " and mail like '%"+mail[0]+"%'";
        }
        sql =  sql + " limit ?,?";

        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), (currentPage - 1) * rows, rows);

        return users;
    }
}
