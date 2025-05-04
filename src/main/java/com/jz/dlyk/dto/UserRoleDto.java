package com.jz.dlyk.dto;

import com.jz.dlyk.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRoleDto {
    private Integer id;
    private String loginAct;
    private String loginPwd;
    private String name;
    private String phone;
    private String email;
    private Integer accountNoExpired;
    private Integer credentialsNoExpired;
    private Integer accountNoLocked;
    private Integer accountEnabled;
    private Date createTime;
    private Integer createBy;
    private Date editTime;
    private Integer editBy;
    private Date lastLoginTime;
    private List<Role> roles;
}
