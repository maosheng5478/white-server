package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.AdminUserRoleDAO;
import com.mao.whiteserver.entity.AdminRole;
import com.mao.whiteserver.entity.AdminUserRole;
import com.mao.whiteserver.service.AdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserRoleServiceImpl implements AdminUserRoleService {
    @Autowired
    AdminUserRoleDAO adminUserRoleDAO;

    @Override
    public List<AdminUserRole> listAllByUidIn(int uid) {
        return adminUserRoleDAO.findAllByUid(uid);
    }
    @Transactional
    @Override
    public void saveRoleChanges(int uid, List<AdminRole> roles) {
        adminUserRoleDAO.deleteAllByUid(uid);
        List<AdminUserRole> urs = new ArrayList<>();
        roles.forEach(r -> {
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(uid);
            ur.setRid(r.getId());
            urs.add(ur);
        });
        adminUserRoleDAO.saveAll(urs);

    }
}
