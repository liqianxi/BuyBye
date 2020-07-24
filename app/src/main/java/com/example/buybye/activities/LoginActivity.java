package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.LoginStatusListener;
import com.example.buybye.listeners.UserProfileStatusListener;

public class LoginActivity extends AppCompatActivity implements UserProfileStatusListener, LoginStatusListener {
    private EditText password;
    private EditText email;
    private TextView loginWarn;
    private UserDatabaseAccessor userDatabaseAccessor;
    private ProgressDialog progressDialog;
    private Button confirmButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        this.progressDialog = new ProgressDialog(LoginActivity.this);
        this.progressDialog.setContentView(R.layout.custom_progress_bar);
        // if user already logged in, go to the profile activity
        if (this.userDatabaseAccessor.isLoggedin()) {
            progressDialog.show();
            userDatabaseAccessor.getUserProfile(this);
        }

        this.password = (EditText)findViewById(R.id.inputPassword);
        this.email = (EditText)findViewById(R.id.inputUserEmail);
        this.loginWarn = findViewById(R.id.loginWarning);
        this.signUpButton = (Button)findViewById(R.id.signUp);
        this.confirmButton = (Button)findViewById(R.id.confirmButton);
        this.loginWarn.setVisibility(View.INVISIBLE);



    }

    @Override
    protected void onStart() {
        super.onStart();


        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!verifyFields()) {
                    loginWarn.setVisibility(View.VISIBLE);
                    return;
                } else {
                    loginWarn.setVisibility(View.INVISIBLE);
                }
                progressDialog.show();
                String emailData = email.getText().toString();
                String passwordData = password.getText().toString();
                User user = new User();
                user.setEmail(emailData);
                user.setPassword(passwordData);
                userDatabaseAccessor.loginUser(user, LoginActivity.this);

            }
        });
        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean verifyFields() {
        // get the strings
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        return emailData.compareTo("") != 0 && passwordData.compareTo("") != 0;
    }
    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        Intent intent;

        intent = new Intent(getApplicationContext(), HomePageActivity.class);

        startActivity(intent);
        if (!LoginActivity.this.isFinishing() && progressDialog!=null) {
            this.progressDialog.dismiss();
        }
        finish();
    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {

    }

    @Override
    public void onLoginSuccess() {
        // go view the map:
        this.userDatabaseAccessor.getUserProfile( this);
        Toast.makeText(getApplicationContext(), "Login success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFailure() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Login Failed, try again later.", Toast.LENGTH_SHORT).show();
    }
}