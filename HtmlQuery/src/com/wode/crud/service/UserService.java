package com.wode.crud.service;

import com.wode.crud.domain.PageBean;
import com.wode.crud.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author goodtime
 * @create 2019-12-28 4:16 下午
 */
public interface UserService {
    public abstract List<User> findAll();
    public abstract User find(String id);
    public abstract void update(User user);
    public abstract User login(User user);
    public abstract void addUserService(User user);
    public abstract void deleteService(String id);
    public abstract void delSelectedService(String[] uids);
    public abstract PageBean<User> jump(String currentPage, String rows, Map<String, String[]> map);
}
