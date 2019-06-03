package com.sckr.security.config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * 我们需要自定义对hasPermission()方法的处理，
 * 就需要自定义PermissionEvaluator，创建类CustomPermissionEvaluator，实现PermissionEvaluator接口。
 * @author xiaobin
 * @date 2019/6/3
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    /**
     * 自定义验证方法
     * @param authentication        登录的时候存储的用户信息
     * @param targetDomainObject    @PreAuthorize("hasPermission('/hello/**','r')") 中hasPermission的第一个参数
     * @param permission            @PreAuthorize("hasPermission('/hello/**','r')") 中hasPermission的第二个参数
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // 获得loadUserByUsername()方法的结果
        CustomerUserDetails user = (CustomerUserDetails)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的权限
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 遍历用户权限进行判定
        for(GrantedAuthority authority : authorities) {
            String needRole = authority.getAuthority();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.equals(ga.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
