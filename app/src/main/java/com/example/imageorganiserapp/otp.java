package com.example.imageorganiserapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class otp extends AppCompatActivity {

    FirebaseAuth auth;
    EditText e1, e2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verification_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.editTextPhone);
        e2 = (EditText) findViewById(R.id.textView);
        auth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_code = s;
                /*Toast.makeText(getApplicationContext(), "CODE SENT TO YOUR NUMBER", Toast.LENGTH_SHORT).show();*/
            }
        };
    }

    public void send_OTP(View v) {
        String number = e1.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallback);
    }

    public void signInWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "NUMBER VERIFIED SUCESSFULLY", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, login.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    public void verify(View v) {
        String input_code = e2.getText().toString();
        verifyPhoneNumber(verification_code, input_code);

    }

    public void verifyPhoneNumber(String verifyCode, String input_code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, input_code);
        signInWithPhone(credential);
    }
}
