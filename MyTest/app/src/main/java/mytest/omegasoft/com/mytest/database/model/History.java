package mytest.omegasoft.com.mytest.database.model;

import lombok.Data;

/**
 * Created by farhad on 10/11/15.
 */
@Data
public class History {

    private int id;
    private String type;
    private String workout;
    private String rest;
    private int rounds;
    private String timestamp;

    public History () {
    }

    public History (int id, String type, String workout, String rest, int rounds, String timestamp) {
        this.id = id;
        this.type = type;
        this.workout = workout;
        this.rest = rest;
        this.rounds = rounds;
        this.timestamp = timestamp;
    }
}
