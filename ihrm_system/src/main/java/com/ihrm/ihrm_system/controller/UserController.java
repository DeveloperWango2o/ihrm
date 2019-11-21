package com.ihrm.ihrm_system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.User;
import com.ihrm.ihrm_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    //保存用户
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result add(@RequestBody User user) throws Exception{
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user);
        return Result.SUCCESS();
    }

    //更新用户
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id")String id,@RequestBody User user) throws Exception{
        userService.update(user);
        return Result.SUCCESS();
    }

    //删除用户
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id")String id)throws Exception{
        userService.delete(id);
        return Result.SUCCESS();
    }

    //根据id查询用户
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id")String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findByPage(int page, int size, Map<String,Object> map)  throws Exception{
        map.put("companyId",companyId);
        Page<User> searchPage = userService.findSearch(map, page, size);
        PageResult<User> pageResult = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @RequestMapping(value = "/user/assignRoles",method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String,Object> map){
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取角色的id列表
        List<String> roleIds = (List<String>)map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }
}
