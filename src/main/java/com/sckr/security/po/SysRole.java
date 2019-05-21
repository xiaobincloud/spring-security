package com.sckr.security.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 角色实体类
 * @author zhoukebo
 * @date 2018/9/4
 */
@Entity
@Data
public class SysRole extends BaseEntity{

    private String name;

    /***和资源是多对多的关系*/
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysResource> resources;
}
