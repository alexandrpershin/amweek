package com.endava.androidamweek.ui.quizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.mail.MailSenderClass;
import com.endava.androidamweek.data.model.Answer;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.ui.main.BaseActivity;
import com.endava.androidamweek.ui.main.SignInActivity;
import com.google.firebase.database.DatabaseReference;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

public class ContentQuizzActivity extends BaseActivity implements View.OnClickListener {

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

    private String userId;
    private String timeString;
    private String dateString;

    private SharedPreferences sharedPreferences;

    private static String USER_ID = "userID";
    private String ACCOUNT_PREFERENCES = "accountPreferences";

    private static String USER_EMAIL = "email";
    private static String USER_NAME = "userName";
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.toolbar_title_quizz_data);

        quizz = (Quizz) getIntent().getSerializableExtra(QuizzActivity.QUIZZ);

        if (quizz.getCodeSnippet().equals("")){
            codeView.setVisibility(View.GONE);
        }

        questionBox.setText(quizz.getConditions());
        codeView.setCode(quizz.getCodeSnippet(), "java");



        sharedPreferences = getSharedPreferences(ACCOUNT_PREFERENCES, MODE_PRIVATE);
        userId = sharedPreferences.getString(USER_ID, "");

        btnSubmit.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        userId = sharedPreferences.getString(USER_ID, "");

        if (getConnectivityStatus(getApplication()) == 0) {
            Toast.makeText(ContentQuizzActivity.this, "No internet  connection", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId.equals("")) {
            Toast.makeText(ContentQuizzActivity.this, "Please sign in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ContentQuizzActivity.this, SignInActivity.class));
            return;
        }

        if (answerBox.getText().toString().equals("")) {
            Toast.makeText(ContentQuizzActivity.this, "Please enter the answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userId.equals("")) {
            createAnswer();
            sendEmail();
            answerBox.setText("");

            Log.i("QuizzContetn","finished with finish()");
            finish();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_quizz_content;
    }

    private void sendEmail() {

        String title = quizz.getTitle();
        String answer = answerBox.getText().toString();
        final String email = sharedPreferences.getString(USER_EMAIL, "");
        final String name = sharedPreferences.getString(USER_NAME, "");

        final String body = "Quizz title : " + title + "\n\n Answer:  \n\n "
                + answer + "\n\n" + name + "\n" + email + "\n\n" + timeString + "\n" + dateString;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new MailSenderClass("endava.amweek@gmail.com", "endavaamweek")
                            .sendMail("Quizz answer", body.toString(), "name",
                                    "Alexandr-pershin@mail.ru", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void createAnswer() {
        DatabaseReference answerReference = Database.getInstance().getQuizzesReferece().child(quizz.getFirebaseFieldName()).child("userAnswer");
        DatabaseReference newAnswer = answerReference.push();

        Date currentDate = Calendar.getInstance().getTime();

        Format formatter = new SimpleDateFormat("yyyy.MM.dd");
        dateString = formatter.format(currentDate);
        formatter = new SimpleDateFormat("HH:mm:ss");
        timeString = formatter.format(currentDate);

        Answer answer = new Answer();
        answer.setAnswer(answerBox.getText().toString());
        answer.setUserId(userId);
        answer.setDate(dateString);
        answer.setTime(timeString);

        newAnswer.setValue(answer);
        Log.i("QuizzContetn","answer has sent");
        Toast.makeText(this, "Your answer has sent successfully", Toast.LENGTH_SHORT).show();
    }


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

}
