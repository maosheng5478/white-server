package com.mao.whiteserver.controller;

import com.mao.whiteserver.entity.AdminMenu;
import com.mao.whiteserver.service.impl.AdminMenuServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminMenuServiceImpl adminMenuServiceImpl;

    @GetMapping("/api/menu")
    @ResponseBody
    public List<AdminMenu> menu() {
        System.out.println(adminMenuServiceImpl.getMenusByCurrentUserIn());
        logger.debug("Request to get menu");
        return adminMenuServiceImpl.getMenusByCurrentUserIn();
    }

}
