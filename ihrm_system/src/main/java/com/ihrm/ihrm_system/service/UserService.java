package com.ihrm.ihrm_system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.ihrm_system.dao.RoleDao;
import com.ihrm.ihrm_system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    public User findByMobileAndPassword(String mobile,String password){
        User user = userDao.findByMobile(mobile);
        if (user!=null && password.equals(user.getPassword())){
            return user;
        }else {
            return null;
        }
    }

    /**
     * 保存用户
     * @param user
     */
    public void save(User user){
        // 填充其他参数
        user.setId(idWorker.nextId()+"");
        user.setCreateTime(new Date());
        user.setPassword("123456");
        user.setEnableState(1);//状态
        userDao.save(user);
    }

    /**
     * 更新用户
     * @param user
     */
    public void update(User user){
        User one = userDao.getOne(user.getId());
        one.setPassword(user.getPassword());
        one.setUsername(user.getUsername());
        one.setDepartmentId(user.getDepartmentId());
        one.setMobile(user.getMobile());
        one.setDepartmentName(user.getDepartmentName());
        userDao.save(one);
    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    public User findById(String id){
       return userDao.findById(id).get();
    }

    /**
     * 删除部门
     * @param id 部门id
     */
    public void delete(String id){
        userDao.deleteById(id);
    }


    public Page<User> findSearch(Map<String,Object> map,int page,int size){
        return userDao.findAll(createSpecification(map), PageRequest.of(page-1,size));
    }

    /**
     * 调整用户部门
     * @param deptId
     * @param deptName
     * @param ids
     */
    public void  changeDept(String deptId,String deptName,List<String> ids){
        for (String id : ids) {
            User user = userDao.findById(id).get();
            user.setDepartmentName(deptName);
            user.setDepartmentId(deptId);
            userDao.save(user);
        }
    }


    public void assignRoles(String userId,List<String> roleIds){
        User user = userDao.findById(userId).get();
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和角色的相互关联关系
        user.setRoles(roles);
        userDao.save(user);
    }


    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))){
                    predicateList.add(cb.equal(root.get("id").as(String.class),(String)searchMap.get("id")));
                }
                //手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))){
                    predicateList.add(cb.equal(root.get("mobile").as(String.class),(String)searchMap.get("mobile")));
                }
                //用户ID
                if (searchMap.get("departmentId")!=null && !"".equals(searchMap.get("departmentId"))){
                    predicateList.add(cb.like(root.get("departmentId").as(String.class),(String)searchMap.get("departmentId")));
                }
                //标题
                if (searchMap.get("formOfEmployment")!=null && !"".equals(searchMap.get("formOfEmployment"))){
                    predicateList.add(cb.like(root.get("formOfEmployment").as(String.class),(String)searchMap.get("formOfEmployment")));
                }
                if (searchMap.get("companyId")!=null && !"".equals(searchMap.get("companyId"))){
                    predicateList.add(cb.like(root.get("companyId").as(String.class),(String)searchMap.get("companyId")));
                }
                if (searchMap.get("hasDept")!=null && !"".equals(searchMap.get("hasDept"))){
                    if ("0".equals((String)searchMap.get("hasDept"))){
                        predicateList.add(cb.isNull(root.get("departmentId")));
                    }else {
                        predicateList.add(cb.isNotNull(root.get("departmentId")));
                    }
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }
}
