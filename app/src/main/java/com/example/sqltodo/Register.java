package com.example.sqltodo;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqltodo.JSON.User;

public class Register extends AppCompatActivity {


    EditText email, password, name, confirm;
    Button register;
    TextView login;
    DatabaseHelper helper;
    ProgressBar signupProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupProgress = findViewById( R.id.signUpProgress );
        login = findViewById( R.id.tvLogin );
        register = findViewById(R.id.Register);
        email = findViewById(R.id.reditEmail);
        confirm = findViewById( R.id.reditConfirm );
        password = findViewById(R.id.reditPassword);
        name= findViewById(R.id.reditName);
        signupProgress.setVisibility(View.GONE);

        helper = new DatabaseHelper(this);

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
            }
        } );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String error = "false";

                if (name.getText().toString().isEmpty()) {
                    name.setError( "Please enter your first name" );
                    name.requestFocus();
                    error = "true";
                    return;
                }

                if (email.getText().toString().isEmpty()) {
                    email.setError( "Please enter your email" );
                    email.requestFocus();
                    error = "true";
                    return;
                }

                if (password.getText().toString().isEmpty()) {
                    password.setError( "Enter a password" );
                    password.requestFocus();
                    error = "true";
                    return;
                }

                if (password.getText().toString().length() < 6) {
                    password.setError( "Minimum password length is 6 character." );
                    password.requestFocus();
                    error = "true";
                    return;
                }

                if (confirm.getText().toString().isEmpty()) {
                    confirm.setError( "Confirm your password." );
                    confirm.requestFocus();
                    error = "true";
                    return;
                }

                if (!confirm.getText().toString().equals( password.getText().toString() )) {
                    confirm.setError( "Passwords do not match." );
                    confirm.requestFocus();
                    error = "true";
                    return;
                }

                if (error.equals( "false" )) {
                    signupProgress.setVisibility(View.VISIBLE);
                    User user = new User();
                    user.setName( name.getText().toString() );
                    user.setEmail( email.getText().toString() );
                    user.setPassword( password.getText().toString() );

                    boolean added = helper.addUser( user );
                    if (added) {
                        Toast.makeText( getApplicationContext(), "User Registered.", Snackbar.LENGTH_LONG ).show();
                        startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                        name.setText( "" );
                        email.setText( "" );
                        password.setText( "" );
                        confirm.setText( "" );
                        signupProgress.setVisibility(View.GONE);
                    } else {
                        Toast.makeText( getApplicationContext(), "Unable to register.", Snackbar.LENGTH_LONG ).show();
                        signupProgress.setVisibility(View.GONE);
                    }

                }
            }
        });

    }
}
