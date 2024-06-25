package com.hp.restaurant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Meal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    //0:stop selling 1:selling
    private Integer status;

    private String description;

    private String image;

    private Integer isDeleted;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
