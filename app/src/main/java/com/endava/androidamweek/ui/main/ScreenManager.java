package com.endava.androidamweek.ui.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.endava.androidamweek.R;

class ScreenManager {

    private final static String DAY_OF_WEEK = "dayOfWeek";
    private static final String ADAPTER_POSITION = "adapterPosition";
    private static ScreenManager instance;
    private Activity context;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = (Activity) context;
    }

    public void replaceFragment(Fragment fragment, int dayOfWeek, int adapterPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(DAY_OF_WEEK, dayOfWeek);
        bundle.putInt(ADAPTER_POSITION,adapterPosition);
        fragment.setArguments(bundle);

        context.getFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit();
    }
}
