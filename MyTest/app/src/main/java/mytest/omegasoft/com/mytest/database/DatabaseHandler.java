package mytest.omegasoft.com.mytest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mytest.omegasoft.com.mytest.database.model.History;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "historyManager";

    // Contacts table name
    private static final String TABLE_HISTORY = "history";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TIME = "timestamp";
    private static final String KEY_WORKOUT = "workout_time";
    private static final String KEY_REST = "rest_time";
    private static final String KEY_ROUNDS = "rounds";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TYPE + " TEXT,"
                + KEY_WORKOUT + " TEXT,"
                + KEY_REST + " TEXT,"
                + KEY_ROUNDS + " INTEGER,"
                + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }


    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, history.getType());
        values.put(KEY_TIME, history.getTimestamp());
        values.put(KEY_WORKOUT, history.getWorkout());
        values.put(KEY_REST, history.getRest());
        values.put(KEY_ROUNDS, history.getRounds());

        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public History getHistory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[] { KEY_ID,
                        KEY_TYPE, KEY_WORKOUT, KEY_REST, KEY_ROUNDS, KEY_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null) return null;
        return new History(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), cursor.getString(5));
    }

    public ArrayList<History> getAllHistories() {
        ArrayList<History> historyList = new ArrayList<History>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setType(cursor.getString(1));
                history.setWorkout(cursor.getString(2));
                history.setRest(cursor.getString(3));
                history.setRounds(Integer.parseInt(cursor.getString(4)));
                history.setTimestamp(cursor.getString(5));

                historyList.add(history);
            } while (cursor.moveToNext());
        }

        // return contact list
        return historyList;
    }
}