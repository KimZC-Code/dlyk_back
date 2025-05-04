package com.jz.dlyk.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色表(Role)表实体类
 *
 * @author makejava
 * @since 2025-04-21 23:21:57
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_role")
public class Role extends Model<Role> {

    private Integer id;

    private String role;

    private String roleName;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

