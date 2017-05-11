package com.endava.androidamweek.ui.quizz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.model.Answer;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.ui.main.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.database.DatabaseReference;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

public class ContentQuizzActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.codeview)
    CodeView codeView;

    @BindView(R.id.question_box)
    TextView questionBox;

    @BindView(R.id.answer_box)
    TextView answerBox;


    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private Quizz quizz;
    private GoogleApiClient mGoogleApiClient;
    private String userId;
    private Boolean UserIsLoggedIn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.toolbar_title_quizz_data);

        quizz = (Quizz) getIntent().getSerializableExtra(QuizzActivity.QUIZZ);

        codeView.setCode(quizz.getCodeSnippet(), "java");

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (answerBox.getText().toString().equals("")) {
            Toast.makeText(ContentQuizzActivity.this, "Please enter the answer", Toast.LENGTH_SHORT).show();
            return;
        }

        checkIfUserIsSignIn();

        if (UserIsLoggedIn) {
            createAnswer();
            UserIsLoggedIn = false;
            answerBox.setText("");
        } else {
            Toast.makeText(ContentQuizzActivity.this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfUserIsSignIn() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            UserIsLoggedIn = true;
            userId = account.getId();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_quizz_content;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void createAnswer() {
        DatabaseReference answerReference = Database.getInstance().getQuizzesReferece().child(quizz.getFirebaseFieldName()).child("userAnswer");
        DatabaseReference newAnswer = answerReference.push();

        Date currentDate = Calendar.getInstance().getTime();

        Format formatter = new SimpleDateFormat("yyyy.MM.dd");
        String dateString = formatter.format(currentDate);
        formatter = new SimpleDateFormat("HH:mm:ss");
        String timeString = formatter.format(currentDate);

        Answer answer = new Answer();
        answer.setAnswer(answerBox.getText().toString());
        answer.setUserId(userId);
        answer.setDate(dateString);
        answer.setTime(timeString);

        newAnswer.setValue(answer);

        Toast.makeText(this, "Your answer has sent successfully", Toast.LENGTH_SHORT).show();
    }
}
