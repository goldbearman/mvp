//package com.bignerdranch.android.photogallery;
//
//import android.app.job.JobParameters;
//import android.app.job.JobService;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class PoollService extends JobService {
//
//    private PollTask mCurrentTask;
//
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        mCurrentTask = new PollTask();
//        mCurrentTask.execute(params);
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        if (mCurrentTask != null) {
//            mCurrentTask.cancel(true);
//        }
//        return true;
//    }
//}
//
//public class PollTask extends AsyncTask<JobParameters, Void, Void> {
//    @Override
//    protected Void doInBackground(JobParameters... params) {
//        JobParameters jobParams = params[0];
//// Проверка новых изображений на Flickr
//        jobFinished(jobParams, false);
//        return null;
//    }
//}