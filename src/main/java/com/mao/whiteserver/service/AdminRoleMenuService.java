package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.AdminRoleMenu;

import java.util.List;
import java.util.Map;

public interface AdminRoleMenuService {

    List<AdminRoleMenu> findAllByRidIn(int rid);

    List<AdminRoleMenu> findAllByRidIn(List<Integer> rids);

    void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds);
}
