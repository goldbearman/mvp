package com.sabirovfarit.android.roommy;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getById(long id);

//    @Query("SELECT employee.name, employee.salary,department.name AS department_name  " +
//            "FROM employee, department "+
//    "WHERE department.id == employee.department_id")
//    public List<EmployeeDepartment> getEmpWithDepart();

    @Insert
    void insert(Employee employee);

    @Insert
    void insert(List<Employee> employees);

    @Update
    void update(Employee employee);

    @Insert
    void insert(Employee[] employees);

    @Delete
    void delete(Employee employee);

//    @Insert
//    void insert(Department department);
//
//    @Update
//    void update(Department department);
//
//    @Delete
//    void delete(Department department);





}
