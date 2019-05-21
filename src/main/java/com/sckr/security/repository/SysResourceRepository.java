package com.sckr.security.repository;

import com.sckr.security.po.SysResource;

/**
 * @author zhoukebo
 * @date 2018/9/4
 */
public interface SysResourceRepository extends BaseRepository<SysResource> {

    SysResource findByIdAndValidity(String id, boolean validity);
}
