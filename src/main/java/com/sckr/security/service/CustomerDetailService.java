package com.sckr.security.service;

import com.sckr.security.config.CustomerUserDetails;
import com.sckr.security.po.SysRole;
import com.sckr.security.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要自定义UserDetailsService实现spring security的UserDetailsService接口
 * @author zhoukebo
 * @date 2018/9/4
 */
@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private SysUserRepository userRepository;

    /**
     * 根据用户查询授权的角色信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerUserDetails user = new CustomerUserDetails(userRepository.findByUsername(username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SysRole> roles = user.user().getRoles();
        //将所有的角色对应的资源权限全部放入user对应的grantedAuthority集合中
        List<SimpleGrantedAuthority> authRoles = new ArrayList<>();
        for (SysRole role : roles) {
            authRoles.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }
        user.setGrantedAuthority(authRoles);
        return user;
    }
}
