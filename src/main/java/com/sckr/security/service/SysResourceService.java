package com.sckr.security.service;

import com.sckr.security.po.SelectVO;
import com.sckr.security.po.SourceTreeVO;
import com.sckr.security.po.SysResource;
import com.sckr.security.repository.SysResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobin
 * @Title: SysResourceService
 * @date 2019/05/15
 * @Description:
 */
@Service
public class SysResourceService {

    @Autowired
    private SysResourceRepository sysResourceRepository;

    /**
     * 获取一级菜单
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<SysResource> rootSource(){
        Specification<SysResource> specification = ((Root<SysResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> pageables = new ArrayList<>();
            pageables.add(criteriaBuilder.equal(root.get("validity"), true));
            pageables.add(root.get("parent").isNull());
            return criteriaBuilder.and(pageables.toArray(new Predicate[]{}));
        });
        return sysResourceRepository.findAll(specification);
    }

    /**
     * 根据上级节点查询子集菜单
     * @param parent
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<SysResource> childSource(SysResource parent) {
        Specification<SysResource> specification = ((Root<SysResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("validity"), true));
            list.add(criteriaBuilder.equal(root.get("parent"), parent));
            return criteriaBuilder.and(list.toArray(new Predicate[]{}));
        });
        return sysResourceRepository.findAll(specification);
    }


    /**
     * 获取左侧资源菜单
     * @return
     */
    @Transactional(readOnly = true)
    public List<SourceTreeVO> sourceTree(List<String> idsList) {
        List<SourceTreeVO> parentSources = new ArrayList<>();
        if (idsList == null || idsList.isEmpty()) {
            return parentSources;
        }
        List<SysResource> parents = this.rootSource();
        if (!parents.isEmpty()) {
            for (SysResource re : parents) {
                if (idsList.contains(re.getId())) {
                    List<SysResource> childList = re.getChildResource();
                    List<SourceTreeVO> childSources = new ArrayList<>();
                    for (SysResource child : childList) {
                        if (idsList.contains(child.getId())) {
                            childSources.add(new SourceTreeVO(child.getResourceName(), child.getMethodPath()));
                        }
                    }
                    parentSources.add(new SourceTreeVO(re.getResourceName(), re.getMethodPath(), childSources));
                }
            }
        }
        return parentSources;
    }

    @Transactional(readOnly = true)
    public SysResource queryById(String id){
        return sysResourceRepository.findByIdAndValidity(id, true);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<SelectVO> searchAllParentSource() {
        List<SysResource> parents = rootSource();
        List<SelectVO> list = new ArrayList<>();
        for (SysResource resourceEntity : parents) {
            SelectVO selectVO = new SelectVO(resourceEntity.getId(), resourceEntity.getResourceName());
            List<SysResource> childList = resourceEntity.getChildResource();
            List<SelectVO> childVoList = new ArrayList<>();
            for (SysResource child : childList) {
                //构建子级资源
                SelectVO childVO = new SelectVO(child.getId(), child.getResourceName());
                //判断是否需要选中
                childVoList.add(childVO);
            }
            selectVO.setChildList(childVoList);
            list.add(selectVO);
        }
        return list;
    }

    /**
     * 查询所有的资源集合，并更具已有的资源集合标记是否选中
     * @param sourceMap 权限已有资源集合
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<SelectVO> searchAllSourceWithAuth(Map<String, String> sourceMap) {
        List<SysResource> parents = rootSource();
        List<SelectVO> list = new ArrayList<>();
        for (SysResource resourceEntity : parents) {
            //构建父级资源
            SelectVO parentVO = new SelectVO(resourceEntity.getId(), resourceEntity.getResourceName());
            //默认未选择状态
            parentVO.setChecked(false);
            //判断是否需要选中
            if (sourceMap.get(parentVO.getKey()) != null) {
                parentVO.setChecked(true);
            }
            List<SysResource> childList = resourceEntity.getChildResource();
            List<SelectVO> childVoList = new ArrayList<>();
            for (SysResource child : childList) {
                //构建子级资源
                SelectVO childVO = new SelectVO(child.getId(), child.getResourceName());
                //默认未选择状态
                childVO.setChecked(false);
                //判断是否需要选中
                if (sourceMap.get(childVO.getKey()) != null) {
                    childVO.setChecked(true);
                }
                childVoList.add(childVO);
            }
            parentVO.setChildList(childVoList);
            list.add(parentVO);
        }
        return list;
    }
}
