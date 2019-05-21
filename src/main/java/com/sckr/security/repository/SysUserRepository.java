package com.sckr.security.repository;

import com.sckr.security.po.SysUser;

/**
 * @author zhoukebo
 * @date 2018/9/4
 */
public interface SysUserRepository extends BaseRepository<SysUser> {

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 系统用户
     */
    SysUser findByUsername(String username);
}
