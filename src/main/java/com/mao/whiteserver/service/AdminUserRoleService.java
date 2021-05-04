package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.AdminRole;
import com.mao.whiteserver.entity.AdminUserRole;

import java.util.List;

public interface AdminUserRoleService {

    List<AdminUserRole> listAllByUidIn(int uid);

    void saveRoleChanges(int uid, List<AdminRole> roles);
}
