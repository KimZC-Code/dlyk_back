package com.jz.dlyk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.dlyk.dto.R;
import com.jz.dlyk.entity.MyUserDetail;
import com.jz.dlyk.service.MyUserDetailService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private MyUserDetailService myUserDetailService;
    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * 获取当前登录用户的信息
     * @param authentication 用户的认证信息
     * @return 当前登录的用户的信息
     */
    @GetMapping("/log/info")
    public R<UserDetails> loginInfo(Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        return R.success(userDetail);
    }

    /**
     * 免登录接口
     * @return  成功响应
     */
    @GetMapping("/freeLogin")
    public R<Object> freeLogin(){
        return R.success();
    }

    /**
     * 登出接口
     * @param authentication 当前登录的用户的认证信息
     * @return 成功响应
     */
    @GetMapping("/logout")
    public R<Object> logout(Authentication authentication){
        MyUserDetail user = (MyUserDetail) authentication.getPrincipal();
        stringRedisTemplate.delete(user.getUsername() + "token");
        return R.success();
    }

    /**
     * 分页查询用户
     * @return  查询出来的用户的page
     */
    @GetMapping("/queryUserPage")
    public R<IPage<MyUserDetail>> queryAllUsers(@RequestParam Integer current,@RequestParam Integer size){
        IPage<MyUserDetail> userPage = new Page<>(current,size);
        return R.success(myUserDetailService.page(userPage));
    }

    /**
     * 根据id查询用户
     * @param id 要查询的用户的id
     * @return 返回查询到的用户
     */
    @GetMapping("/queryUserById")
    public R<MyUserDetail> queryUserById(@RequestParam Integer id){
        return R.success(myUserDetailService.getById(id));
    }

    /**
     * 添加用户
     * @param myUserDetail  要添加的用户的信息
     * @param authentication    当前登录的用户的信息
     * @return  是否添加成功
     */
    @PostMapping("/addUser")
    public R<Object> addUser(@RequestBody MyUserDetail myUserDetail,Authentication authentication){
        log.info(myUserDetail.toString());
        MyUserDetail principal = (MyUserDetail) authentication.getPrincipal();
        Integer id = principal.getId();
        myUserDetail.setPassword(passwordEncoder.encode(myUserDetail.getPassword()));
        myUserDetail.setCreateBy(id);
        myUserDetail.setEditBy(id);
        myUserDetail.setAccountEnabled(1);
        myUserDetail.setEditTime(new Date());
        boolean save = myUserDetailService.save(myUserDetail);
        return save ? R.success():R.failure();
    }

    /**
     * 根据用户id集合删除多个用户
     * @param ids 用户id的集合
     * @return 返回是否删除数据
     */
    @DeleteMapping("/deleteByUserIds")
    public R<Object> deleteByUserIds(@RequestBody Integer[] ids){
        List<Integer> idList = Arrays.stream(ids).toList();
        boolean b = myUserDetailService.deleteUsersAndRolesByIds(idList);
        return b ? R.success():R.failure();
    }

    /**
     * 修改用户
     * @param myUserDetail 修改用户的新数据
     * @return  返回修改结果
     */
    @PutMapping("/updateUser")
    public R<Object> updateUser(@RequestBody MyUserDetail myUserDetail){
        boolean result = myUserDetailService.updateById(myUserDetail);
        return result ? R.success():R.failure();
    }

    /**
     * 根据用户id删除单个用户
     * @param id 要删除的用户id
     * @return 返回删除结果
     */
    @DeleteMapping("/deleteByUserId")
    public R<Object> deleteOneById(@RequestParam Integer id){
        Boolean result = myUserDetailService.deleteUserAndRoleById(id);
        return result ? R.success():R.failure();
    }
}
