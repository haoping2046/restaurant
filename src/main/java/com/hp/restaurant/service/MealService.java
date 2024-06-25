package com.hp.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hp.restaurant.dto.SetmealDto;
import com.hp.restaurant.entity.Meal;

import java.util.List;

public interface MealService extends IService<Meal> {
    void saveWithSetMealDish(SetmealDto setmealDto);
    void  removeWithDish(List<Long> ids);
    void updateStatus(List<Long> ids,int status);
    SetmealDto getSetmealDtoById(Long ids);
    void updateWithSetmealDish(SetmealDto setmealDto);

}
