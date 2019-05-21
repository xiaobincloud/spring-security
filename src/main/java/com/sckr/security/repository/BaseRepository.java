package com.sckr.security.repository;

import com.sckr.security.po.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xiaobin
 * @Title: BaseRepository
 * @date 2019/05/15
 * @Description:
 */
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T,String>, JpaSpecificationExecutor<T> {
}
