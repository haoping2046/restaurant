package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.entity.MealDish;
import com.hp.restaurant.mapper.MealDishMapper;
import com.hp.restaurant.service.MealDishService;
import org.springframework.stereotype.Service;

@Service
public class MealDishServiceImpl extends ServiceImpl<MealDishMapper, MealDish> implements MealDishService {

}
