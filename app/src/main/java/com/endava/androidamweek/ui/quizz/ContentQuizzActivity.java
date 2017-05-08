package com.endava.androidamweek.ui.quizz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.endava.androidamweek.R;
import com.endava.androidamweek.ui.main.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

public class ContentQuizzActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.codeview)
    CodeView codeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.toolbar_title_quizz_data);

        // auto language recognition - not working
        // codeView.setCode(getString(R.string.singl));

        codeView.setCode(getString(R.string.singl), "java");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_quizz_content;
    }
}
