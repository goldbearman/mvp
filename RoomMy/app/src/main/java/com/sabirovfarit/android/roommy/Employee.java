package com.sabirovfarit.android.roommy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int salary;
    @ColumnInfo(name = "department_id")
    private int departmentId;


    public Employee( String name, int salary, int departmentId) {

        this.name = name;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
