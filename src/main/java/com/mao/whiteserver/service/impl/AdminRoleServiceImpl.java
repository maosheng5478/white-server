package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.AdminRoleDAO;
import com.mao.whiteserver.entity.AdminPermission;
import com.mao.whiteserver.entity.AdminRole;
import com.mao.whiteserver.entity.AdminUserRole;
import com.mao.whiteserver.service.AdminRoleService;
import com.mao.whiteserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    AdminRoleDAO adminRoleDAO;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    AdminUserRoleServiceImpl adminUserRoleServiceImpl;
    @Autowired
    AdminPermissionServiceImpl adminPermissionServiceImpl;

    @Override
    public List<AdminRole> list() {
        List<AdminRole> roles = adminRoleDAO.findAll();
        List<AdminPermission> perms;
        for (AdminRole role : roles) {
            perms = adminPermissionServiceImpl.listPermsByRole(role.getId());
            role.setPerms(perms);
        }
        return roles;
    }

    @Override
    public AdminRole findById(int id) {
        return adminRoleDAO.findById(id);
    }

    @Override
    public void addOrUpdate(AdminRole adminRole) {
        adminRoleDAO.save(adminRole);
    }

    @Override
    public List<AdminRole> listRolesByUser(String username) {
        int uid =  userServiceImpl.findByUserName(username).getId();
        List<AdminRole> roles = new ArrayList<>();
        List<AdminUserRole> urs = adminUserRoleServiceImpl.listAllByUidIn(uid);
        for (AdminUserRole ur: urs) {
            roles.add(adminRoleDAO.findById(ur.getRid()));
        }
        return roles;
    }
}
