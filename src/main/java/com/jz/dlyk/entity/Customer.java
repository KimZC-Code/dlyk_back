package com.jz.dlyk.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * 客户表(Customer)实体类
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:38
 */
@Data
@TableName("t_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = -42921460007401687L;
    /**
     * 主键，自动增长，客户ID
     */
    private Integer id;
    /**
     * 线索ID
     */
    private Integer clueId;
    /**
     * 选购产品
     */
    private Integer product;
    /**
     * 客户描述
     */
    private String description;
    /**
     * 下次联系时间
     */
    private Date nextContactTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 编辑时间
     */
    private Date editTime;
    /**
     * 编辑人
     */
    private Integer editBy;



}

