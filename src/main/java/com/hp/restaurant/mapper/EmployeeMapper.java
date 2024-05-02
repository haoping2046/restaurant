package com.hp.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hp.restaurant.entity.Employee;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("select * from employee")
    List<Employee> list();

    @Select("select * from employee where id = #{id}")
    Employee selectEmpById(Integer id);

//    @Select("select * from employee where name like '%${name}%' and sex = #{sex} and create_time = #{createTime}")
//    List<Employee> selectEmpByConditions(String name, String sex, LocalDateTime createTime);

    // use xml
    List<Employee> selectEmpByConditions(String name, String sex, LocalDateTime createTime);


    @Delete("delete from employee where id = #{id}")
    int delete(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user)" +
            "value (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insertEmp(Employee employee);

//    @Update("update employee set name = #{name}, password = #{password}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
//    void updateEmp(Employee employee);

    // use xml
    void updateEmp(Employee employee);

    void deleteByIds(List<Integer> ids);
}
