package com.example.sqltodo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sqltodo.Dashboard;
import com.example.sqltodo.DatabaseHelper;
import com.example.sqltodo.R;
import com.example.sqltodo.TipsTrick;

import java.text.DecimalFormat;

public class ReportCard extends AppCompatActivity {

    CardView tips;
    ProgressBar completionpg, actpg;
    TextView comp, act;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_report_card );

        helper = new DatabaseHelper( this );
        tips = findViewById( R.id.tips );
        completionpg = findViewById( R.id.completionpg );
        completionpg.setMax( 100 );
        completionpg.setMin( 0 );
        actpg = findViewById( R.id.actpg );
        actpg.setMax( 100 );
        actpg.setMin( 0 );
        comp = findViewById( R.id.completionTV );
        act = findViewById( R.id.actualTV );

        Double completionP = helper.reportTaskCompletion();
        comp.setText( new DecimalFormat("#").format((int) (long) Math.round( completionP )) + "%" );
        Integer c = (int) (long) Math.round( completionP );
        completionpg.setProgress( c );

        Double timeP = helper.reportTime();
        act.setText( new DecimalFormat("#").format((int) (long) Math.round( timeP )) + "%");
        actpg.setProgress( (int) (long) Math.round( timeP ));

        tips.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), TipsTrick.class ) );
            }
        } );
    }
}
