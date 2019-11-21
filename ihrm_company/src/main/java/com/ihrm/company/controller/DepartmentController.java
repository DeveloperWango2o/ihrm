package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.Service.CompanyService;
import com.ihrm.company.Service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController extends BaseController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DepartmentService departmentService;


    /**
     * 添加部门
     * @param department
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result add(@RequestBody Department department){
        department.setCompanyId(companyId);
        departmentService.save(department);
        return Result.SUCCESS();
    }

    /**
     * 修改部门信息
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id")String id,@RequestBody Department department){
        department.setCompanyId(companyId);
        department.setId(id);
        departmentService.updateDepartment(department);
        return Result.SUCCESS();
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id")String id){
        departmentService.delete(id);
        return Result.SUCCESS();
    }

    //根据id查询部门列表
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id)throws Exception{
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    //组织机构列表
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll() throws Exception{
        Company company = companyService.findById(companyId);
        List<Department> departmentList = departmentService.findAll(company.getId());
        return new Result(ResultCode.SUCCESS,new DeptListResult(company,departmentList));
    }

}
