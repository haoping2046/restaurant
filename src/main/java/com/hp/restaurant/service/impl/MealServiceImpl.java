package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.common.CustomException;
import com.hp.restaurant.dto.SetmealDto;
import com.hp.restaurant.entity.Meal;
import com.hp.restaurant.entity.MealDish;
import com.hp.restaurant.mapper.MealMapper;
import com.hp.restaurant.service.MealDishService;
import com.hp.restaurant.service.MealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl extends ServiceImpl<MealMapper, Meal> implements MealService {
    @Resource
    private MealDishService setmealDishService;

    @Override
    @Transactional
    public void saveWithSetMealDish(SetmealDto setmealDto) {
        this.save(setmealDto); // save combo table

        List<MealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId()); // set combo id to combo_dish
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes); // save combo_dish table
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // select if the meal is active
        LambdaQueryWrapper<Meal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Meal::getStatus, 1);
        queryWrapper1.in(Meal::getId, ids);

        // select count(*) from where id in (...) and status = 1
        int count = (int) this.count(queryWrapper1);
        if (count > 0) {
            throw new CustomException("Meal is active，cannot delete");
        }

        this.removeByIds(ids);

        // delete from setmeal_dish where setmeal id in (...)
        LambdaQueryWrapper<MealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(MealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapper2);
    }

    @Override
    @Transactional
    public void updateStatus(List<Long> ids, int status) {
        LambdaQueryWrapper<Meal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Meal::getId, ids);
        Meal setmeal = new Meal();
        setmeal.setStatus(status);
        this.update(setmeal,queryWrapper);
    }

    @Override
    @Transactional
    public SetmealDto getSetmealDtoById(Long ids) {
        Meal setmeal = this.getById(ids);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<MealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MealDish::getSetmealId, setmeal.getId());
        List<MealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    @Override
    @Transactional
    public void updateWithSetmealDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        // delete prev meal
        LambdaQueryWrapper<MealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MealDish::getDishId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);

        // add curr meal
        Long mealId = setmealDto.getId();
        List<MealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(item -> item.setSetmealId(mealId));

        setmealDishService.saveBatch(setmealDishes);
    }
}
