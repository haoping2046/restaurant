package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.common.CustomException;
import com.hp.restaurant.entity.Category;
import com.hp.restaurant.entity.Combo;
import com.hp.restaurant.entity.Dish;
import com.hp.restaurant.mapper.CategoryMapper;
import com.hp.restaurant.service.CategoryService;
import com.hp.restaurant.service.DishService;
import com.hp.restaurant.service.ComboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishService dishService;

    @Resource
    private ComboService comboService;

    /**
     * remove category
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        // check if this category relates at least one dish
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long countDish = dishService.count(dishLambdaQueryWrapper);
        if (countDish > 0) {
            throw new CustomException("This category cannot be deleted because it is associated with dishes");
        }

        // check if this category relates at least one combo
        LambdaQueryWrapper<Combo> comboLambdaQueryWrapper = new LambdaQueryWrapper<>();
        comboLambdaQueryWrapper.eq(Combo::getCategoryId, id);
        long countCombo = comboService.count(comboLambdaQueryWrapper);
        if (countCombo > 0) {
            throw new CustomException("This category cannot be deleted because it is associated with combo");
        }

        super.removeById(id);
    }
}