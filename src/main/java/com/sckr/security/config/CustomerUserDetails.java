package com.sckr.security.config;

import com.sckr.security.po.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Transient;
import java.util.Collection;

/**
 * @author xiaobin
 * @Title: CustomerUserDetails
 * @date 2019/05/29
 * @Description:
 */
@Data
public class CustomerUserDetails implements UserDetails {

    private SysUser sysUser;

    private Collection<? extends GrantedAuthority> grantedAuthority;

    /***
     * 正常情况下，角色和权限是两回事，
     * 所以我们还需要重写getAuthorities方法，将用户的角色和权限关联起来
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return user().getPassword();
    }

    @Override
    public String getUsername() {
        return user().getUsername();
    }

    public SysUser user() {
        return sysUser;
    }

    public CustomerUserDetails(SysUser user) {
        this.sysUser = user;
    }
}
