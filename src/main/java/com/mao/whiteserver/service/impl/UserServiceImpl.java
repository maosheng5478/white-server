package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.UserDAO;
import com.mao.whiteserver.entity.AdminRole;
import com.mao.whiteserver.entity.User;
import com.mao.whiteserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AdminRoleServiceImpl adminRoleServiceImpl;

    @Override
    public boolean isExist(String username) {
        User user = findByUserName(username);
        return null!=user;
    }

    @Override
    public List<User> list() {
        List<User> users =  userDAO.list();
        List<AdminRole> roles;
        for (User user : users) {
            roles = adminRoleServiceImpl.listRolesByUser(user.getUsername());
            user.setRoles(roles);
        }
        return users;
    }

    @Override
    public User findByUserName(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User get(String username, String password) {
        System.out.println(isExist(username));
        User user = findByUserName(username);
        System.out.println(user);
        return userDAO.getByUsernameAndPassword(username, password);
    }

    @Override
    public void add(User user) {
        userDAO.save(user);
    }

    @Override
    public void addOrUpdate(User user) {
        userDAO.save(user);
    }
}
