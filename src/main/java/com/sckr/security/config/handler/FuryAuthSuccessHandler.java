package com.sckr.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.sckr.security.po.SourceTreeVO;
import com.sckr.security.po.SysResource;
import com.sckr.security.po.SysRole;
import com.sckr.security.service.SysResourceService;
import com.sckr.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理登录验证成功的类
 * @author xiaobin
 * @date 2019/6/03
 *
 */
@Component
public class FuryAuthSuccessHandler implements AuthenticationSuccessHandler {
    /**Json转化工具*/
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException{
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("code", "200");
        map.put("msg", "登录成功");
        List<String> resourceIds = new ArrayList<>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            String tempAuth = ga.getAuthority();
            tempAuth = (tempAuth.split("ROLE_"))[1];
            SysRole sysRole = sysRoleService.queryByRoleName(tempAuth);
            for (SysResource resource : sysRole.getResources()) {
                resourceIds.add(resource.getId());
            }
        }
        List<SourceTreeVO> sourceTreeVOS = sysResourceService.sourceTree(resourceIds);
        map.put("menu", sourceTreeVOS);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}