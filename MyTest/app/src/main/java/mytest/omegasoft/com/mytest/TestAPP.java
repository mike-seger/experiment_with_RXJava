package mytest.omegasoft.com.mytest;

import android.app.Application;

import fslogger.lizsoft.lv.fslogger.FSLogger;

/**
 * Created by farhad on 15.9.10.
 */
public class TestAPP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize FSLogger
        FSLogger.init("TEST");
        FSLogger.setType(FSLogger.FSLoggerLimitationType.ALLOR);
        FSLogger.enableLoggingWithBackTrace();
        FSLogger.addCode(1);
        if (!BuildConfig.DEBUG) FSLogger.disable();
    }
}
