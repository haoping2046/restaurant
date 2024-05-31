package com.hp.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hp.restaurant.dto.DishDto;
import com.hp.restaurant.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
}
