package com.example.sqltodo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqltodo.Dashboard;
import com.example.sqltodo.DatabaseHelper;
import com.example.sqltodo.JSON.Task;
import com.example.sqltodo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewTask extends AppCompatActivity {

    TextView name, start, end, pr, time, desc, est;
    Button btnupdate, btnstart, btncomplete;
    ImageView back;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_task );

        btnupdate = findViewById( R.id.btnupdate );
        btnstart = findViewById( R.id.btnstart );
        name = findViewById( R.id.name );
        start = findViewById( R.id.startDate );
        end = findViewById( R.id.endDate );
        pr = findViewById( R.id.priority );
        time = findViewById( R.id.preftime );
        desc = findViewById( R.id.desc );
        est = findViewById( R.id.estDuration );
        back = findViewById( R.id.back );
        helper = new DatabaseHelper( this );

        String tname = getIntent().getStringExtra( "name" );

        final Task task = helper.viewTask( tname );

        name.setText( task.getName() );
        start.setText( task.getStartDate() );
        end.setText( task.getEndDate() );

        switch (task.getPriority().toString() ){
            case "1": pr.setText( "High" );
                break;
            case "2": pr.setText( "Medium" );
                break;
            case "3": pr.setText( "Low" );
                break;
        }

        est.setText( task.getEstDuration().toString() );

        String tp = task.getPrefTime().toString();
        String period = "";
        if (tp.equalsIgnoreCase( "1" )){
            period = "Morning";
        } else if (tp.equalsIgnoreCase( "2" )){
            period = "Afternoon";
        } else if (tp.equalsIgnoreCase( "3" )){
            period = "Evening";
        }  else if (tp.equalsIgnoreCase( "4" )) {
            period = "Night";
        }
        time.setText( period );

        desc.setText( task.getDesc() );

        btnupdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), EditTask.class );
                intent.putExtra( "name", getIntent().getStringExtra( "name" ) );
                intent.putExtra( "startDate", start.getText() );
                intent.putExtra( "endDate", end.getText() );
                intent.putExtra( "priority", pr.getText() );
                intent.putExtra( "prefTime", time.getText() );
                intent.putExtra( "desc", desc.getText() );
                startActivity( intent );
            }
        } );

        btnstart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), Timer.class );
                intent.putExtra( "est", est.getText().toString() );
                intent.putExtra( "name", name.getText().toString() );
                startActivity( intent );
            }
        } );

        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), Dashboard.class );
                startActivity( intent );
            }
        } );

    }
}
