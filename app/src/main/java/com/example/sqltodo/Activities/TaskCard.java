package com.example.sqltodo.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sqltodo.Adapters.TaskAdapter;
import com.example.sqltodo.JSON.Task;
import com.example.sqltodo.R;
import com.example.sqltodo.DatabaseHelper;

import java.text.ParseException;
import java.util.ArrayList;

public class TaskCard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppCompatActivity activity = TaskCard.this;


    private ArrayList<Task> listTasks;
    private TaskAdapter taskAdapter;
    private DatabaseHelper databaseHelper;
    private RecyclerView taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_task_card );

        listTasks = new ArrayList<Task>();
        taskList = findViewById( R.id.taskList );
        taskList.setLayoutManager( new LinearLayoutManager( this ) );

        FloatingActionButton fab = findViewById( R.id.fab );
        taskList = findViewById( R.id.taskList );
        listTasks = new ArrayList<Task>();
        taskAdapter = new TaskAdapter( TaskCard.this, listTasks );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        taskList.setLayoutManager( mLayoutManager );
        taskList.setItemAnimator( new DefaultItemAnimator() );
        taskList.setAdapter( taskAdapter );
        databaseHelper = new DatabaseHelper( activity );


        getDataFromSQLite();

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(), AddTask.class ) );
            }
        } );
    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listTasks.clear();
                try {
                    listTasks.addAll( databaseHelper.getAllOnGoingTasks() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute( aVoid );
                taskAdapter.notifyDataSetChanged();
            }
        }.execute();


    }

    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case 1:
                taskAdapter.removeItem( item.getGroupId() );
                return true;
            case 2:
                taskAdapter.markComplete( item.getGroupId() );
                return true;
            default:
                return super.onContextItemSelected( item );
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ongoing) {
            startActivity( new Intent( getApplicationContext(), TaskCard.class ) );
        } else if (id == R.id.completed) {
            startActivity( new Intent( getApplicationContext(), Completed.class ) );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
