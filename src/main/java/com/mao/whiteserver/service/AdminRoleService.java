package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.AdminRole;

import java.util.List;

public interface AdminRoleService {
    List<AdminRole> list();

    AdminRole findById(int id);

    void addOrUpdate(AdminRole adminRole);

    List<AdminRole> listRolesByUser(String username);
}
