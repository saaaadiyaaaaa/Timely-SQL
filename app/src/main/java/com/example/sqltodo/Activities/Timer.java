package com.example.sqltodo.Activities;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqltodo.Dashboard;
import com.example.sqltodo.DatabaseHelper;
import com.example.sqltodo.R;

import static com.example.sqltodo.App.CHANNEL_1_ID;

public class Timer extends AppCompatActivity {

    private boolean running;
    Chronometer chronometer;
    Button buttonStart;
    Button buttonStop;
    Button buttonRestart;
    Button finish;
    TextView estimated;
    private long pauseOffset;
    Notification notification;
    DatabaseHelper helper;
    long est;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timer );

        notificationManager = NotificationManagerCompat.from(this);
        estimated = findViewById( R.id.estimated );
        buttonStart = findViewById( R.id.buttonStartChronometer );
        buttonStop = findViewById( R.id.buttonStopChronometer );
        buttonRestart = findViewById( R.id.buttonRestartChronometer );
        finish = findViewById( R.id.finish );
        chronometer = findViewById( R.id.chronometerExample );

        chronometer.setBase(SystemClock.elapsedRealtime());

        estimated.setText( "Estimated Time: " + getIntent().getStringExtra( "est" ) + " minutes");

        est = Long.parseLong( getIntent().getStringExtra( "est" ) ) * 60000;

        Toast.makeText( getApplicationContext(), Long.toString( est ) , Toast.LENGTH_SHORT ).show();

        chronometer.setOnChronometerTickListener( new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) == est) {
                    sendOnChannel1();
                }
            }
        } );

        finish.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.marksAsCompleted( getIntent().getStringExtra( "name" ) );
            }
        } );

        buttonStart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometer();
            }
        } );

        buttonStop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometer();
            }
        } );

        buttonRestart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetChronometer();
            }
        } );
    }

    public void startChronometer(){
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void stopChronometer(){
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void sendOnChannel1() {
        Intent resultIntent = new Intent( this, Timer.class );
        PendingIntent resultPendingIntent = PendingIntent.getActivity( this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        String title = "Time's up!";
        String message = "You've reached the estimated time for the task.";

        notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.timely_final)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority( NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel( true )
                .setContentIntent( resultPendingIntent )
                .build();

        notificationManager.notify( 1, notification );
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( getApplicationContext(), Dashboard.class ) );
    }
}




