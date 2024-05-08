package com.hp.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hp.restaurant.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
