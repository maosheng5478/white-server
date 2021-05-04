package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.AdminPermission;

import java.util.List;
import java.util.Set;

public interface AdminPermissionService {
    List<AdminPermission> list();

    AdminPermission findById(int id);

    boolean needFilter(String requestAPI);

    List<AdminPermission> listPermsByRole(int rid);

    Set<String> listPermissionURLsByUser(String username);
}
