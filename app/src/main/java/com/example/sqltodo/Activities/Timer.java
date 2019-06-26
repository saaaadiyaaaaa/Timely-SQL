package com.example.sqltodo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqltodo.Dashboard;
import com.example.sqltodo.R;

import java.util.Locale;

public class Timer extends AppCompatActivity {

    private EditText setTime;
    private TextView timerText;
    private Button setTimer;
    private Button pauseStart;
    private Button resetTimer;

    private CountDownTimer timer;

    private boolean timerRunning;

    private long startTime;
    private long timeLeft;
    private long endTime;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timer );

        setTime = findViewById( R.id.setTime );
        timerText = findViewById( R.id.timerText );
        back = findViewById( R.id.back );
        setTimer = findViewById( R.id.set );
        pauseStart = findViewById( R.id.startPause );
        resetTimer = findViewById( R.id.resetTimer );


        String input = getIntent().getStringExtra( "est" );
        long millisInput = Long.parseLong( input ) * 60000;
        setTime( millisInput );

        pauseStart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        } );

        resetTimer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        } );

        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), TaskCard.class ) );
            }
        } );
    }

    private void setTime(long milliseconds) {
        startTime = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeft;

        timer = new CountDownTimer( timeLeft, 1000 ) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                updateWatchInterface();
            }
        }.start();

        timerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        timer.cancel();
        timerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        timeLeft = startTime;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format( Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds );
        } else {
            timeLeftFormatted = String.format( Locale.getDefault(), "%02d:%02d", minutes, seconds );
        }

        timerText.setText( timeLeftFormatted );
    }

    private void updateWatchInterface() {
        if (timerRunning) {
            setTime.setVisibility( View.INVISIBLE );
            setTimer.setVisibility( View.INVISIBLE );
            resetTimer.setVisibility( View.INVISIBLE );
            pauseStart.setText( "Pause" );
        } else {
            setTime.setVisibility( View.VISIBLE );
            setTimer.setVisibility( View.VISIBLE );
            pauseStart.setText( "Start" );

            if (timeLeft < 1000) {
                pauseStart.setVisibility( View.INVISIBLE );
            } else {
                pauseStart.setVisibility( View.VISIBLE );
            }

            if (timeLeft < startTime) {
                resetTimer.setVisibility( View.VISIBLE );
            } else {
                resetTimer.setVisibility( View.INVISIBLE );
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences( "prefs", MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong( "startTimeInMillis", startTime );
        editor.putLong( "millisLeft", timeLeft );
        editor.putBoolean( "timerRunning", timerRunning );
        editor.putLong( "endTime", endTime );

        editor.apply();

        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences( "prefs", MODE_PRIVATE );

        startTime = prefs.getLong( "startTimeInMillis", 600000 );
        timeLeft = prefs.getLong( "millisLeft", startTime );
        timerRunning = prefs.getBoolean( "timerRunning", false );

        updateCountDownText();
        updateWatchInterface();

        if (timerRunning) {
            endTime = prefs.getLong( "endTime", 0 );
            timeLeft = endTime - System.currentTimeMillis();

            if (timeLeft < 0) {
                timeLeft = 0;
                timerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( getApplicationContext(), Dashboard.class ) );
    }
}

