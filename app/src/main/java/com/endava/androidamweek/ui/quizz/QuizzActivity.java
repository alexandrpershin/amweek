package com.endava.androidamweek.ui.quizz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.callbacks.QuizzCallback;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.ui.main.BaseActivity;

import butterknife.BindView;

public class QuizzActivity extends BaseActivity implements QuizzCallback {

    public static final String QUIZZ = "quizz";
    private QuizzAdapter quizzAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.quizz_recycler_view)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setTitle(R.string.toolbar_title_quizzes);

        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        quizzAdapter = new QuizzAdapter(this);

        recyclerView.setAdapter(quizzAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quizz;
    }

    @Override
    public void OnClick(Quizz quizz) {
        Intent intent = new Intent(getApplication(), ContentQuizzActivity.class);
        intent.putExtra(QUIZZ, quizz);
        startActivity(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            quizzAdapter.updateList();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("UpdateData"));
    }
}
