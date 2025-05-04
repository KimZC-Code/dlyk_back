package com.jz.dlyk.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jz.dlyk.entity.MyUserDetail;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class MybatisPlusHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 添加用户时自动填充创建时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        this.setFieldValByName("createTime", date, metaObject);
        // 添加用户时添加编辑的用户
        MyUserDetail user = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.setFieldValByName("editBy", user.getId(),metaObject);
        this.setFieldValByName("createBy", user.getId(),metaObject);
        this.setFieldValByName("editTime", date,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 修改时，修改修改时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        this.setFieldValByName("editTime", date, metaObject);
        // 更改编辑用户
        MyUserDetail user = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.setFieldValByName("editBy", user.getId(),metaObject);
    }
}
