package mytest.omegasoft.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.txtText1)
    TextView txtText1;

    @Bind(R.id.btnPause)
    Button btnPause;

    @Bind(R.id.btnStop)
    Button btnStop;

    @Bind(R.id.btnStart)
    Button btnStart;

    private Boolean isPause = false;
    private long currentTime = 0;

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Fill Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.plans_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = true;
    }

    @OnClick(R.id.btnPause)
    synchronized void clickPauseButton() {
        if (isPause) {
            btnPause.setText(getText(R.string.Pause));
        } else {
            btnPause.setText(getText(R.string.Resume));
        }

        isPause = !isPause;
    }

    @OnClick(R.id.btnStop)
    synchronized void clickStopButton() {
        unSubscribe();

        btnStart.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnStart)
    synchronized void clickStartButton() {
        isPause = false;
        currentTime = 0;

        btnStart.setVisibility(View.GONE);
        btnPause.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
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

        unSubscribe();
        subscription = Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        FSLogger.w(1, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        FSLogger.w(1, "onError e:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (isPause) return;

                        FSLogger.w(1, "onNext aLong:" + aLong);
                        currentTime++;
                        txtText1.setText(String.valueOf(aLong + " - " + currentTime));
                    }
                });
        currentTime++;

    }

    private void unSubscribe() {
        if (subscription != null) subscription.unsubscribe();
    }

    private ArrayList<String> getNumbers() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            result.add(String.valueOf(i));
        }
        return result;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unSubscribe();
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
