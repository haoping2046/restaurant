package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.common.CustomException;
import com.hp.restaurant.dto.DishDto;
import com.hp.restaurant.entity.Dish;
import com.hp.restaurant.entity.DishFlavor;
import com.hp.restaurant.entity.MealDish;
import com.hp.restaurant.mapper.DishMapper;
import com.hp.restaurant.service.DishFlavorService;
import com.hp.restaurant.service.DishService;
import com.hp.restaurant.service.MealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private MealDishService mealDishService;

    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto); // save to dish table

        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(item -> item.setDishId(dishId));

        dishFlavorService.saveBatch(flavors); // save to dish_flavor table
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto); // update to dish table

        // delete prev dish flavor
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // add curr dish flavor
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(item -> item.setDishId(dishId));

        dishFlavorService.saveBatch(flavors); // save to dish_flavor table
    }

    @Override
    @Transactional
    public void updateStatus(List<Long> ids, int status) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        Dish dish = new Dish();
        dish.setStatus(status);
        this.update(dish, queryWrapper);
    }

    @Override
    @Transactional
    public void removeWithFlavor(List<Long> ids) {
        // select if the dish is active
        LambdaQueryWrapper<Dish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Dish::getStatus, 1);
        queryWrapper1.in(Dish::getId, ids);

        // select count(*) from where id in (...) and status = 1
        int count = (int) this.count(queryWrapper1);
        if (count > 0) {
            throw new CustomException("Dish is active, cannot delete");
        }

        // select count(*) from meal_dish id in (...)
        LambdaQueryWrapper<MealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(MealDish::getDishId, ids);
        count = (int) mealDishService.count(queryWrapper2);
        if (count > 0) {
            throw new CustomException("Dish is included int a meal, cannot delete");
        }

        this.removeByIds(ids);

        // delete from dish_flavor where dish id in (...)
        LambdaQueryWrapper<DishFlavor> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(queryWrapper3);
    }
}
