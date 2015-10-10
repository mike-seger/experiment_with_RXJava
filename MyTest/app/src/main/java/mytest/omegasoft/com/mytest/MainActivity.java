package mytest.omegasoft.com.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import mytest.omegasoft.com.mytest.callbacks.DialogCallback;
import mytest.omegasoft.com.mytest.interfaces.Workout;
import mytest.omegasoft.com.mytest.utils.utils;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.txt_workout_time)
    TextView txt_workout_time;

    @Bind(R.id.btn_edit_workout_time)
    ImageButton btn_edit_workout_time;

    @Bind(R.id.txt_rest_time)
    TextView txt_rest_time;

    @Bind(R.id.btn_edit_rest_time)
    ImageButton btn_edit_rest_time;

    @Bind(R.id.txt_rounds)
    TextView txt_rounds;

    @Bind(R.id.btn_edit_rounds)
    ImageButton btn_edit_rounds;

    @Bind(R.id.btnStart)
    Button btnStart;

    private ArrayList<Workout> workouts;
    private Workout currWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        workouts = utils.getEorkouts();
        setCurrWorkoutByPosition(0);

        //Fill Spinner
        ArrayAdapter<Workout> adapter = new ArrayAdapter<Workout>(this, android.R.layout.simple_spinner_item, workouts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.btn_edit_rest_time)
    synchronized void clickEditRestTime() {
        utils.showTimePickerDialog(this, currWorkout.getRestTime(), new DialogCallback() {
            @Override
            public void ok(int value) {
                currWorkout.setRestTime(value);
                updateUI();
            }
        });
    }

    @OnClick(R.id.btn_edit_workout_time)
    synchronized void clickEditWorkoutTime() {
        utils.showTimePickerDialog(this, currWorkout.getWorkoutTime(), new DialogCallback() {
            public void ok(int value) {
                currWorkout.setWorkoutTime(value);
                updateUI();
            }
        });
    }

    @OnClick(R.id.btn_edit_rounds)
    synchronized void clickEditRounds() {
        utils.showNumberPickerDialog(this, currWorkout.getRounds(), new DialogCallback() {
            public void ok(int value) {
                currWorkout.setRounds(value);
                updateUI();
            }
        });
    }

    @OnClick(R.id.btnStart)
    synchronized void clickStartButton() {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("name", currWorkout.getName());
        intent.putExtra("workout_time", currWorkout.getWorkoutTime());
        intent.putExtra("rest_time", currWorkout.getRestTime());
        intent.putExtra("rounds", currWorkout.getRounds());
        startActivity(intent);
    }

    private void setCurrWorkoutByPosition(int position) {
        currWorkout = workouts.get(position);
        updateUI();
    }

    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_workout_time.setText(utils.timeToString(currWorkout.getWorkoutTime()));
                txt_rest_time.setText(utils.timeToString(currWorkout.getRestTime()));
                txt_rounds.setText(String.valueOf(currWorkout.getRounds()));
            }
        });
    }

    @OnItemSelected(R.id.spinner)
    void onSelectSpinner(AdapterView<?> parent, View view, int position, long id) {
        setCurrWorkoutByPosition(position);
    }
}
