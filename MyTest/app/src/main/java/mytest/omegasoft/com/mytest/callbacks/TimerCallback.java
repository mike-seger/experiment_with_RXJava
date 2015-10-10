package mytest.omegasoft.com.mytest.callbacks;

import mytest.omegasoft.com.mytest.interfaces.TrainingType;

public interface TimerCallback {
    void onTimerProgress(long progress, long timeElapsed, long timeRemaining, int round, TrainingType trainingType);

    void onTrainingStart();

    void onExerciseStart(int time);

    void onExerciseFinish();

    void onRestStart(int time);

    void onRestFinish();

    void onTrainingFinish(String msg);

    void onPauseTimer(long currenttime);
}