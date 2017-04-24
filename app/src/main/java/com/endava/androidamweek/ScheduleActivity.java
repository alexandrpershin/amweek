package com.endava.androidamweek;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.endava.androidamweek.demo.DemoAdapter;
import com.endava.androidamweek.demo.DemoItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleActivity extends AppCompatActivity implements OnTabSelectListener {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ButterKnife.bind(this);
        bottomBar.setOnTabSelectListener(this);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DemoAdapter adapter = new DemoAdapter(this.getDemoList());
        recyclerView.setAdapter(adapter);
    }

    private List<DemoItem> getDemoList() {
        List<DemoItem> list = new ArrayList<>();

        list.add(new DemoItem("Test 1"));
        list.add(new DemoItem("Test asdasd"));
        list.add(new DemoItem("Test44 asd"));
        list.add(new DemoItem("Test 12rqfeg"));
        list.add(new DemoItem("Test 92asd sad"));
        list.add(new DemoItem("Test grthgtfr"));
        list.add(new DemoItem("asd sa safasf"));

        return list;
    }

    private void replaceFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, new ScheduleFragment())
                .commit();
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.monday:
                break;
            case R.id.tuesday:
                break;
            case R.id.wednesday:
                break;
            case R.id.thursday:
                break;
            case R.id.friday:
                break;
            default:
                break;
        }
    }

}