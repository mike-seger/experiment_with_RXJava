package mytest.omegasoft.com.mytest.callbacks;

import mytest.omegasoft.com.mytest.interfaces.Workout;

public interface TimerCallback {
    void onTimerProgress(long progress, long timeElapsed, long timeRemaining);

    void onTrainingStart();

    void onExerciseStart(Workout workout);

    void onExerciseFinish();

    void onTrainingFinish(String msg);
}