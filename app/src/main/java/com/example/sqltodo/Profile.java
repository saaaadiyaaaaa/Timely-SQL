package com.example.sqltodo;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqltodo.JSON.User;

public class Profile extends AppCompatActivity {

    Button update;
    EditText etname;
    ImageView back;
    TextView tvemail;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        etname = findViewById(R.id.name);
        tvemail = findViewById(R.id.email);
        update = findViewById(R.id.updateDetails);
        back = findViewById( R.id.imageView8 );
        helper = new DatabaseHelper( this );
        SharedPreferences sharedPreferences = getSharedPreferences( "USER", MODE_PRIVATE );
        final String email = sharedPreferences.getString("email",null);

        String name = helper.getUser( email );

        etname.setText( name );
        tvemail.setText( email );

        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateName = etname.getText().toString();
                helper.updateUser( updateName, email );
            }
        } );


        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), Dashboard.class ) );
            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( getApplicationContext(), Dashboard.class ) );
    }
}
