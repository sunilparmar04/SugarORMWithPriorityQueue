package sunil.sugarormwithpriorityqueue.utils;

import android.app.Application;

import com.orm.SugarContext;

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
}
