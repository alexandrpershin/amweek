package com.endava.androidamweek.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.ui.quizz.QuizzActivity;
import com.endava.androidamweek.ui.training.TrainingsFragment;
import com.endava.androidamweek.ui.training.UpdateDataService;
import com.endava.androidamweek.utils.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.mainContent)
    CoordinatorLayout view;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(getApplicationContext(), UpdateDataService.class);
        startService(intent);

        ScreenManager.getInstance().setContext(this);

        ButterKnife.bind(this);
        toolbar.setTitle(R.string.toolbar_title_shedule);

        bottomBar.setOnTabSelectListener(this);
        setSupportActionBar(toolbar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScheduleActivity.this, QuizzActivity.class));
            }
        });

        setCurrentDay();

        Log.i("ScheduleActivity", "created");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    private void setCurrentDay() {
        switch (Utils.getWeekDay()) {
            case 1:
                bottomBar.setDefaultTab(R.id.monday);
                break;
            case 2:
                bottomBar.setDefaultTab(R.id.tuesday);
                break;
            case 3:
                bottomBar.setDefaultTab(R.id.wednesday);
                break;
            case 4:
                bottomBar.setDefaultTab(R.id.thursday);
                break;
            case 5:
                bottomBar.setDefaultTab(R.id.friday);
                break;
        }
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.monday:
                ScreenManager.getInstance().replaceFragment(new TrainingsFragment(), 1);
                break;
            case R.id.tuesday:
                ScreenManager.getInstance().replaceFragment(new TrainingsFragment(), 2);
                break;
            case R.id.wednesday:
                ScreenManager.getInstance().replaceFragment(new TrainingsFragment(), 3);
                break;
            case R.id.thursday:
                ScreenManager.getInstance().replaceFragment(new TrainingsFragment(), 4);
                break;
            case R.id.friday:
                ScreenManager.getInstance().replaceFragment(new TrainingsFragment(), 5);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
        Log.i("ScheduleActivity", "Destoyed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ScheduleActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ScheduleActivity", "onPause");
    }
}

