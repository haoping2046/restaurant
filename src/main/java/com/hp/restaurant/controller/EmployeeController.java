package com.hp.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hp.restaurant.common.R;
import com.hp.restaurant.entity.Employee;
import com.hp.restaurant.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.apache.commons.lang.StringUtils;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * Login
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // MD5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // query username in db
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp == null) return R.error("User doesn't exist");

        // compare password
        if(!emp.getPassword().equals(password)) return R.error("Password is incorrect");

        // check status of emp
        if(emp.getStatus () == 0) return R.error("Account is forbidden");

        // store id in session
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * Logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // remove id from session
        request.getSession().removeAttribute("employee");
        return R.success("Exit successfully");
    }

    /**
     * Create new employee
     * @param employee
     * @param request
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // Use MyMetaObjectHandler to fill these attributes
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("Add employee successfully");
    }

    /**
     * Pagination
     * @param page
     * @param limit
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int limit, String name) {

        Page pageInfo = new Page(page, limit);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * Change state of employee
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee, HttpServletRequest request) {
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("change state of employee successfully");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if(employee != null) return R.success(employee);
        return R.error("Did not find the employee info");
    }
}
