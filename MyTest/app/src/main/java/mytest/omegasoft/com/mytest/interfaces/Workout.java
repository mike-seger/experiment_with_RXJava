package mytest.omegasoft.com.mytest.interfaces;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by farhad on 10/10/15.
 */
public abstract class Workout {

    //times are by seconds
    @Getter @Setter String name = "worksout";
    @Getter @Setter int workoutTime = 60;
    @Getter @Setter int restTime = 30;
    @Getter @Setter int rounds = 3;

    public Workout(String name, int workoutTime, int restTime, int rounds) {
        this.name = name;
        this.workoutTime = workoutTime;
        this.restTime = restTime;
        this.rounds = rounds;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
