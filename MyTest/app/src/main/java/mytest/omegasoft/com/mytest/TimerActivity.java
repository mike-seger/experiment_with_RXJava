package mytest.omegasoft.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fslogger.lizsoft.lv.fslogger.FSLogger;
import mytest.omegasoft.com.mytest.callbacks.TimerCallback;
import mytest.omegasoft.com.mytest.interfaces.TrainingType;
import mytest.omegasoft.com.mytest.interfaces.Workout;
import mytest.omegasoft.com.mytest.utils.TrainingTimer;
import mytest.omegasoft.com.mytest.views.CircleTimerView;

public class TimerActivity extends AppCompatActivity {

    @Bind(R.id.txtText1)
    TextView txtText1;

    @Bind(R.id.timeactivity_circletimerview)
    CircleTimerView circleTimerView;

    @Bind(R.id.btnPause)
    Button btnPause;

    @Bind(R.id.btnStop)
    Button btnStop;

    @Bind(R.id.btnStart)
    Button btnStart;

    private Workout currWorkout;
    private TrainingTimer trainingTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currWorkout = (Workout) extras.get("workout");
            if (currWorkout == null) finish();
        } else {
            finish();
        }

        trainingTimer = new TrainingTimer(timerCallback, currWorkout);
        circleTimerView.setTime(currWorkout.getWorkoutTime());

        FSLogger.w(1, "ffff time:" + currWorkout.getWorkoutTime());
    }

    private TimerCallback timerCallback = new TimerCallback() {
        @Override
        public void onTimerProgress(long progress, long timeElapsed, long timeRemaining, int round, TrainingType trainingType) {
            FSLogger.w(1, "ffff onTimerProgress progress:" + progress + " timeElapsed:" + timeElapsed + " timeRemaining:" + timeRemaining);

            circleTimerView.setTime((int) timeRemaining);
            circleTimerView.setCurrentStatus(trainingType.toString());
            circleTimerView.setCurrentRound(round);
            circleTimerView.refreshView();
        }

        @Override
        public void onTrainingStart() {
            FSLogger.w(1, "ffff onTrainingStart");
//            circleTimerView.startTimer();
        }

        @Override
        public void onExerciseStart(int time) {
            FSLogger.w(1, "ffff onExerciseStart");
            circleTimerView.setTime(time);
//            circleTimerView.startTimer();
        }

        @Override
        public void onExerciseFinish() {
            FSLogger.w(1, "ffff onExerciseFinish");
//            circleTimerView.stopTimer();
        }

        @Override
        public void onRestStart(int time) {
            FSLogger.w(1, "ffff onRestStart");
            circleTimerView.setTime(time);
//            circleTimerView.startTimer();
        }

        @Override
        public void onRestFinish() {
            FSLogger.w(1, "ffff onRestFinish");
//            circleTimerView.stopTimer();
        }

        @Override
        public void onTrainingFinish(String msg) {
            FSLogger.w(1, "ffff onTrainingFinish");
        }

        @Override
        public void onPauseTimer(long currenttime) {
//            circleTimerView.pauseTimer((int) currenttime);
            FSLogger.w(1, "ffff onPauseTimer");
        }
    };

    @OnClick(R.id.btnPause)
    synchronized void clickPauseButton() {
        if (trainingTimer.pauseTraining()) {
            btnPause.setText(getText(R.string.Resume));
        } else {
            btnPause.setText(getText(R.string.Pause));
            trainingTimer.runTraining();
        }
    }

    @OnClick(R.id.btnStop)
    synchronized void clickStopButton() {
        trainingTimer.stopTraining();
        btnStart.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnStart)
    synchronized void clickStartButton() {
        trainingTimer.runTraining();

        btnStart.setVisibility(View.GONE);
        btnPause.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
    }
}
