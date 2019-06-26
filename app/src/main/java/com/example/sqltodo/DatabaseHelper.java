package com.example.sqltodo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.sqltodo.JSON.Task;
import com.example.sqltodo.JSON.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="timely";
    public static final String TABLE_USER="users";
    public static final String COL_1_ID="id";
    public static final String COL_2_NAME="name";
    public static final String COL_3_EMAIL="email";
    public static final String COL_4_PASSWORD="password";
    
    public static final String TABLE_TASK="task";
    public static final String COL_1_TID="id";
    public static final String COL_2_TNAME="name";
    public static final String COL_3_START="startDate";
    public static final String COL_4_END="endDate";
    public static final String COL_5_EST="estDuration";
    public static final String COL_6_PREF="prefTime";
    public static final String COL_7_PR="priority";
    public static final String COL_8_ACT="actDuration";
    public static final String COL_9_STATUS="status";
    public static final String COL_10_DESC="descr";


    String CREATE_TASK_TABLE="CREATE TABLE IF NOT EXISTS "
            + TABLE_TASK
            + "("
            + COL_1_TID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_2_TNAME + " VARCHAR, "
            + COL_3_START  + " VARCHAR, "
            + COL_4_END + " VARCHAR, "
            + COL_5_EST + " INTEGER, "
            + COL_6_PREF + " INTEGER, "
            + COL_7_PR + " INTEGER, "
            + COL_8_ACT + " INTEGER,"
            + COL_9_STATUS + " VARCHAR,"
            + COL_10_DESC + " VARCHAR)";

    String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+ TABLE_USER + " ("
            + COL_1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_2_NAME + " VARCHAR, "
            + COL_3_EMAIL + " VARCHAR, "
            + COL_4_PASSWORD + " VARCHAR)";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASK;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
        database.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK);
        onCreate(db);
    }

    public String getUser(String email){
        String name = "";

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COL_2_NAME,
        };

        String selection = COL_3_EMAIL + "=?";

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                new String[]{email},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString( cursor.getColumnIndex( COL_2_NAME ));
                } while (cursor.moveToNext());
            }
        }

        return name;
    }

    public void updateUser(String updateName, String email){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2_NAME, updateName);
        long result = db.update( TABLE_USER, values, COL_3_EMAIL + "=?", new String[]{email} );
    }

    public boolean addUser(User user){
        Log.d( TAG, "Add user entered" );
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2_NAME, user.getName());
        values.put(COL_3_EMAIL, user.getEmail());
        values.put(COL_4_PASSWORD, user.getPassword());
        long result = db.insert(TABLE_USER, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COL_1_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COL_3_EMAIL + " = ?" + " AND " + COL_4_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2_TNAME, task.getName());
        values.put(COL_3_START, task.getStartDate());
        values.put(COL_4_END, task.getEndDate());
        values.put(COL_5_EST, task.getEstDuration());
        values.put(COL_6_PREF, task.getPrefTime());
        values.put(COL_7_PR, task.getPriority());
        values.put(COL_9_STATUS, "ongoing");
        values.put( COL_10_DESC, task.getDesc() );

        db.insert(TABLE_TASK, null, values);
    }

    public List<Task> getAllOnGoingTasks() throws ParseException {

        String[] columns = {
                COL_2_TNAME,
                COL_3_START,
                COL_4_END,
                COL_5_EST,
                COL_6_PREF,
                COL_7_PR,
                COL_8_ACT,
                COL_10_DESC
        };

        String selection = COL_9_STATUS + "=?";

        List<Task> taskList = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASK, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                new String[]{"ongoing"},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    task.setName( cursor.getString( cursor.getColumnIndex( COL_2_TNAME ) ) );
                    task.setStartDate( cursor.getString( cursor.getColumnIndex( COL_3_START ) ));
                    task.setStartDate( cursor.getString( cursor.getColumnIndex( COL_10_DESC ) ));
                    taskList.add(task);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        return taskList;
    }

    public Task viewTask(String name) {

        String[] columns = {
                COL_2_TNAME,
                COL_3_START,
                COL_4_END,
                COL_5_EST,
                COL_6_PREF,
                COL_7_PR,
                COL_10_DESC
        };

        String selection = COL_2_NAME + "=?";

        Task viewtask = new Task();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASK, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                new String[]{name},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {

                    viewtask.setName( cursor.getString( cursor.getColumnIndex( COL_2_TNAME ) ) );
                    viewtask.setStartDate( cursor.getString( cursor.getColumnIndex( COL_3_START ) ));
                    viewtask.setPriority( cursor.getInt( cursor.getColumnIndex( COL_7_PR ) )  );
                    viewtask.setPrefTime( cursor.getInt( cursor.getColumnIndex( COL_6_PREF ) ) );
                    viewtask.setEndDate( cursor.getString( cursor.getColumnIndex( COL_4_END ) ) );
                    viewtask.setDesc( cursor.getString( cursor.getColumnIndex( COL_10_DESC ) ) );
                    viewtask.setEstDuration( cursor.getInt( cursor.getColumnIndex( COL_5_EST ) ) );
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        return viewtask;
    }



    public List<Task> getCompletedTasks() throws ParseException {

        String[] columns = {
                COL_2_TNAME,
                COL_3_START,
                COL_4_END,
                COL_5_EST,
                COL_6_PREF,
                COL_7_PR,
                COL_8_ACT,
                COL_10_DESC
        };

        List<Task> taskList = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASK, //Table to query
                columns,    //columns to return
                COL_9_STATUS,        //columns for the WHERE clause
                new String[]{"completed"},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setName( cursor.getString( cursor.getColumnIndex( COL_2_TNAME ) ) );
                task.setStartDate( COL_3_START);
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    public void updateTask(Task task) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_2_TNAME, task.getName());
        values.put(COL_3_START, task.getStartDate());
        values.put(COL_4_END, task.getEndDate());
        values.put(COL_5_EST, task.getEstDuration());
        values.put(COL_6_PREF, task.getPrefTime());
        values.put(COL_7_PR, task.getPriority());

        db.update(TABLE_TASK, values, COL_1_TID + " = ?", new String[]{String.valueOf(task.getTaskid())});
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, COL_1_TID + " = ?",
                new String[]{String.valueOf(task.getTaskid())});
    }
}


