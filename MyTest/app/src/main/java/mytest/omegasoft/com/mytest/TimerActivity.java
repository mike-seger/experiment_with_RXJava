package mytest.omegasoft.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fslogger.lizsoft.lv.fslogger.FSLogger;
import mytest.omegasoft.com.mytest.callbacks.WorkoutCallback;
import mytest.omegasoft.com.mytest.database.DatabaseHandler;
import mytest.omegasoft.com.mytest.database.model.History;
import mytest.omegasoft.com.mytest.interfaces.Workout;
import mytest.omegasoft.com.mytest.interfaces.WorkoutType;
import mytest.omegasoft.com.mytest.utils.WorkoutTimer;
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
    private WorkoutTimer workoutTimer;

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

        workoutTimer = new WorkoutTimer(workoutCallback, currWorkout);
        circleTimerView.setTime(currWorkout.getWorkoutTime());
    }

    private WorkoutCallback workoutCallback = new WorkoutCallback() {
        @Override
        public void onTimerProgress(long progress, long timeElapsed, long timeRemaining, int round, WorkoutType workoutType) {
            FSLogger.w(1, "ffff onTimerProgress progress:" + progress + " timeElapsed:" + timeElapsed + " timeRemaining:" + timeRemaining);

            circleTimerView.refreshView((int) timeRemaining, workoutType.toString(), round);
        }

        @Override
        public void onTrainingStart() {
            FSLogger.w(1, "ffff onTrainingStart");
        }

        @Override
        public void onExerciseStart(int time) {
            FSLogger.w(1, "ffff onExerciseStart");
            circleTimerView.setTime(time);
        }

        @Override
        public void onExerciseFinish() {
            FSLogger.w(1, "ffff onExerciseFinish");
        }

        @Override
        public void onRestStart(int time) {
            FSLogger.w(1, "ffff onRestStart");
            circleTimerView.setTime(time);
        }

        @Override
        public void onRestFinish() {
            FSLogger.w(1, "ffff onRestFinish");
        }

        @Override
        public void onTrainingFinish(Workout workout, String msg) {
            FSLogger.w(1, "ffff onTrainingFinish");
            //Workout finished. Time to save into Database

            btnStart.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);

            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH);
            String cDateTime=dateFormat.format(new Date());


            //Save into Database
            DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
            databaseHandler.addHistory(new History(0,
                    workout.getName(),
                    String.valueOf(workout.getWorkoutTime()),
                    String.valueOf(workout.getRestTime()),
                    workout.getRounds(),
                    cDateTime));


            //TODO Save into GOOGLE Fit
        }

        @Override
        public void onTrainingTerminated(Workout workout, String msg) {

        }

        @Override
        public void onPauseTimer(long currenttime) {
            FSLogger.w(1, "ffff onPauseTimer");
        }
    };

    @OnClick(R.id.btnPause)
    synchronized void clickPauseButton() {
        if (workoutTimer.pauseTraining()) {
            btnPause.setText(getText(R.string.Resume));
        } else {
            btnPause.setText(getText(R.string.Pause));
            workoutTimer.runTraining();
        }
    }

    @OnClick(R.id.btnStop)
    synchronized void clickStopButton() {
        workoutTimer.stopTraining();
        btnStart.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnStart)
    synchronized void clickStartButton() {
        workoutTimer.runTraining();

        btnStart.setVisibility(View.GONE);
        btnPause.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        clickStopButton();
        super.onBackPressed();
    }
}
