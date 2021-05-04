package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.AdminRolePermissionDAO;
import com.mao.whiteserver.entity.AdminPermission;
import com.mao.whiteserver.entity.AdminRolePermission;
import com.mao.whiteserver.service.AdminRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminRolePermissionServiceImpl implements AdminRolePermissionService {
    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;
    @Override
    public List<AdminRolePermission> findAllByRid(int rid) {
        return adminRolePermissionDAO.findAllByRid(rid);
    }

    @Override
    @Modifying
    @Transactional
    public void savePermChanges(int rid, List<AdminPermission> perms) {
        adminRolePermissionDAO.deleteAllByRid(rid);
        for (AdminPermission perm : perms) {
            AdminRolePermission rp = new AdminRolePermission();
            rp.setRid(rid);
            rp.setPid(perm.getId());
            adminRolePermissionDAO.save(rp);
        }
    }
}
