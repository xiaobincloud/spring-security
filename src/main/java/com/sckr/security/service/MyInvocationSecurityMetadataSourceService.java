package com.sckr.security.service;

import com.google.common.collect.Maps;
import com.sckr.security.po.SysResource;
import com.sckr.security.po.SysRole;
import com.sckr.security.repository.SysResourceRepository;
import com.sckr.security.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 此类用来匹配该请求需要哪些对应的权限
 * @author zhoukebo
 * @date 2018/9/19
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

//    @Autowired
//    private SysResourceRepository permissionDao;

    @Autowired
    private SysRoleService sysRoleService;

    private Map<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = Maps.newHashMapWithExpectedSize(7);
//        Collection<ConfigAttribute> array;
//        ConfigAttribute urlString;
        List<String> permissions = sysRoleService.searchAllRoleName();
        for (String permission : permissions) {
            ConfigAttribute ca = new SecurityConfig(permission);
//            array = new ArrayList<>();
            List<String> pathList = sysRoleService.querySourceByRoleName(permission);
            for (String path : pathList) {
                // 判断资源文件和权限的对应关系，如果已经存在相关的资源url，
                // 则要通过该url为key提取出权限集合，将权限增加到权限集合中。 sparta
                if (map.containsKey(path)) {
                    Collection<ConfigAttribute> value = map.get(path);
                    value.add(ca);
                    map.put(path, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<>();
                    atts.add(ca);
                    map.put(path, atts);
                }
            }
//            urlString = new SecurityConfig(permission.getMethodPath());
//            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
//            array.add(urlString);
//            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
//            map.put(permission.getMethodPath(), array);
        }

    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
     * 进行判定用户是否有此权限。如果不在权限表中则放行。
     * @param object
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (String s : map.keySet()) {
            resUrl = s;
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}