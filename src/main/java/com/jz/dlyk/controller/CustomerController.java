package com.jz.dlyk.controller;

import com.jz.dlyk.entity.Customer;
import com.jz.dlyk.service.CustomerService;
import com.jz.dlyk.dto.R;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;
/**
 * 客户表(Customer)表控制层
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:38
 */
@Slf4j
@RestController
@RequestMapping("customer")
public class CustomerController {
    /**
     * 服务对象
     */
    @Resource
    private CustomerService customerService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 页面大小
     * @return 查询结果
     */
    @GetMapping("pageQueryCustomer")
    public R<Page<Customer>> queryByPage(@RequestParam Integer current,@RequestParam Integer size) {
        return R.success(this.customerService.page(new Page<>(current,size)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R<Customer> queryById(@PathVariable("id") Integer id) {
        return R.success(this.customerService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param customer 实体
     * @return 新增结果
     */
    @PostMapping("addCustomer")
    public R<Boolean> add(@RequestBody Customer customer) {
        return R.success(this.customerService.save(customer));
    }

    /**
     * 编辑数据
     *
     * @param customer 实体
     * @return 编辑结果
     */
    @PutMapping
    public R<Customer> edit(@RequestBody Customer customer) {
        boolean result = this.customerService.updateById(customer);
        return result ? R.success():R.failure();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("deleteCustomerById")
    public R<Boolean> deleteById(Integer id) {
        return R.success(this.customerService.removeById(id));
    }
    /**
     * 删除多条数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
     @DeleteMapping("deleteCustomerByIds")
     public R<Object> deleteByIds(List<Integer> ids){
        return R.success(this.customerService.removeByIds(ids));
     }

}

