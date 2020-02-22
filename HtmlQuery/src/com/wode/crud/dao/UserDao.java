package com.wode.crud.dao;

import com.wode.crud.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author goodtime
 * @create 2019-12-28 4:12 下午
 */
public interface UserDao {
    public abstract List<User> findAll();
    public abstract User find(int id);
    public abstract void update(User user);
    public abstract void add(User user);
    public abstract void delete(int id);
    public abstract User findUsernameAndPassword(String username,String password);
    int countAll(Map<String, String[]> map);
    List<User> multiquery(int currentPage, int rows, Map<String, String[]> map);
}
