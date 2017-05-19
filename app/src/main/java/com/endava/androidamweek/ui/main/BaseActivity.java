package com.endava.androidamweek.ui.main;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.endava.androidamweek.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindView();
    }

    protected abstract int getLayoutId();

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
