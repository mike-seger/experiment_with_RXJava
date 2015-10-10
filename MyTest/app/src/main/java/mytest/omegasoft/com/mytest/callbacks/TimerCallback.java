package mytest.omegasoft.com.mytest.callbacks;

public interface TimerCallback {
    void onTimerProgress(long progress, long timeElapsed, long timeRemaining);

    void onTrainingStart();

    void onExerciseStart(int time);

    void onExerciseFinish();

    void onRestStart(int time);

    void onRestFinish();

    void onTrainingFinish(String msg);

    void onPauseTimer(long currenttime);
}