package com.mao.whiteserver.config.filter;

import com.mao.whiteserver.exception.DefaultExceptionHandler;
import com.mao.whiteserver.service.impl.AdminPermissionServiceImpl;
import com.mao.whiteserver.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {
    @Autowired
    AdminPermissionServiceImpl adminPermissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        if (null==adminPermissionService) {
            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionServiceImpl.class);
        }

        String requestAPI = getPathWithinApplication(request);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            System.out.println("需要登录");
           /* DefaultExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();
            defaultExceptionHandler.handleAuthorizationException(new HostUnauthorizedException("400"));*/
            return false;
        }

        // 判断访问接口是否需要过滤（数据库中是否有对应信息）
        boolean needFilter = adminPermissionService.needFilter(requestAPI);
        if (!needFilter) {
            System.out.println(this.getClass()+"接口：" + requestAPI + "无需权限");
            return true;
        } else {
            // 判断当前用户是否有相应权限
            boolean hasPermission = false;
            String username = subject.getPrincipal().toString();
            Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(username);
            for (String api : permissionAPIs) {
                // 匹配前缀
                if (requestAPI.startsWith(api)) {
                    hasPermission = true;
                    break;
                }
            }

            if (hasPermission) {
                System.out.println(this.getClass()+"访问权限：" + requestAPI + "验证成功");
                return true;
            } else {
                System.out.println(this.getClass()+"当前用户没有访问接口" + requestAPI + "的权限");
                return false;
            }
        }
    }
}
