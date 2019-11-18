package com.ihrm.ihrm_system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.ihrm_system.dao.PermissionApiDao;
import com.ihrm.ihrm_system.dao.PermissionDao;
import com.ihrm.ihrm_system.dao.PermissionMenuDao;
import com.ihrm.ihrm_system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存权限
     */
    public void save(Map<String,Object> map) throws Exception{
        //设置主键的值
        String id = idWorker.nextId()+"";
        //通过map构造permission对象
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        //根据类型构造不同的资源对象(菜单,按钮,api)
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PERMISSION_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenu.setId(id);
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PERMISSION_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PERMISSION_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //保存
        permissionDao.save(permission);
    }

    /**
     * 更新权限
     */
    public void  update(Map<String,Object> map) throws Exception{
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        //通过传递的权限id查询权限
        Permission permission = permissionDao.findById(perm.getId()).get();
        permission.setName(perm.getName());
        permission.setCode(perm.getCode());
        permission.setDescription(perm.getDescription());
        permission.setEnVisible(perm.getEnVisible());
        //根据类型构造不同的资源
        Integer type = perm.getType();
        switch (type){
            case PermissionConstants.PERMISSION_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenu.setId(perm.getId());
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PERMISSION_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(perm.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PERMISSION_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(perm.getId());
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //保存
        permissionDao.save(permission);
    }

    /**
     * 根据id查询
     *          1:查询权限
     *          2.根据权限的类型查询资源
     *          3.构造map集合
     */
    public Map<String,Object> findById(String id) throws Exception{
        Permission perm = permissionDao.findById(id).get();
        Integer type = perm.getType();
        Object object = null;
        if (type == PermissionConstants.PERMISSION_MENU){
            object = permissionMenuDao.findById(id).get();
        }else if (type == PermissionConstants.PERMISSION_POINT){
            object = permissionPointDao.findById(id).get();
        }else if (type == PermissionConstants.PERMISSION_API){
            object = permissionApiDao.findById(id).get();
        }else {
            throw new CommonException(ResultCode.FAIL);
        }

        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        map.put("enVisible",perm.getEnVisible());

        return map;
    }

    public List<Permission> findAll(Map<String,Object> map){
        //1.需要查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /*
                    动态拼接条件
                 */

            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //根据父id查询
                if (!StringUtils.isEmpty(map.get("pid"))){
                    predicateList.add(cb.equal(root.get("pid").as(String.class),map.get("pid")));
                }
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    predicateList.add(cb.equal(root.get("enVisible").as(String.class),map.get("enVisible")));
                }
                //根据类型查询
                if (!StringUtils.isEmpty(map.get("type"))){
                    String ty =(String) map.get("type");
                    CriteriaBuilder.In<Object> in = cb.in(root.get("type"));
                    if ("0".equals(ty)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(ty));
                    }
                    predicateList.add(in);
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }

    /**
     * 根据id删除
     */
    public void deleteById(String id) throws Exception{
        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        //根据类型构造不同的资源
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PERMISSION_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PERMISSION_API:
                permissionApiDao.deleteById(id);
                break;
            case PermissionConstants.PERMISSION_POINT:
                permissionPointDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }

}
