package com.jz.dlyk.service.impl;

import com.jz.dlyk.entity.Clue;
import com.jz.dlyk.mapper.ClueMapper;
import com.jz.dlyk.service.ClueService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 线索表(Clue)表服务实现类
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:28
 */
@Service("clueService")
public class ClueServiceImpl extends MPJBaseServiceImpl<ClueMapper, Clue> implements ClueService {
    @Resource
    private ClueMapper clueMapper;
}
