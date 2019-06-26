package com.example.sqltodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView register;
    Button login;
    EditText email, password;
    SQLiteDatabase db;
    String TempPassword = "NOT_FOUND";
    DatabaseHelper sqLiteHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        login = findViewById( R.id.buttonLogin );
        register = findViewById( R.id.buttonRegister );
        email = findViewById( R.id.editEmail );
        password = findViewById( R.id.editPassword );

        sqLiteHelper = new DatabaseHelper( this );

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFunction();
            }
        } );

        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, Register.class );
                startActivity( intent );
            }
        } );

    }

    public void LoginFunction() {


        String error = "false";

        if (email.getText().toString().isEmpty()) {
            email.setError( "Enter an email." );
            error = "true";
        }

        if (!Patterns.EMAIL_ADDRESS.matcher( email.getText().toString() ).matches()) {
            email.setError( "Enter a valid email." );
            email.requestFocus();
            error = "true";
            return;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError( "Enter a password" );
            error = "true";
        }

        if (error.equals( "false" )) {
            if (sqLiteHelper.checkUser( email.getText().toString().trim(), password.getText().toString().trim() )) {
                SharedPreferences sharedPreferences = getSharedPreferences( "USER", MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email.getText().toString());
                editor.apply();
                Intent intent = new Intent( getApplicationContext(), Dashboard.class );
                startActivity( intent );
            }
        } else {
            Toast.makeText( this, "Unable to login", Toast.LENGTH_SHORT ).show();
        }
    }

}

