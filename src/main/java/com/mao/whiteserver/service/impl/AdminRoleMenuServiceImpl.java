package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.AdminRoleMenuDAO;
import com.mao.whiteserver.entity.AdminRoleMenu;
import com.mao.whiteserver.service.AdminRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminRoleMenuServiceImpl implements AdminRoleMenuService {
    @Autowired
    AdminRoleMenuDAO adminRoleMenuDAO;

    @Override
    public List<AdminRoleMenu> findAllByRidIn(int rid) {
        return adminRoleMenuDAO.findAllByRid(rid);
    }

    @Override
    public List<AdminRoleMenu> findAllByRidIn(List<Integer> rids) {
        return adminRoleMenuDAO.findAllByRidIn(rids);
    }

    @Modifying
    @Transactional
    @Override
    public void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds) {
        adminRoleMenuDAO.deleteAllByRid(rid);
        List<AdminRoleMenu> rms = new ArrayList<>();
        for (Integer mid : menusIds.get("menusIds")) {
            AdminRoleMenu rm = new AdminRoleMenu();
            rm.setMid(mid);
            rm.setRid(rid);
            rms.add(rm);
        }

        adminRoleMenuDAO.saveAll(rms);
    }
}
