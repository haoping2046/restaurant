package com.hp.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.restaurant.entity.Employee;
import com.hp.restaurant.mapper.EmployeeMapper;
import com.hp.restaurant.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
