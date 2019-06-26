package com.example.sqltodo.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqltodo.Dashboard;
import com.example.sqltodo.JSON.Task;
import com.example.sqltodo.R;
import com.example.sqltodo.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTask extends AppCompatActivity {

    private static final String[] Priority = {"Priority", "High", "Medium", "Low"};
    private static final String[] Period = {"Preferred time", "Morning", "Afternoon", "Evening", "Night"};
    Integer priority =  0,  prefTime = 0;
    Date startD, endD;
    Button btnSave, btnCancel;
    DatePickerDialog startDatePicker, endDatePicker;
    private Spinner prefSpinner;
    private EditText name, estDuration, actDuration, desc;
    private TextView startDate, endDate;
    boolean error = false;
    RadioButton prRadio, prefRadio;
    RadioGroup priorityRadio, prefRadioGroup;
    DatabaseHelper taskHelper;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_task );

        taskHelper = new DatabaseHelper( this );
        name = findViewById( R.id.name );
        startDate = findViewById( R.id.startDate );
        endDate = findViewById( R.id.endDate );
        estDuration = findViewById( R.id.estduration );
        desc = findViewById( R.id.desc );
        btnCancel = findViewById( R.id.btncancel );
        priorityRadio = findViewById( R.id.priority );
        RadioButton high = findViewById( R.id.high );
        RadioButton med = findViewById( R.id.medium );
        RadioButton low = findViewById( R.id.low );
        prefRadioGroup = findViewById( R.id.pref );
        RadioButton morn = findViewById( R.id.morning );
        RadioButton after = findViewById( R.id.afternoon );
        RadioButton even = findViewById( R.id.evening );
        RadioButton night = findViewById( R.id.night );
        btnSave = findViewById( R.id.btnsave );

        name.setText( getIntent().getStringExtra( "name" ) );
        startDate.setText( getIntent().getStringExtra( "startDate" ) );
        endDate.setText( getIntent().getStringExtra( "endDate" ) );
        estDuration.setText( getIntent().getStringExtra( "est" ) );
        desc.setText( getIntent().getStringExtra( "desc" ) );

        if (getIntent().getStringExtra( "prefTime" ).equals( "Morning" )){
            morn.setSelected( true );
        } else  if (getIntent().getStringExtra( "prefTime" ).equals( "Evening" )){
            even.setSelected( true );
        } else  if (getIntent().getStringExtra( "prefTime" ).equals( "Afternoon" )){
            after.setSelected( true );
        } else  if (getIntent().getStringExtra( "prefTime" ).equals( "Noght" )){
            night.setSelected( true );
        }

        if (getIntent().getStringExtra( "priority" ).equals( "High" )){
            high.setSelected( true );
        } else  if (getIntent().getStringExtra( "priority" ).equals( "Medium" )){
            med.setSelected( true );
        } else  if (getIntent().getStringExtra( "priority" ).equals( "Low" )){
            low.setSelected( true );
        }

        startDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar start = Calendar.getInstance();
                int mYear = start.get( Calendar.YEAR );
                int mMonth = start.get( Calendar.MONTH );
                int mDay = start.get( Calendar.DAY_OF_MONTH );

                startDatePicker = new DatePickerDialog( EditTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText( dayOfMonth + "/" + (month + 1) + "/" + year );
                        startD = start.getTime();
                    }
                }, mYear, mMonth, mDay );
                startDatePicker.show();
            }
        } );

        endDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar end = Calendar.getInstance();
                int mYear = end.get( Calendar.YEAR );
                int mMonth = end.get( Calendar.MONTH );
                int mDay = end.get( Calendar.DAY_OF_MONTH );

                endDatePicker = new DatePickerDialog( EditTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText( dayOfMonth + "/" + (month + 1) + "/" + year );
                        endD = end.getTime();
                    }
                }, mYear, mMonth, mDay );
                endDatePicker.show();
            }
        } );

        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = priorityRadio.getCheckedRadioButtonId();
                prRadio = findViewById(selectedId);

                if (prRadio.getText().equals( "High" )){
                    priority =1;
                } else if (prRadio.getText().equals( "Medium" )){
                    priority =2;
                } else if (prRadio.getText().equals( "Low" )){
                    priority =3;
                }

                int selectId = prefRadioGroup.getCheckedRadioButtonId();
                prefRadio = findViewById(selectId);

                if (prefRadio.getText().equals( "Morning" )){
                    prefTime = 1;
                } else if (prefRadio.getText().equals( "Afternoon" )){
                    prefTime = 2;
                } else if (prefRadio.getText().equals( "Evening" )){
                    prefTime = 3;
                } else if (prefRadio.getText().equals( "Night" )){
                    prefTime = 4;
                }


                if (name.getText().toString().isEmpty()) {
                    error = true;
                    name.setError( "Enter a task name." );
                }

                if (estDuration.getText().toString().isEmpty()) {
                    error = true;
                    estDuration.setError( "Enter an estimated duration" );
                }

                if (!error){
                    Toast.makeText( getApplicationContext(), "Task being inserted.", Toast.LENGTH_LONG ).show();
                    Task task = new Task();
                    task.setName( name.getText().toString() );
                    task.setStartDate( startDate.getText().toString() );
                    task.setEstDuration( Integer.parseInt( estDuration.getText().toString() ) );
                    task.setDesc( desc.getText().toString() );
                    task.setEndDate( endDate.getText().toString() );
                    task.setPrefTime( prefTime );
                    task.setPriority( priority );
                    taskHelper.addTask( task );

                    Toast.makeText( getApplicationContext(), "Task Inserted", Snackbar.LENGTH_LONG).show();
                }
            }
        } );

        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), TaskCard.class);
                startActivity(intent);
            }
        } );
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError( "error" );
            selectedTextView.setTextColor( Color.RED );
            selectedTextView.setText( error );
        }
    }

    @Override
    public void finish() {
        super.finish();
    }


    @Override
    public void onBackPressed() {
        startActivity( new Intent( getApplicationContext(), Dashboard.class ) );
    }

}
