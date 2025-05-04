package com.jz.dlyk.service;

import com.jz.dlyk.entity.MyUserDetail;
import com.github.yulichang.base.MPJBaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MyUserDetailService extends UserDetailsService, MPJBaseService<MyUserDetail> {
    // 根据id删除用户
    public Boolean deleteUserAndRoleById(Integer id);
    // 根据id删除多个用户
    public Boolean deleteUsersAndRolesByIds(List<Integer> ids);
}
