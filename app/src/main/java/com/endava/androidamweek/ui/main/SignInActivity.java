package com.endava.androidamweek.ui.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.mail.MailSenderClass;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.data.model.User;
import com.endava.androidamweek.data.model.UserTraining;
import com.endava.androidamweek.ui.training.TrainingsFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SING_IN = 9001;

    @BindView(R.id.sign_out_button)
    Button signOutButton;

    @BindView(R.id.status)
    TextView statusView;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    GoogleApiClient googleApiClient;
    GoogleSignInResult result;
    private final String ACCOUNT_PREFERENCES = "accountPreferences";
    private final static String USER_ID = "userID";
    private static String USER_EMAIL = "email";
    private static String USER_NAME = "userName";
    SharedPreferences sharedPreferences;
    private View view;
    private AlertDialog dialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        sharedPreferences = getSharedPreferences(ACCOUNT_PREFERENCES, MODE_PRIVATE);

        showDialog();
    }

    private void showDialog() {
        view = getLayoutInflater().inflate(R.layout.activity_authentication, null);

        ButterKnife.bind(this, view);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignInActivity.this);

        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);


        String userName = sharedPreferences.getString(USER_NAME, "");
        String userEmail = sharedPreferences.getString(USER_EMAIL, "");

        if (!userName.equals("") && !userEmail.equals("")) {
            name.setText("Hello , " + userName);
            email.setText(userEmail);
            statusView.setText("Signed in");
        }

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SING_IN) {
            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            statusView.setText("Signed in");

            sharedPreferences.edit().putString(USER_ID, account.getId()).apply();
            sharedPreferences.edit().putString(USER_NAME, account.getDisplayName()).apply();
            sharedPreferences.edit().putString(USER_EMAIL, account.getEmail()).apply();

            checkIfUserExist(account);
        }
    }

    private void checkIfUserExist(GoogleSignInAccount account) {
        Boolean flag = false;
        for (int i = 0; i < LocalDatabase.getInstance().getUsers().size(); i++) {
            if (LocalDatabase.getInstance().getUsers().get(i).getId().equals(account.getId()))
                flag = true;
        }
        if (!flag)
            createUser(account);

        finish();

    }

    private void createUser(GoogleSignInAccount account) {
        DatabaseReference newUser = Database.getInstance().getUsersReferece().push();
        User user = new User();
        user.setEmail(account.getEmail());
        user.setFirstName(account.getGivenName());
        user.setLastName(account.getFamilyName());
        user.setId(account.getId());

        ArrayList<UserTraining> trainings = new ArrayList<>();
        UserTraining userTraining = new UserTraining();
        userTraining.setTrainingId(0);
        trainings.add(userTraining);
        user.setTrainings(trainings);
        newUser.setValue(user);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                statusView.setText("Signed out");
                name.setText("Name");
                email.setText("Email");

                sharedPreferences.edit().putString(USER_ID, "").apply();
                sharedPreferences.edit().putString(USER_NAME, "").apply();
                sharedPreferences.edit().putString(USER_EMAIL, "").apply();

                finish();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SING_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}