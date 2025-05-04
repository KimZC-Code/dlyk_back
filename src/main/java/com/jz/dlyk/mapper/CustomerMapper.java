package com.jz.dlyk.mapper;

import com.jz.dlyk.entity.Customer;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户表(Customer)表数据库访问层
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:38
 */
 @Mapper
public interface CustomerMapper extends MPJBaseMapper<Customer> {

}

