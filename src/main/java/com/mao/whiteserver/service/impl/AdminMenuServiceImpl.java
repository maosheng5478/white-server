package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.AdminMenuDAO;
import com.mao.whiteserver.entity.AdminMenu;
import com.mao.whiteserver.entity.AdminRoleMenu;
import com.mao.whiteserver.entity.AdminUserRole;
import com.mao.whiteserver.entity.User;
import com.mao.whiteserver.service.AdminMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuServiceImpl implements AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    AdminUserRoleServiceImpl adminUserRoleServiceImpl;
    @Autowired
    AdminRoleMenuServiceImpl adminRoleMenuServiceImpl;


    @Override
    public List<AdminMenu> getAllByParentIdIn(int parentId) {
        return adminMenuDAO.findAllByParentId(parentId);
    }

    @Override
    public List<AdminMenu> getMenusByCurrentUserIn() {
        // Get current user in DB.
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userServiceImpl.findByUserName(username);

        // Get roles' ids of current user.
        List<Integer> rids = adminUserRoleServiceImpl.listAllByUidIn(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        // Get menu items of these roles.
        List<Integer> menuIds = adminRoleMenuServiceImpl.findAllByRidIn(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        // Adjust the structure of the menu.
        handleMenus(menus);
        return menus;
    }

    @Override
    public List<AdminMenu> getMenusByRoleIdIn(int rid) {
        List<Integer> menuIds = adminRoleMenuServiceImpl.findAllByRidIn(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);

        handleMenus(menus);
        return menus;
    }

    @Override
    public void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentIdIn(m.getId());
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }
}
