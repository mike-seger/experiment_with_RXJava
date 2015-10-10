package mytest.omegasoft.com.mytest.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

import mytest.omegasoft.com.mytest.interfaces.Workout;

public class TrainingProgram {

    private ConcurrentLinkedQueue<Workout> exerciseList;
    private Workout currentExercise;
    private long totalTime;

    public TrainingProgram() {
        exerciseList = new ConcurrentLinkedQueue<>();
        totalTime = 0;
    }

    public TrainingProgram(Workout workout) {

        this();

        for (int i = 0; i < workout.getRounds(); i++) {
            exerciseList.add(workout);
            totalTime += workout.getWorkoutTime() + workout.getRestTime();
        }
        currentExercise = exerciseList.poll();
    }

    public long getTotalTime() {
        return totalTime;
    }

    public Workout getCurrentExercise() {
        return currentExercise;
    }

    public void stepNext() {
        currentExercise = exerciseList.poll();
    }

}