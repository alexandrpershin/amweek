package com.endava.androidamweek.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1000;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout container;

    @BindView(R.id.appTitle)
    TextView appTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/10199.ttf");
        appTitle.setTypeface(typeface);

        container.startShimmerAnimation();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, ScheduleActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
