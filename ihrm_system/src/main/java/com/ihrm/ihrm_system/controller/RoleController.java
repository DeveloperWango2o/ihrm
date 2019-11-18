package com.ihrm.ihrm_system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.ihrm_system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    /**
     * 添加角色
     */
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public Result save(@RequestBody Role role)throws Exception{
        role.setCompanyId(parseCompanyId());
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }


    /**
     * 更新角色
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id")String id, @RequestBody Role role) throws Exception{
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }

    //删除角色
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id")String id)throws Exception{
        roleService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    //根据id获取角色信息
    @RequestMapping(value = "/role/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id")String id)throws Exception{
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS,role);
    }

    /**
     * 分页查询角色
     */
    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public Result findByPage(int page,int pagesize,Role role)throws Exception{
        String companyId = "1";
        Page<Role> searchPage = roleService.findSearch(companyId, page, pagesize);
        PageResult pageResult = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }
}
