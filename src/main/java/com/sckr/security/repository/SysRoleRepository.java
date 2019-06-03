package com.sckr.security.repository;

import com.sckr.security.po.SysRole;

import java.util.List;

/**
 * @author zhoukebo
 * @date 2018/9/4
 */
public interface SysRoleRepository extends BaseRepository<SysRole> {

    SysRole findByNameAndValidity(String name, boolean validity);

    List<SysRole> findByValidity(boolean validity);
}
