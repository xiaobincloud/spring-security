package com.sckr.security.po;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * 用户实体类，这里要成为spring security使用的实体类必须要实现UserDetails接口
 * @author zhoukebo
 * @date 2018/9/4
 */
@Entity
@Data
public class SysUser extends BaseEntity {

    private String username;

    private String password;

    /***和角色是多对多的关系*/
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysRole> roles;
}
