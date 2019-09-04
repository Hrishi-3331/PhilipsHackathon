package com.hackathon.philips.dare2complete.philips;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ImageView spash_image;
    private LinearLayout anim_layout;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spash_image = (ImageView)findViewById(R.id.splash_image);
        anim_layout = (LinearLayout)findViewById(R.id.splash_anim);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        animate();
    }

    private void animate() {

        //Animation code here
        spash_image.animate().alpha(1).setDuration(1200).start();
        anim_layout.animate().scaleX(300).setDuration(1200).start();

        //Transition Code

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (mUser == null){
                    intent = new Intent(SplashActivity.this, Authentication.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1500);

    }
}
