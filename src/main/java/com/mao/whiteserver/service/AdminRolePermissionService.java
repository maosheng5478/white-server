package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.AdminPermission;
import com.mao.whiteserver.entity.AdminRolePermission;

import java.util.List;

public interface AdminRolePermissionService {
    List<AdminRolePermission> findAllByRid(int rid);

    void savePermChanges(int rid, List<AdminPermission> perms);
}
