package com.example.user.appproject;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView splash = (TextView) findViewById(R.id.WelcomeTxt);
        ImageView splash_image = (ImageView) findViewById(R.id.imageView);  //retrieve design field by id's
        TextView version = (TextView) findViewById(R.id.versiontxt);

        Animation fade1 = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation fade2 = AnimationUtils.loadAnimation(this,R.anim.fade_in2); //load animation files from anim directory
        Animation spinIn = AnimationUtils.loadAnimation(this,R.anim.anim);

        fade2.setAnimationListener(new Animation.AnimationListener(){
            public void onAnimationEnd (Animation animation){
                Intent intent = new Intent(MainActivity.this,UserRegister.class);
                startActivity(intent);
            }

            public void onAnimationStart(Animation animation){

            }

            public void onAnimationRepeat(Animation animation){

            }
        });

        splash.startAnimation(fade1);
        splash_image.startAnimation(spinIn);
        version.startAnimation(fade2);
    }

    public void onPause(){
        super.onPause();

        TextView splash = (TextView) findViewById(R.id.WelcomeTxt);
        ImageView splash_image = (ImageView) findViewById(R.id.imageView);
        TextView version = (TextView) findViewById(R.id.versiontxt);

        splash.clearAnimation();
        splash_image.clearAnimation();
        version.clearAnimation();
    }
}