package com.hp.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hp.restaurant.common.R;
import com.hp.restaurant.dto.DishDto;
import com.hp.restaurant.entity.Dish;
import com.hp.restaurant.service.CategoryService;
import com.hp.restaurant.service.DishFlavorService;
import com.hp.restaurant.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("add a dish successfully!");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int limit, String name) {
        Page<Dish> pageInfo = new Page(page, limit);
        Page<DishDto> dishDtoPage = new Page<>(page, limit);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("edit a dish successfully!");
    }

    /**
     * show dishes when add a combo
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> getDishList(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishList = dishService.list(queryWrapper);

        return R.success(dishList);
    }

    @PostMapping("/status/{status}")
    public R<String> updateState(@RequestParam List<Long> ids, @PathVariable int status) {
        dishService.updateStatus(ids,status);
        return R.success("operated successfully!");
    }

    @DeleteMapping
    public R<String> deleteDish(@RequestParam List<Long> ids) {
        dishService.removeWithFlavor(ids);
        return R.success("delete successfully!");
    }
}
