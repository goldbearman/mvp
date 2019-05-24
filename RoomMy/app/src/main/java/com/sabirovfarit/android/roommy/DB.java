package com.sabirovfarit.android.roommy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {Employee.class},version = 1)
public abstract class DB extends RoomDatabase {

    public abstract EmployeeDao employeeDao();
}
