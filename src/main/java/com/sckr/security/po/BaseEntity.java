package com.sckr.security.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaobin
 * Created by xiaobin on 2017/12/8
 * Copyright (c) 2016-2019
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    private Boolean validity = true;
}

