package com.mao.whiteserver.controller;

import com.mao.whiteserver.entity.AdminPermission;
import com.mao.whiteserver.entity.AdminRole;
import com.mao.whiteserver.entity.User;
import com.mao.whiteserver.result.Result;
import com.mao.whiteserver.result.ResultFactory;
import com.mao.whiteserver.service.impl.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    AdminRoleServiceImpl adminRoleServiceImpl;
    @Autowired
    AdminUserRoleServiceImpl adminUserRoleServiceImpl;
    @Autowired
    AdminPermissionServiceImpl adminPermissionServiceImpl;
    @Autowired
    AdminRolePermissionServiceImpl adminRolePermissionServiceImpl;

    @RequiresPermissions("/api/admin/user")
    @GetMapping("/api/admin/user")
    public List<User> listUsers() throws Exception {
        return userServiceImpl.list();
    }
    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody User requestUser) {
        User user = userServiceImpl.findByUserName(requestUser.getUsername());
        user.setEnabled(requestUser.isEnabled());
        userServiceImpl.addOrUpdate(user);
        String message = "用户" + requestUser.getUsername() + "状态更新成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody User requestUser) {
        User user = userServiceImpl.findByUserName(requestUser.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        user.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        user.setPassword(encodedPassword);
        userServiceImpl.addOrUpdate(user);
        String message = "重置密码成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody User requestUser) {
        User user = userServiceImpl.findByUserName(requestUser.getUsername());
        user.setName(requestUser.getName());
        user.setPhone(requestUser.getPhone());
        user.setEmail(requestUser.getEmail());
        userServiceImpl.addOrUpdate(user);
        adminUserRoleServiceImpl.saveRoleChanges(user.getId(),requestUser.getRoles());
        String message = "修改用户信息成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/api/admin/role")
    public List<AdminRole> listRoles(){
        return adminRoleServiceImpl.list();
    }

    @PutMapping("/api/admin/role/status")
    public Result updateRoleStatus(@RequestBody AdminRole requestRole) {
        AdminRole adminRole = adminRoleServiceImpl.findById(requestRole.getId());
        adminRole.setEnabled(requestRole.isEnabled());
        adminRoleServiceImpl.addOrUpdate(adminRole);
        String message = "用户" + adminRole.getNameZh() + "状态更新成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/role")
    public Result editRole(@RequestBody AdminRole requestRole) {
        adminRoleServiceImpl.addOrUpdate(requestRole);
        adminRolePermissionServiceImpl.savePermChanges(requestRole.getId(), requestRole.getPerms());
        String message = "修改角色信息成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/api/admin/perm")
    public List<AdminPermission> listPerms() {
        return adminPermissionServiceImpl.list();
    }

}
