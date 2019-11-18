package com.ihrm.company.Service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 部门操作业务逻辑
 */
@Service
public class DepartmentService extends BaseService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 添加部门
     */
    public void save(Department department){
        //填充其他参数
        department.setId(idWorker.nextId()+"");
        department.setCreateTime(new Date());
        departmentDao.save(department);
    }

    /**
     * 更新部门
     */
    public void updateDepartment(Department department){
        Department sourceDepartment = departmentDao.findById(department.getId()).get();
        sourceDepartment.setName(department.getName());
        sourceDepartment.setPid(department.getPid());
        sourceDepartment.setManagerId(department.getPid());
        sourceDepartment.setIntroduce(department.getIntroduce());
        sourceDepartment.setManager(department.getManager());
        departmentDao.save(sourceDepartment);
    }

    /**
     * 根据id获取部门信息
     * @param id
     * @return
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }

    /**
     * 根据Id删除部门
     * @param id
     */
    public void  delete(String id){
        departmentDao.deleteById(id);
    }

    /**
     * 获取部门列表
     *
     */
    public List<Department> findAll(String companyId){
        return departmentDao.findAll(getSpecification(companyId));
    }


}
