package com.hp.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hp.restaurant.common.R;
import com.hp.restaurant.entity.Category;
import com.hp.restaurant.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * Pagination
     * @param page
     * @param limit
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int limit, String name) {

        Page pageInfo = new Page(page, limit);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Category::getUpdateTime);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("Create category successfully!");
    }

    @DeleteMapping
    public R<String> delete(Long id) {
        categoryService.remove(id);
        return R.success("Delete category successfully!");
    }

    @PutMapping
    public R<String> update(@RequestBody  Category category) {
        categoryService.updateById(category);
        return R.success("Edit category successfully!");
    }

    /**
     * Show dish category when adding a dish
     * @param category
     * @retu
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
