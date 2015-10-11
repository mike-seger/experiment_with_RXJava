package mytest.omegasoft.com.mytest.utils;

import android.os.CountDownTimer;

import mytest.omegasoft.com.mytest.callbacks.WorkoutCallback;
import mytest.omegasoft.com.mytest.interfaces.WorkoutState;
import mytest.omegasoft.com.mytest.interfaces.WorkoutType;
import mytest.omegasoft.com.mytest.interfaces.Workout;

public class WorkoutTimer {

    private WorkoutState mWorkoutState;
    private WorkoutType mWorkoutType = WorkoutType.Exercise;
    private WorkoutCallback mWorkoutCallback;
    private MyTimer mMyTimer;
    private long exerciseTimeRemaining;
    private long msElapsed = 0;
    private long msRemain;
    private Workout currWorkout;
    private int mcurrentRound = 1;

    public WorkoutTimer(WorkoutCallback callback, Workout workout) {
        currWorkout = workout;
        mWorkoutCallback = callback;
        mWorkoutState = WorkoutState.STOPPED;
    }

    public void runTraining() {

        msRemain = currWorkout.getWorkoutTime();

        if (mWorkoutState == WorkoutState.STOPPED) {
            mWorkoutCallback.onTrainingStart();
        }

        startExercise();
    }

    private void startExercise() {
        mWorkoutType = WorkoutType.Exercise;
        if (mWorkoutState == WorkoutState.STOPPED) {
            msElapsed = 0;
            mMyTimer = new MyTimer(currWorkout.getWorkoutTime());
            mWorkoutCallback.onExerciseStart(currWorkout.getWorkoutTime());
            mMyTimer.start();
            mWorkoutState = WorkoutState.STARTED;
        } else {
            mMyTimer = new MyTimer(exerciseTimeRemaining);
            mMyTimer.start();
            mWorkoutState = WorkoutState.STARTED;
        }
    }

    private void startRest() {
        mWorkoutType = WorkoutType.Rest;
        if (mWorkoutState == WorkoutState.STOPPED) {
            msElapsed = 0;
            mMyTimer = new MyTimer(currWorkout.getRestTime());
            mWorkoutCallback.onRestStart(currWorkout.getRestTime());
            mMyTimer.start();
            mWorkoutState = WorkoutState.STARTED;
        } else {
            mMyTimer = new MyTimer(exerciseTimeRemaining);
            mMyTimer.start();
            mWorkoutState = WorkoutState.STARTED;
        }
    }

    public boolean pauseTraining() {
        if (mWorkoutState != WorkoutState.PAUSED) {
            mMyTimer.cancel();
            mWorkoutState = WorkoutState.PAUSED;
            mWorkoutCallback.onPauseTimer(exerciseTimeRemaining);
            return true;
        }
        return false;
    }

    public void stopTraining() {
        if (mWorkoutState != WorkoutState.STOPPED) {
            mMyTimer.cancel();
            mWorkoutState = WorkoutState.STOPPED;
            mWorkoutCallback.onTrainingTerminated(currWorkout, "Training stopped");
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
            mWorkoutCallback.onTimerProgress(millisUntilFinished, (int) (msElapsed / 1000.0f), (int) (exerciseTimeRemaining), mcurrentRound, mWorkoutType);
        }

        @Override
        public void onFinish() {
            cancel();
            mWorkoutState = WorkoutState.STOPPED;
            if (mWorkoutType == WorkoutType.Exercise) {
                mWorkoutCallback.onExerciseFinish();

                startRest();
            } else if (mWorkoutType == WorkoutType.Rest) {
                mWorkoutCallback.onRestFinish();

                if (mcurrentRound < currWorkout.getRounds()) {
                    mcurrentRound++;
                    startExercise();
                } else {
                    mWorkoutCallback.onTrainingFinish(currWorkout, "Training finish");
                }
            }

        }
    }
}