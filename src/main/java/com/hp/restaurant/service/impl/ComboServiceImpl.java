package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.entity.Combo;
import com.hp.restaurant.mapper.ComboMapper;
import com.hp.restaurant.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComboServiceImpl extends ServiceImpl<ComboMapper, Combo> implements ComboService {
}
