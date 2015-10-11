package mytest.omegasoft.com.mytest.callbacks;

import mytest.omegasoft.com.mytest.interfaces.Workout;
import mytest.omegasoft.com.mytest.interfaces.WorkoutType;

public interface WorkoutCallback {
    void onTimerProgress(long progress, long timeElapsed, long timeRemaining, int round, WorkoutType workoutType);

    void onTrainingStart();

    void onExerciseStart(int time);

    void onExerciseFinish();

    void onRestStart(int time);

    void onRestFinish();

    void onTrainingFinish(Workout workout, String msg);

    void onTrainingTerminated(Workout workout, String msg);

    void onPauseTimer(long currenttime);
}