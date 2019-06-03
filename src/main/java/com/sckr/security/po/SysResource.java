package com.sckr.security.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @author zhoukebo
 * @date 2018/9/4
 */
@Entity
@Data
public class SysResource extends BaseEntity{

    /**资源名称*/
    private String resourceName;
    /**资源所对应的包路径*/
    private String methodPath;
    /**一级资源*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceId", referencedColumnName = "id")
    private SysResource parent;
    /**资源排序*/
    private Integer orders;
    /**子级资源*/
    @OrderBy(value = "orders")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    private List<SysResource> childResource;
}
