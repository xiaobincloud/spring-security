package com.sckr.security.service;

import com.sckr.security.po.SysResource;
import com.sckr.security.po.SysRole;
import com.sckr.security.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobin
 * @Title: SysRoleService
 * @date 2019/05/31
 * @Description:
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Transactional
    public SysRole queryByRoleName(String name){
        return sysRoleRepository.findByNameAndValidity(name, true);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<String> searchAllRoleName() {
        List<SysRole> list = sysRoleRepository.findByValidity(true);
        List<String> roleNameList = new ArrayList<>();
        for (SysRole ae : list) {
            if (!roleNameList.contains(ae.getName())) {
                roleNameList.add(ae.getName());
            }
        }
        return roleNameList;
    }

    // 根据权限名称查询对应的资源路径集合
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<String> querySourceByRoleName(String name){
        SysRole sysRole = sysRoleRepository.findByNameAndValidity(name, true);
        List<String> pathList = new ArrayList<>();
        if (sysRole == null) {
            return pathList;
        }
        for (SysResource resource : sysRole.getResources()) {
            if (!pathList.contains(resource.getMethodPath())) {
                pathList.add(resource.getMethodPath());
            }
        }
        return pathList;
    }
}
