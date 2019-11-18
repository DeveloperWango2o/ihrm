package com.ihrm.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "co_company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    //ID
    @Id
    private String id;
    /**
     * 公司名称
     */
    @Column
    private String name;
    /**
     * 企业登录账号ID
     */
    @Column(name = "manager_id")
    private String managerId;
    /**
     * 当前版本
     */
    @Column
    private String version;
    /**
     * 续期时间
     */
    @Column(name = "renewal_date")
    private Date renewalDate;
    /**
     * 到期时间
     */
    @Column(name = "expiration_date")
    private Date expirationDate;
    /**
     * 公司地区
     */
    @Column(name = "company_area")
    private String companyArea;
    /**
     * 公司地址
     */
    @Column(name = "company_address")
    private String companyAddress;
    /**
     * 营业执照-图片ID
     */
    @Column(name = "business_license_id")
    private String businessLicenseId;
    /**
     * 法人代表
     */
    @Column(name = "legal_representative")
    private String legalRepresentative;
    /**
     * 公司电话
     */
    @Column(name = "company_phone")
    private String companyPhone;
    /**
     * 邮箱
     */
    @Column
    private String mailbox;
    /**
     * 公司规模
     */
    @Column(name = "company_size")
    private String companySize;
    /**
     * 所属行业
     */
    @Column
    private String industry;
    /**
     * 备注
     */
    @Column
    private String remarks;
    /**
     * 审核状态
     */
    @Column(name = "audit_state")
    private String auditState;
    /**
     * 状态
     */
    @Column
    private Integer state;
    /**
     * 当前余额
     */
    @Column
    private Double balance;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}