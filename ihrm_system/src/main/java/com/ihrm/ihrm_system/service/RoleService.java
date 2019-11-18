package com.ihrm.ihrm_system.service;

import com.ihrm.common.entity.PageResult;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.ihrm_system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoleDao roleDao;

    /**
     * 添加角色
     */
    public void save(Role role){
        //填充参数
        role.setId(idWorker.nextId()+"");
        roleDao.save(role);
    }

    /**
     * 删除角色
     */
    public void delete(String roleId){
        roleDao.deleteById(roleId);
    }
    /**
     * 更新角色
     */
    public void update(Role role){
        Role one = roleDao.findById(role.getId()).get();
        one.setName(role.getName());
        one.setDescription(role.getDescription());
        roleDao.save(one);
    }
    /**
     * 根据ID查询角色
     */
    public Role findById(String roleId){
        return roleDao.findById(roleId).get();
    }

    public Page<Role> findSearch(String companyId,int page,int size){
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                return cb.equal(root.get("companyId").as(String.class),companyId);
            }
        };
        return roleDao.findAll(specification, PageRequest.of(page-1,size));
    }
}
