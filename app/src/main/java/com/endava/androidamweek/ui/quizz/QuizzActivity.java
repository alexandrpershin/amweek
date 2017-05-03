package com.endava.androidamweek.ui.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.endava.androidamweek.R;
import com.endava.androidamweek.ui.main.BaseActivity;

import butterknife.BindView;

public class QuizzActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.quizz_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.testLinear)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setTitle(R.string.toolbar_title_quizzes);

        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplication(), ContentQuizzActivity.class));
//
//            }
//        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), ContentQuizzActivity.class));
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quizz;
    }
}
