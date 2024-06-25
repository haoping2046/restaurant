package com.hp.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hp.restaurant.common.R;
import com.hp.restaurant.dto.SetmealDto;
import com.hp.restaurant.entity.Meal;
import com.hp.restaurant.service.CategoryService;
import com.hp.restaurant.service.MealDishService;
import com.hp.restaurant.service.MealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class MealController {
    @Resource
    private MealService setmealService;

    @Resource
    private MealDishService setmealDishService;

    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithSetMealDish(setmealDto);
        return R.success("add successfully!");
    }

    @GetMapping("/page")
    public R<Page> showSetmealInfo(int page,int pageSize,String name) {
        Page<Meal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Meal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Meal::getName,name);
        queryWrapper.orderByDesc(Meal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Meal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            setmealDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> deleteWithSetmealDishByIds(@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return R.success("delete successfully!");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids, @PathVariable int status) {
        setmealService.updateStatus(ids, status);
        return R.success("operated successfully!");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealDtoById(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getSetmealDtoById(id);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> updateWithSetmealDish(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithSetmealDish(setmealDto);
        return R.success("edit successfully!");
    }
}
