package com.jz.dlyk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jz.dlyk.dto.UserRoleDto;
import com.jz.dlyk.entity.MyUserDetail;
import com.jz.dlyk.entity.Role;
import com.jz.dlyk.entity.UserRole;
import com.jz.dlyk.mapper.MyUserDetailMapper;
import com.jz.dlyk.service.MyUserDetailService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MyUserDetailServiceImpl extends MPJBaseServiceImpl<MyUserDetailMapper, MyUserDetail> implements MyUserDetailService {
    @Resource
    private MyUserDetailMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        MyUserDetail myUserDetail = userMapper.selectOne(new LambdaQueryWrapper<MyUserDetail>()
                .eq(MyUserDetail::getUsername, username)
        );
        if (myUserDetail == null) {
            log.error("用户名不存在");
            return null;
        }else {
            List<UserRoleDto> userRoleDtos = userMapper.selectJoinList(UserRoleDto.class, new MPJLambdaWrapper<MyUserDetail>()
                    .selectAll(MyUserDetail.class)
                    .selectCollection(Role.class, UserRoleDto::getRoles)
                    .leftJoin(UserRole.class, UserRole::getUserId, MyUserDetail::getId)
                    .leftJoin(Role.class, Role::getId, UserRole::getRoleId)
                    .eq(MyUserDetail::getUsername, username)
            );
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            userRoleDtos.forEach(userRoleDto -> {
                userRoleDto.getRoles().forEach(role -> {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase());
                    grantedAuthorities.add(simpleGrantedAuthority);
                });
            });
            myUserDetail.setAuthorities(grantedAuthorities);
            log.info("登录成功"+myUserDetail.toString());
            if (myUserDetail != null){
                LocalDateTime localDateTime = LocalDateTime.now();
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                log.info(date.toString());
                boolean update = new LambdaUpdateChainWrapper<>(MyUserDetail.class)
                        .set(MyUserDetail::getLastLoginTime, date)
                        .eq(MyUserDetail::getId, myUserDetail.getId())
                        .update();
            }
            return myUserDetail;
        }
    }
    @Override
    public Boolean deleteUserAndRoleById(Integer id){
        int i = userMapper.deleteJoin(new MPJLambdaWrapper<>(MyUserDetail.class)
                .leftJoin(UserRole.class, UserRole::getUserId, MyUserDetail::getId)
                .eq(MyUserDetail::getId, id)
        );
        return i==1;
    }
    @Override
    public Boolean deleteUsersAndRolesByIds(List<Integer> ids){
        int i = userMapper.deleteJoin(new MPJLambdaWrapper<>(MyUserDetail.class)
                .leftJoin(UserRole.class, UserRole::getUserId, MyUserDetail::getId)
                .in(MyUserDetail::getId, ids)
        );
        return i>0;
    }
}
