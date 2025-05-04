package com.jz.dlyk.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * 市场活动表(Activity)实体类
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:10
 */
@Data
@TableName("t_activity")
public class Activity implements Serializable {
    private static final long serialVersionUID = -76018294302026837L;
    /**
     * 主键，自动增长，活动ID
     */
    private Integer id;
    /**
     * 活动所属人ID
     */
    private Integer ownerId;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 活动预算
     */
    private Double cost;
    /**
     * 活动描述
     */
    private String description;
    /**
     * 活动创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 活动创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    /**
     * 活动编辑时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date editTime;
    /**
     * 活动编辑人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer editBy;



}

