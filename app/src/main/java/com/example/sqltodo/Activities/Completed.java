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

public class Completed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppCompatActivity activity = Completed.this;

    Button drawerBtn;
    private ArrayList<Task> listCompletedTasks;
    private TaskAdapter taskAdapter;
    private DatabaseHelper databaseHelper;
    private RecyclerView taskCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_task_card );

        final DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawerBtn = findViewById( R.id.drawerBtn );
        listCompletedTasks = new ArrayList<Task>();
        taskCompleted = findViewById( R.id.taskCompleted );
        taskAdapter = new TaskAdapter(Completed.this, listCompletedTasks);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskCompleted.setLayoutManager(mLayoutManager);
        taskCompleted.setItemAnimator(new DefaultItemAnimator());
        taskCompleted.setAdapter(taskAdapter);
        databaseHelper = new DatabaseHelper(activity);

        drawerBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer( GravityCompat.START );
            }
        } );

        getDataFromSQLite();

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

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listCompletedTasks.clear();
                try {
                    listCompletedTasks.addAll(databaseHelper.getCompletedTasks());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                taskAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
