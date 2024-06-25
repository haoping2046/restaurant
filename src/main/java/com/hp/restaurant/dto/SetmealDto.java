package com.hp.restaurant.dto;


import com.hp.restaurant.entity.Meal;
import com.hp.restaurant.entity.MealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Meal {

    private List<MealDish> setmealDishes;

    private String categoryName;
}
