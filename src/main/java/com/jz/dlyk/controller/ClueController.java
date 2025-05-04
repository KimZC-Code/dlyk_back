package com.jz.dlyk.controller;

import com.jz.dlyk.entity.Clue;
import com.jz.dlyk.service.ClueService;
import com.jz.dlyk.dto.R;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;
/**
 * 线索表(Clue)表控制层
 *
 * @author Kim ZC
 * @since 2025-05-03 07:32:28
 */
@Slf4j
@RestController
@RequestMapping("clue")
public class ClueController {
    /**
     * 服务对象
     */
    @Resource
    private ClueService clueService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 页面大小
     * @return 查询结果
     */
    @GetMapping("pageQueryClue")
    public R<Page<Clue>> queryByPage(@RequestParam Integer current,@RequestParam Integer size) {
        return R.success(this.clueService.page(new Page<>(current,size)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R<Clue> queryById(@PathVariable("id") Integer id) {
        return R.success(this.clueService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param clue 实体
     * @return 新增结果
     */
    @PostMapping("addClue")
    public R<Boolean> add(@RequestBody Clue clue) {
        return R.success(this.clueService.save(clue));
    }

    /**
     * 编辑数据
     *
     * @param clue 实体
     * @return 编辑结果
     */
    @PutMapping
    public R<Clue> edit(@RequestBody Clue clue) {
        boolean result = this.clueService.updateById(clue);
        return result ? R.success():R.failure();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("deleteClueById")
    public R<Boolean> deleteById(Integer id) {
        return R.success(this.clueService.removeById(id));
    }
    /**
     * 删除多条数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
     @DeleteMapping("deleteClueByIds")
     public R<Object> deleteByIds(List<Integer> ids){
        return R.success(this.clueService.removeByIds(ids));
     }

}

