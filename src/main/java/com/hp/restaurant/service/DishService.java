package com.hp.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hp.restaurant.dto.DishDto;
import com.hp.restaurant.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
    public void updateStatus(List<Long> ids, int status);
    public void removeWithFlavor(List<Long> ids);
}
