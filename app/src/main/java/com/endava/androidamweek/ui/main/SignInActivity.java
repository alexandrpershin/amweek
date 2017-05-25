package com.endava.androidamweek.ui.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.User;
import com.endava.androidamweek.data.model.UserTraining;
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
import butterknife.Unbinder;

public class SignInActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private  final int RC_SING_IN = 9001;
    private final static String USER_ID = "userID";
    private static String USER_EMAIL = "email";
    private static String USER_NAME = "userName";
    private final String ACCOUNT_PREFERENCES = "accountPreferences";
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.sign_out_button)
    Button signOutButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinator)
    ConstraintLayout layout;
    GoogleApiClient googleApiClient;
    GoogleSignInResult result;
    SharedPreferences sharedPreferences;

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

        if (!sharedPreferences.getString(USER_ID, "").equals("")) {
            signInButton.setVisibility(View.INVISIBLE);
            signOutButton.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.INVISIBLE);
        }
        signInButton.setOnClickListener(new SignInClickListener());
        signOutButton.setOnClickListener(new SignOutClickListener());
        toolbar.setTitle(R.string.sing_in);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SING_IN) {
            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i("Signin","succes");

            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            sharedPreferences.edit().putString(USER_ID, account.getId()).apply();
            sharedPreferences.edit().putString(USER_NAME, account.getDisplayName()).apply();
            sharedPreferences.edit().putString(USER_EMAIL, account.getEmail()).apply();

            checkIfUserExist(account);

            onBackPressed();
        } else {
            Snackbar.make(layout, "An error has occured, please try again", Snackbar.LENGTH_LONG);
            Log.i("SignInActivity","result is unseccess");
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

                sharedPreferences.edit().putString(USER_ID, "").apply();
                sharedPreferences.edit().putString(USER_NAME, "").apply();
                sharedPreferences.edit().putString(USER_EMAIL, "").apply();
                signInButton.setVisibility(View.VISIBLE);
                signOutButton.setVisibility(View.INVISIBLE);
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

    private class SignInClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.i("Signin","clickedButton");
            signIn();


        }
    }

    private class SignOutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            signOut();


        }
    }
}