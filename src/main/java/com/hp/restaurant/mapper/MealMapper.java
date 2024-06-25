package com.hp.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hp.restaurant.entity.Meal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MealMapper extends BaseMapper<Meal> {
}
