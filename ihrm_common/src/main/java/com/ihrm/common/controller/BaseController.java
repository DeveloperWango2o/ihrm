package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共controller
 *      获取request,response
 *      获取企业id,获取企业名称
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;
    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.companyId = "1";
        this.companyName = "河南师慧信息技术有限公司";
    }

}
