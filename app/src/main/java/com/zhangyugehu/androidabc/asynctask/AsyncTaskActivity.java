package com.zhangyugehu.androidabc.asynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zhangyugehu.androidabc.R;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AsyncTaskActivity extends AppCompatActivity {
    private static final String TAG = "AsyncTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

//        handlerTest("handler 01");
//        handlerTest("handler 02");
//        handlerTest("handler 03");
//        handlerTest("handler 04");
        asyncTest();

    }

    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 2;
    private static final int KEEP_ALIVE_SECONDS = 60;
    private static final BlockingQueue sPoolWorkQueue = new LinkedBlockingDeque<Runnable>(128);
    private static final ThreadFactory sThreadFactory = new ThreadFactory(){
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "MyAsyncTask #" + mCount.getAndIncrement());
        }
    };
    private static Executor MY_THREAD_POOL_EXECUTOR = null;
    static {
        MY_THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    }
    private void asyncTest(){


        new DemoAsyncTask().executeOnExecutor(MY_THREAD_POOL_EXECUTOR,"开始ba1");
        new DemoAsyncTask().executeOnExecutor(MY_THREAD_POOL_EXECUTOR,"开始ba2");
        new DemoAsyncTask().executeOnExecutor(MY_THREAD_POOL_EXECUTOR,"开始ba3");
        new DemoAsyncTask().executeOnExecutor(MY_THREAD_POOL_EXECUTOR,"开始ba4");
        new DemoAsyncTask().executeOnExecutor(MY_THREAD_POOL_EXECUTOR,"开始ba5");
//        new DemoAsyncTask().execute("开始ba4");
//        new DemoAsyncTask().execute("开始ba5");
    }


    private static ExecutorService executorService = null;
    static {
//        executorService = Executors.newSingleThreadExecutor();
        executorService = Executors.newCachedThreadPool();
    }
    private void handlerTest(String flag){
        final DemoHandler handler = new DemoHandler(flag);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Progress." );
                handler.sendEmptyMessage(DemoHandler.STATE_START);
                SystemClock.sleep(2000);
                handler.sendMessage(handler.obtainMessage(DemoHandler.STATE_PROGRESS, 50));
                SystemClock.sleep(5000);
                handler.sendEmptyMessage(DemoHandler.STATE_SUCCESS);
            }
        });
    }









    static class DemoHandler extends Handler{
        public static final int STATE_START = 0;
        public static final int STATE_PROGRESS = 1;
        public static final int STATE_SUCCESS = 2;

        private String mFlag;
        public DemoHandler(String flag) {
            this.mFlag = flag;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STATE_START:
                    Log.d(TAG, "handleMessage: STATE_START " + this.mFlag);
                    break;
                case STATE_PROGRESS:
                    Log.d(TAG, "handleMessage: STATE_PROGRESS " + this.mFlag + " " + msg.obj);
                    break;
                case STATE_SUCCESS:
                    Log.d(TAG, "handleMessage: STATE_SUCCESS " + this.mFlag);
                    break;
            }
        }
    }


    public static class DemoAsyncTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + Arrays.toString(strings));
            SystemClock.sleep(2000);
            publishProgress(100);
            SystemClock.sleep(20000);
            return "background work completed.";
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: param: " + s);
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate: params: " + Arrays.toString(values));
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            Log.d(TAG, "onCancelled: param: " + s);
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            Log.d(TAG, "onCancelled: release resources");
            super.onCancelled();
        }
    }
}
