package com.jz.dlyk.service.impl;

import com.jz.dlyk.entity.Customer;
import com.jz.dlyk.mapper.CustomerMapper;
import com.jz.dlyk.service.CustomerService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 客户表(Customer)表服务实现类
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:38
 */
@Service("customerService")
public class CustomerServiceImpl extends MPJBaseServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;
}
