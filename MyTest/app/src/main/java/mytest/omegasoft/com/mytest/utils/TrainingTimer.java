package mytest.omegasoft.com.mytest.utils;

import android.os.CountDownTimer;

import mytest.omegasoft.com.mytest.callbacks.TimerCallback;
import mytest.omegasoft.com.mytest.interfaces.TrainingState;
import mytest.omegasoft.com.mytest.interfaces.TrainingType;
import mytest.omegasoft.com.mytest.interfaces.Workout;

public class TrainingTimer {

    private TrainingState mTrainingState;
    private TrainingType mTrainingType = TrainingType.Exercise;
    private TimerCallback mTimerCallback;
    private MyTimer mMyTimer;
    private long exerciseTimeRemaining;
    private long msElapsed = 0;
    private long msRemain;
    private Workout currWorkout;
    private int mcurrentRound = 1;

    public TrainingTimer(TimerCallback callback, Workout workout) {
        currWorkout = workout;
        mTimerCallback = callback;
        mTrainingState = TrainingState.STOPPED;
    }

    public void runTraining() {

        msRemain = currWorkout.getWorkoutTime();

        if (mTrainingState == TrainingState.STOPPED) {
            mTimerCallback.onTrainingStart();
        }

        startExercise();
    }

    private void startExercise() {
        mTrainingType = TrainingType.Exercise;
        if (mTrainingState == TrainingState.STOPPED) {
            msElapsed = 0;
            mMyTimer = new MyTimer(currWorkout.getWorkoutTime());
            mTimerCallback.onExerciseStart(currWorkout.getWorkoutTime());
            mMyTimer.start();
            mTrainingState = TrainingState.STARTED;
        } else {
            mMyTimer = new MyTimer(exerciseTimeRemaining);
            mMyTimer.start();
            mTrainingState = TrainingState.STARTED;
        }
    }

    private void startRest() {
        mTrainingType = TrainingType.Rest;
        if (mTrainingState == TrainingState.STOPPED) {
            msElapsed = 0;
            mMyTimer = new MyTimer(currWorkout.getRestTime());
            mTimerCallback.onRestStart(currWorkout.getRestTime());
            mMyTimer.start();
            mTrainingState = TrainingState.STARTED;
        } else {
            mMyTimer = new MyTimer(exerciseTimeRemaining);
            mMyTimer.start();
            mTrainingState = TrainingState.STARTED;
        }
    }

    public boolean pauseTraining() {
        if (mTrainingState != TrainingState.PAUSED) {
            mMyTimer.cancel();
            mTrainingState = TrainingState.PAUSED;
            mTimerCallback.onPauseTimer(exerciseTimeRemaining);
            return true;
        }
        return false;
    }

    public void stopTraining() {
        if (mTrainingState != TrainingState.STOPPED) {
            mMyTimer.cancel();
            mTrainingState = TrainingState.STOPPED;
            mTimerCallback.onTrainingFinish("Training stopped");
        }
    }

    class MyTimer extends CountDownTimer {

        private final long interval = 1000;

        public MyTimer(long millisInFuture) {
            super(millisInFuture * 1000, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            msElapsed += interval;

            exerciseTimeRemaining = millisUntilFinished / 1000;
            mTimerCallback.onTimerProgress(millisUntilFinished, (int) (msElapsed / 1000.0f), (int) (exerciseTimeRemaining), mcurrentRound, mTrainingType);
        }

        @Override
        public void onFinish() {
            cancel();
            mTrainingState = TrainingState.STOPPED;
            if (mTrainingType == TrainingType.Exercise) {
                mTimerCallback.onExerciseFinish();

                startRest();
            } else if (mTrainingType == TrainingType.Rest) {
                mTimerCallback.onRestFinish();

                if (mcurrentRound < currWorkout.getRounds()) {
                    mcurrentRound++;
                    startExercise();
                } else {
                    mTimerCallback.onTrainingFinish("Training finish");
                }
            }

        }
    }
}