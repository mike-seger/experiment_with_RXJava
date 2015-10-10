package mytest.omegasoft.com.mytest.utils;

import android.os.CountDownTimer;

import mytest.omegasoft.com.mytest.callbacks.TimerCallback;
import mytest.omegasoft.com.mytest.interfaces.TrainingState;
import mytest.omegasoft.com.mytest.interfaces.Workout;

public class TrainingTimer {

    private TrainingProgram mTrainingProgram;
    private TrainingState mTrainingState;
    private TimerCallback mTimerCallback;
    private MyTimer mMyTimer;
    private long exerciseTimeRemaining;
    private long msElapsed = 0;
    private long interval;
    private long msRemain;

    public TrainingTimer(TimerCallback callback, Workout workout) {
        mTrainingProgram = new TrainingProgram(workout);
        mTimerCallback = callback;
        mTrainingState = TrainingState.STOPPED;
    }

    public void runTraining() {

        msRemain = mTrainingProgram.getTotalTime();

        if (mTrainingState == TrainingState.STOPPED) {
            mTimerCallback.onTrainingStart();
            startExercise();
        } else if (mTrainingState == TrainingState.PAUSED) {
            startExercise();
        }
    }

    public void startExercise() {
        if (mTrainingState == TrainingState.STOPPED) {
            mMyTimer = new MyTimer(mTrainingProgram.getCurrentExercise().getWorkoutTime(), 100);
            mTimerCallback.onExerciseStart(mTrainingProgram.getCurrentExercise());
            mMyTimer.start();
            mTrainingState = TrainingState.STARTED;
        } else {
            mMyTimer = new MyTimer(exerciseTimeRemaining, 100);
            mMyTimer.start();
            mTrainingState = TrainingState.STARTED;
        }
    }

    public void pauseTraining() {
        mMyTimer.cancel();
        mTrainingState = TrainingState.PAUSED;
    }

    public void stopTraining() {
        if (mTrainingState != TrainingState.STOPPED) {
            mMyTimer.cancel();
            mTrainingState = TrainingState.STOPPED;
            mTimerCallback.onTrainingFinish("Trening anulowano");
        }

    }

    class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            interval = countDownInterval;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            msElapsed += interval;

            exerciseTimeRemaining = millisUntilFinished;
            mTimerCallback.onTimerProgress(millisUntilFinished, (int) Math.round(msElapsed / 1000.0) * 1000, (int) Math.round((msRemain - msElapsed) / 1000.0) * 1000);
        }

        @Override
        public void onFinish() {
            cancel();
            mTrainingState = TrainingState.STOPPED;
            mTimerCallback.onExerciseFinish();

            mTrainingProgram.stepNext();
            if (mTrainingProgram.getCurrentExercise() != null) {
                startExercise();
            } else {
                mTimerCallback.onTrainingFinish("Trening zakończono");
            }
        }
    }
}