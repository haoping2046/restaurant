package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.dto.DishDto;
import com.hp.restaurant.entity.Dish;
import com.hp.restaurant.entity.DishFlavor;
import com.hp.restaurant.mapper.DishMapper;
import com.hp.restaurant.service.DishFlavorService;
import com.hp.restaurant.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;

    /**
     * add new dish and simultaneously add flavors
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto); // save to dish table

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
//        flavors.stream().map(item -> {
//            item.setDishId(dishId);
//            return item;
//        }).collect(Collectors.toList());

        flavors.forEach(item -> item.setDishId(dishId));

        dishFlavorService.saveBatch(flavors);
    }
}
