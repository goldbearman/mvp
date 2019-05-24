package com.sabirovfarit.android.roommy;

import android.arch.persistence.room.ColumnInfo;

public class EmployeeDepartment {

    public String name;

    public int Salary;

    @ColumnInfo(name = "department_name")
    public String departmentName;
}
