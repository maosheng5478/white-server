package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.User;

import java.util.List;

public interface UserService {

    List<User> list();

    boolean isExist(String username) ;

    User findByUserName(String username);

    User get(String username, String password);

    void add(User user) ;

    void addOrUpdate(User user);

}
