package com.ihrm.company.Service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加公司
     * @param company
     */
    public void add(Company company){
        long id = idWorker.nextId();
        company.setId(id+"");
        company.setAuditState("0");
        company.setState(0);//0未激活 1已激活
        company.setCreateTime(new Date());
        companyDao.save(company);
    }

    /**
     * 更新公司信息
     */
    public void update(Company company){
        Company company1 = companyDao.findById(company.getId()).get();
        company1.setName(company.getName());
        company1.setCompanyPhone(company.getCompanyPhone());
        companyDao.save(company1);
    }

    /**
     * 删除企业
     * @param id 企业id
     */
    public void delete(String id){

        companyDao.deleteById(id);
    }

    /**
     * 根据id查询
     */
    public Company findById(String id){
        return companyDao.findById(id).get();
    }

    /**
     * 企业列表查询
     * @return
     */
    public List<Company> findAll(){
        return companyDao.findAll();
    }
}
