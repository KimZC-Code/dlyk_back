package com.jz.dlyk.controller;

import com.jz.dlyk.entity.Activity;
import com.jz.dlyk.entity.MyUserDetail;
import com.jz.dlyk.service.ActivityService;
import com.jz.dlyk.dto.R;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;
/**
 * 市场活动表(Activity)表控制层
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:10
 */
@Slf4j
@RestController
@RequestMapping("activity")
public class ActivityController {
    /**
     * 服务对象
     */
    @Resource
    private ActivityService activityService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 页面大小
     * @return 查询结果
     */
    @GetMapping("pageQuery")
    public R<Page<Activity>> queryByPage(@RequestParam Integer current,@RequestParam Integer size) {
        return R.success(this.activityService.page(new Page<>(current,size)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R<Activity> queryById(@PathVariable("id") Integer id) {
        return R.success(this.activityService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param activity 实体
     * @return 新增结果
     */
    @PostMapping("add")
    public R<Boolean> add(@RequestBody Activity activity, Authentication authentication) {
        MyUserDetail user = (MyUserDetail) authentication.getPrincipal();

        return R.success(this.activityService.save(activity));
    }

    /**
     * 编辑数据
     *
     * @param activity 实体
     * @return 编辑结果
     */
    @PutMapping("edit")
    public R<Activity> edit(@RequestBody Activity activity) {
        boolean result = this.activityService.updateById(activity);
        return result ? R.success():R.failure();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("deleteById")
    public R<Boolean> deleteById(Integer id) {
        return R.success(this.activityService.removeById(id));
    }
    /**
     * 删除多条数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
     @DeleteMapping("deleteByIds")
     public R<Object> deleteByIds(@RequestBody List<Integer> ids){
        return R.success(this.activityService.removeByIds(ids));
     }

}

