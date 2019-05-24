package com.sabirovfarit.android.roommy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Employee employee1 = new Employee("Максимов Д.М.", 10, 2);
        Employee employee2 = new Employee("Петров К.И.", 15, 3);
        Employee employee3 = new Employee("Киров М.К.", 10, 1);

        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);

//        new FillDBAsync().execute(list);

//        App.getInstance().getDataBase().employeeDao().insert(list.toArray(new Employee[list.size()]))
//                .subscribeOn(Schedulers.newThread());


        UserFactory userFactory = new UserFactory() {
            @Override
            public User create(String name, String surname) {

                User user = new User(name,surname);
                return user;
            }
        };

        User user = userFactory.create("dfkjdf", "fdfd");







//        Flowable.fromArray(list.toArray(new Employee[list.size()]))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Subscriber<Employee>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//
//                    }
//
//                    @Override
//                    public void onNext(Employee employee) {
//                        Log.i(TAG, "onNext: "+ employee);
//                        EmployeeDao employeeDao = App.getInstance().getDataBase().employeeDao();
//                        employeeDao.insert(employee);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }

//    private class FillDBAsync extends AsyncTask<List<Employee>, Void, Void> {
//
//        @Override
//        protected Void doInBackground(List<Employee>... lists) {
//
//            EmployeeDao employeeDao = App.getInstance().getDataBase().employeeDao();
//            employeeDao.insert(lists[0]);
//            Log.i(TAG, "doInBackground: "+lists);
//            return null;
//        }
//    }


}


//        dataBase.employeeDao().insert(new Department(0,"Экономический"));
//        dataBase.employeeDao().insert(new Department(1,"Юридический"));
//        dataBase.employeeDao().insert(new Department(2,"Учебный"));

//        List<Employee> all = employeeDao.getAll();
//        Log.d(TAG, "onCreate: "+all.get(0));
