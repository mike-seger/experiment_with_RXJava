package mytest.omegasoft.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fslogger.lizsoft.lv.fslogger.FSLogger;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.btnTest)
    Button btnTest;

    @Bind(R.id.txtText1)
    TextView txtText1;

    @Bind(R.id.txtText2)
    TextView txtText2;

    @Bind(R.id.txtText3)
    TextView txtText3;

    @Bind(R.id.txtText4)
    TextView txtText4;


    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            FSLogger.w(1, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            FSLogger.w(1, "onError e:" + e.getMessage());
        }

        @Override
        public void onNext(String text) {
            FSLogger.w(1, "onError text:" + text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnTest)
    void clickTestButton() {
//        Observable.from(getNumbers())
//                .distinctUntilChanged()
//                .subscribeOn(Schedulers.io())
//                .buffer(10, TimeUnit.MILLISECONDS, 2)
//                .map(new Func1<List<String>, String>() {
//                    public String call(List<String> l) {
//                        return "a";
//                    }
//                })
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//                        FSLogger.w(1, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        FSLogger.w(1, "onError e:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(String stringObservable) {
//                        FSLogger.w(1, "onNext stringObservable:" + stringObservable);
//                    }
//                });

//        getApps().distinctUntilChanged()
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onError(Throwable e) {
//                        FSLogger.w(1, "onError e:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        FSLogger.w(1, "onNext s:" + s);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        FSLogger.w(1, "onCompleted");
//                    }
//                });


        Observable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        FSLogger.w(1, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        FSLogger.w(1, "onError e:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        FSLogger.w(1, "onNext aLong:" + aLong);
                        txtText1.setText(String.valueOf(aLong + " - " + System.currentTimeMillis()));
                    }
                });

    }

    private ArrayList<String> getNumbers() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            result.add(String.valueOf(i));
        }
        return result;
    }

    private Observable<String> getApps() {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        ArrayList<String> result = new ArrayList<>();
                        for (int i = 0; i < 100; i++) {
                            result.add(String.valueOf(i));
                        }
                        for (String text : result) {

                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            subscriber.onNext(text);
                        }
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }
                });
    }
}
