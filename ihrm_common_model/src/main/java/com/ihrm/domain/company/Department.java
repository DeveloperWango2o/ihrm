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
@Table(name = "co_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    private static final long serialVersionUID = -9084332495284489553L;

    @Id
    private String id;

    /**
     * 父级id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门编码
     */
    private String code;

    /**
     * 负责人id
     */
    private String managerId;
    /**
     * 负责人名称
     */
    private String manager;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 创建时间
     */
    private Date createTime;
}
