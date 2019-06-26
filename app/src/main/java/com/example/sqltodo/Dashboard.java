package com.example.sqltodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.sqltodo.Activities.CalendarCard;
import com.example.sqltodo.Activities.ReportCard;
import com.example.sqltodo.Activities.TaskCard;

public class Dashboard extends AppCompatActivity {

        CardView tasks, calendar, profile, report;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);

            tasks = findViewById( R.id.tasks );
            calendar = findViewById( R.id.calendar );
            profile = findViewById( R.id.profile );
            report = findViewById( R.id.reports );

            tasks.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( Dashboard.this, TaskCard.class ) );
                }
            } );

            calendar.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( Dashboard.this, CalendarCard.class ) );
                }
            } );

            profile.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( Dashboard.this, Profile.class ) );
                }
            } );

            report.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( Dashboard.this, ReportCard.class ) );
                }
            } );
        }
}
