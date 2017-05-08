package com.endava.androidamweek.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.endava.androidamweek.R;
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
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EmailPasswordActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = "TAG";
    SignInButton signInButton;
    Button signOutButton;
    TextView statusTextView, name, email;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SING_IN = 9001;
    GoogleSignInResult result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        statusTextView = (TextView) findViewById(R.id.statusTextView);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        checkIfUserIsSignIn();
    }

    private void checkIfUserIsSignIn() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.i(TAG, "Got cached sign-in");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:

                signIn();

                break;
            case R.id.signOutButton:
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
            statusTextView.setText("Signed in");
            name.setText("Hello , " + account.getDisplayName());
            email.setText(account.getEmail());
            checkIfUserExist(account);
        }
    }

    private void checkIfUserExist(GoogleSignInAccount account) {
        Boolean flag = false;
        for (int i = 0; i < Database.getInstance().getUsers().size(); i++) {
            if (Database.getInstance().getUsers().get(i).getId().equals(account.getId()))
                flag = true;
        }
        if (flag == false)
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
        user.setTraining(trainings);
        newUser.setValue(user);

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                statusTextView.setText("Signed out");
                name.setText("Name");
                email.setText("Email");
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SING_IN);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}