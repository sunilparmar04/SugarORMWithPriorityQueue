package sunil.sugarormwithpriorityqueue.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import sunil.sugarormwithpriorityqueue.db.UserDetails;
import sunil.sugarormwithpriorityqueue.model.Request;

public class SaveDataService extends Service {

    Context mContext;
    private static final Object OBJ_LOCK = new Object(); //better
    private PriorityBlockingQueue<Request> mQueue = new PriorityBlockingQueue<>();
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private DownloadTask downloadTask;

    private void addtoQueue(Request request) {
        mQueue.clear();
        mQueue.add(request);
    }

    private int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {

            synchronized (OBJ_LOCK) {
                if (intent != null && intent.hasExtra("userDetails")) {
                    Log.v("download_executing", "size:" + mQueue.size());
                    ArrayList<UserDetails> userDetails = intent.getParcelableArrayListExtra("userDetails");
                    Request request = new Request();
                    request.setSequence(getSequenceNumber());
                    request.setUserDetailsArrayList(userDetails);
                    addtoQueue(request);
                }
                int i = SugarRecord.deleteAll(UserDetails.class);
                Log.v("download_executing", "start service Deleted:" + i);

                if (downloadTask == null) {
                    downloadTask = new DownloadTask();
                    downloadTask.start();
                    Log.v("download_executing", "first time running");
                } else {
                    Log.e("download_executing", "Already running:" + downloadTask.isAlive());
                }

            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onQuit() {
        // do something
        mQueue.clear();
        Log.e("download_executing", "COmpleteddddddddddddddddd=========n stop::" + SugarRecord.count(UserDetails.class));
        stopSelf();
    }

    class DownloadTask extends Thread {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                try {
                    Request request = mQueue.take();
                    SugarRecord.saveInTx(request.getUserDetailsArrayList());
                    if (mQueue != null && mQueue.isEmpty()) {
                        onQuit();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("", "" + e.getMessage());
                }
            }

        }
    }

}
