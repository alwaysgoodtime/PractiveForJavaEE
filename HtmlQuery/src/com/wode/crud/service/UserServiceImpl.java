package com.wode.crud.service;

import com.wode.crud.dao.UserDao;
import com.wode.crud.dao.UserDaoImpl;
import com.wode.crud.domain.PageBean;
import com.wode.crud.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author goodtime
 * @create 2019-12-28 4:16 下午
 */
public class UserServiceImpl implements UserService {

    private UserDao Dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {

        List<User> users = Dao.findAll();

        return  users;
    }

    @Override
    public User find(String id) {
        User user = Dao.find(Integer.parseInt(id));
        return user;
    }

    @Override
    public void update(User user) {
        Dao.update(user);
    }

    @Override
    public User login(User user) {

        return Dao.findUsernameAndPassword(user.getUsername(),user.getPassword());

    }

    @Override
    public void addUserService(User user) {
        Dao.add(user);
    }

    @Override
    public void deleteService(String id) {
        Dao.delete(Integer.parseInt(id));
    }

    @Override
    public void delSelectedService(String[] uids) {
        if(uids != null && uids.length != 0) {
            for (int i = 0; i < uids.length; i++) {
                Dao.delete(Integer.parseInt(uids[i]));
            }
        }
    }

    @Override
    public PageBean<User> jump(String _currentPage, String _rows, Map<String, String[]> map) {
        PageBean<User> pb = new PageBean<User>();
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        int allRows = Dao.countAll(map);
        int pages;
        if (allRows != 0) {
            pages = allRows % rows == 0 ? allRows / rows : (allRows / rows) + 1;
        }
        else{
            pages = 1;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > pages) {
            currentPage = pages;
        }
        pb.setList(Dao.multiquery(currentPage, rows, map));
        pb.setRows(rows);
        pb.setPages(pages);
        pb.setCurrentPage(currentPage);
        pb.setAllRows(allRows);
        return pb;
    }
}

