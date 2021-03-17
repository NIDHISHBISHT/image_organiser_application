package com.example.imageorganiserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    Button next, back;
    TextInputLayout fullname, username, email, password, phonenumber;
    FirebaseDatabase rootNode;
    DatabaseReference refrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        fullname = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName()|!validateUserName()|!validateemail()|!validatephonenumber()|!validatephonenumber()|!validatepassword()) {
                    return;
                }

                Intent intent = new Intent(signup.this, otp.class);
                startActivity(intent);
                rootNode = FirebaseDatabase.getInstance();
                refrence = rootNode.getReference("users");

                //get all the values
                String rname = fullname.getEditText().getText().toString();
                String rusername = username.getEditText().getText().toString();
                String remail = email.getEditText().getText().toString();
                String rphonenumber = phonenumber.getEditText().getText().toString();
                String rpassword = password.getEditText().getText().toString();

                UserHelperClass helperclass = new UserHelperClass(rname, rusername, remail, rphonenumber, rpassword);
                refrence.child(rusername).setValue(helperclass);


                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "Please Verify your Phone Number", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(signup.this, login.class);
                startActivity(intent1);
            }
        });

    }

    private Boolean validateName() {
        String val = fullname.getEditText().getText().toString();
        if (val.isEmpty()) {
            fullname.setError("Field Cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            return true;
        }
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
    private Boolean validateemail() {
        String val = email.getEditText().getText().toString();
        if (val.isEmpty()) {
            email.setError("Field Cannot be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private Boolean validatephonenumber() {
        String val = phonenumber.getEditText().getText().toString();
        if (val.isEmpty()) {
            phonenumber.setError("Field Cannot be empty");
            return false;
        } else {
            phonenumber.setError(null);
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

}