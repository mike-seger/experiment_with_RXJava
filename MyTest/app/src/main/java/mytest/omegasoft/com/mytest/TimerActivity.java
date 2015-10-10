package mytest.omegasoft.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimerActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.bind(this);
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
    }
}
