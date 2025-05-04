package com.jz.dlyk.service.impl;

import com.jz.dlyk.entity.Activity;
import com.jz.dlyk.mapper.ActivityMapper;
import com.jz.dlyk.service.ActivityService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 市场活动表(Activity)表服务实现类
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:10
 */
@Service("activityService")
public class ActivityServiceImpl extends MPJBaseServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
}
