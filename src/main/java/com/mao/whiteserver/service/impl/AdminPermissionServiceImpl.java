package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.AdminPermissionDAO;
import com.mao.whiteserver.dao.AdminRolePermissionDAO;
import com.mao.whiteserver.entity.AdminPermission;
import com.mao.whiteserver.entity.AdminRole;
import com.mao.whiteserver.entity.AdminRolePermission;
import com.mao.whiteserver.service.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {

    @Autowired
    AdminPermissionDAO adminPermissionDAO;
    @Autowired
    AdminUserRoleServiceImpl adminUserRoleServiceImpl;
    @Autowired
    AdminRoleServiceImpl adminRoleServiceImpl;
    @Autowired
    AdminRolePermissionServiceImpl adminRolePermissionServiceImpl;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Override
    public AdminPermission findById(int id) {
        return adminPermissionDAO.findById(id);
    }
    @Override
    public List<AdminPermission> list() {return adminPermissionDAO.findAll();}
    @Override
    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        for (AdminPermission p: ps) {
            // 这里我们进行前缀匹配，拥有父权限就拥有所有子权限
            if (requestAPI.startsWith(p.getUrl())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<AdminPermission> listPermsByRole(int rid) {
        List<AdminRolePermission> rps = adminRolePermissionServiceImpl.findAllByRid(rid);
        List<AdminPermission> perms = new ArrayList<>();
        for (AdminRolePermission rp : rps) {
            perms.add(adminPermissionDAO.findById(rp.getPid()));
        }
        return perms;
    }
    @Override
    public Set<String> listPermissionURLsByUser(String username) {
        List<AdminRole> roles = adminRoleServiceImpl.listRolesByUser(username);
        Set<String> URLs = new HashSet<>();

        for (AdminRole role : roles) {
            List<AdminRolePermission> rps = adminRolePermissionServiceImpl.findAllByRid(role.getId());
            for (AdminRolePermission rp : rps) {
                URLs.add(adminPermissionDAO.findById(rp.getPid()).getUrl());
            }
        }
        return URLs;
    }


}
