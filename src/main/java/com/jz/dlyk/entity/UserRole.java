package com.jz.dlyk.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户角色关系表(UserRole)表实体类
 *
 * @author makejava
 * @since 2025-04-21 23:32:52
 */
@Data
@TableName("t_user_role")
public class UserRole{

    private Integer id;

    private Integer userId;

    private Integer roleId;




}

