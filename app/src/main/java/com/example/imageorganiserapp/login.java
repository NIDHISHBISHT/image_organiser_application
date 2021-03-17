package com.example.imageorganiserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends AppCompatActivity {
    Button signup,gobtn;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        gobtn = findViewById(R.id.go);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,signup.class);
                startActivity(intent);
            }
        });
        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUserName()|!validatepassword()){
                    return;
                }
                else{
                    isUser();
                }
            }
        });
    }
    private Boolean validateUserName() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field Cannot be empty");
            return false;
        }
        else if(val.length()>=10){
            username.setError("Username too long");
            return false;
        }
        else {
            username.setError(null);
            return true;
        }
    }
    private Boolean validatepassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field Cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }



    private void isUser() {
        final String UserEnteredUserName = username.getEditText().getText().toString().trim();
        final String UserEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.orderByChild("username").equalTo(UserEnteredUserName);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(UserEnteredUserName).child("password").getValue(String.class);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if(Objects.equals(passwordFromDB, UserEnteredPassword)){
                            password.setError(null);
                            password.setErrorEnabled(false);
                            String emailFromDB = dataSnapshot.child(UserEnteredUserName).child("email").getValue(String.class);
                            String fullnameFromDB = dataSnapshot.child(UserEnteredUserName).child("fullname").getValue(String.class);
                            String usernameFromDB = dataSnapshot.child(UserEnteredUserName).child("username").getValue(String.class);
                            String phonenumberFromDB = dataSnapshot.child(UserEnteredUserName).child("phonenumber").getValue(String.class);

                           /* Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                            intent.putExtra("fullname",fullnameFromDB);
                            intent.putExtra("username",usernameFromDB);
                            intent.putExtra("phonenumber",phonenumberFromDB);
                            intent.putExtra("email",emailFromDB);
                            intent.putExtra("password",passwordFromDB);
                            startActivity(intent);*/
                        }
                        else {
                            password.setError("Wrong Password");
                            password.requestFocus();
                        }
                    }
                }
                else{
                    username.setError("No Such User");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}