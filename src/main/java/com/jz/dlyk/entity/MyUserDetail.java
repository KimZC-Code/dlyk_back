package com.jz.dlyk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_user")
public class MyUserDetail implements UserDetails {
    private Integer id;
    @TableField("login_act")
    private String username;
    @TableField("login_pwd")
    private String password;
    private String name;
    private String phone;
    private String email;
    private Integer accountNoExpired;
    private Integer credentialsNoExpired;
    private Integer accountNoLocked;
    private Integer accountEnabled;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer createBy;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date editTime;
    private Integer editBy;
    private Date lastLoginTime;
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNoExpired ==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNoLocked == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNoExpired == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.accountEnabled == 1;
    }
}
