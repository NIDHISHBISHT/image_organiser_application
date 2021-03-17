package com.example.imageorganiserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {

    //variables
    Animation topanim, bottomanim;
    TextView slogan,slogan1;

    private static int SPLASH = 5500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animations);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animations);
        //hooks
        slogan = findViewById(R.id.textView);
        slogan1 = findViewById(R.id.textView1);

        slogan.setAnimation(bottomanim);
        slogan1.setAnimation(topanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashscreen.this, login.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(slogan,"welcome");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(splashscreen.this,pairs);
                    startActivity(intent,options.toBundle());
                }

            }
        },SPLASH);

    }
}