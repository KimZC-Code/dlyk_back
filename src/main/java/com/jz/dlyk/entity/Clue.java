package com.jz.dlyk.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * 线索表(Clue)实体类
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:28
 */
@Data
@TableName("t_clue")
public class Clue implements Serializable {
    private static final long serialVersionUID = 425297515394460661L;
    /**
     * 主键，自动增长，线索ID
     */
    private Integer id;
    /**
     * 线索所属人ID
     */
    private Integer ownerId;
    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 姓名
     */
    private String fullName;
    /**
     * 称呼
     */
    private Integer appellation;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 微信号
     */
    private String weixin;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 职业
     */
    private String job;
    /**
     * 年收入
     */
    private Double yearIncome;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否需要贷款（0不需要，1需要）
     */
    private Integer needLoan;
    /**
     * 意向状态
     */
    private Integer intentionState;
    /**
     * 意向产品
     */
    private Integer intentionProduct;
    /**
     * 线索状态
     */
    private Integer state;
    /**
     * 线索来源
     */
    private Integer source;
    /**
     * 线索描述
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

