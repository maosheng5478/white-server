package com.mao.whiteserver.dao;

import com.mao.whiteserver.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPermissionDAO extends JpaRepository<AdminPermission, Integer> {
    AdminPermission findById(int id);
}
