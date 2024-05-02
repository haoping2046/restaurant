package com.hp.restaurant;

import com.hp.restaurant.entity.Employee;
import com.hp.restaurant.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class EmployeeMapperTest {

    @Resource
    private EmployeeMapper employeeMapper;

    @Test
    public void testSelectAll() {
        List<Employee> employeeList = employeeMapper.list();
        employeeList.stream().forEach(e -> System.out.println(e));
    }

    @Test
    public void testSelectEmpById() {
        Employee emp = employeeMapper.selectEmpById(1);
        System.out.println(emp);
    }

    @Test
    public void testSelectEmpByConditions() {
        List<Employee> employeeList = employeeMapper.selectEmpByConditions("ha", "1", LocalDateTime.of(2024,4, 13, 0, 14, 5));
        employeeList.stream().forEach(e -> System.out.println(e));
    }

    @Test
    public void testDeleteById() {
        int delete = employeeMapper.delete(2);
        System.out.println(delete);
    }

    @Test
    public void testInsertEmp() {
        Employee emp = new Employee();
        emp.setName("tom4");
        emp.setUsername("tom4");
        emp.setPassword("123456");
        emp.setPhone("911");
        emp.setSex("1");
        emp.setIdNumber("110");
        emp.setStatus(1);
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setCreateUser(10L);
        emp.setUpdateUser(1L);

        employeeMapper.insertEmp(emp);
        System.out.println(emp.getId());
    }

    @Test
    public void testUpdateEmp() {
        Employee emp = new Employee();
        emp.setId(5L);
        emp.setName("Faye");
        emp.setPassword("4321");
        emp.setPhone("912");
        emp.setSex("2");
        emp.setIdNumber("11010419823021");
        emp.setStatus(1);
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setUpdateUser(1L);
        employeeMapper.updateEmp(emp);
    }

    @Test
    public void testDelEmpByIds() {
        List<Integer> ids = Arrays.asList(8, 9);
        employeeMapper.deleteByIds(ids);
    }


}

