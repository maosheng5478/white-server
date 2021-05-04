package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.AdminMenu;

import java.util.List;

public interface AdminMenuService {

    List<AdminMenu> getAllByParentIdIn(int parentId);

    List<AdminMenu> getMenusByCurrentUserIn();

    List<AdminMenu> getMenusByRoleIdIn(int rid);

    /**
     * Adjust the Structure of the menu.
     *
     * @param menus Menu items list without structure
     */
    void handleMenus(List<AdminMenu> menus);
}
