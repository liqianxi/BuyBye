package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class LoginContainerActivity extends AppCompatActivity implements UserProfileStatusListener, LoginStatusListener {

    private UserDatabaseAccessor userDatabaseAccessor;

    //widgets for page1
    private ProgressDialog progressDialog;
    private TextView loginWarn;
    private TextView EmailTitle;
    private TextView PasswordTitle;
    private EditText LoginEnterEmail;
    private EditText EnterPassword;
    private TextView ForgetPassword;
    private TextView SignUpText;
    private Button LoginButton;
    private CardView page1;
    //widgets for page2
    private TextView SignUpWarning;
    private EditText SignUpEnterEmail;
    private TextView SignUpEmailTitle;
    private TextView SignUpNameTitle;
    private EditText SignUpEnterName;
    private Button VerifyButton;
    private Button NextPageButton;
    private CardView page2;
    //widgets for page3
    private EditText SignUpEnterPhone;
    private TextView SignUpPhoneNumber;
    private Button NextPageButton2;
    private CardView page3;
    //widgets for page4
    private EditText SignUpEnterPassword;
    private TextView SignUpPasswordTitle;
    private EditText SignUpEnterPasswordAgain;
    private TextView SignUpPasswordTitleAgain;
    private Button FinishSignUpButton;
    private CardView page4;

    Map<Integer,CardView> pageMap = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_container);
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        this.progressDialog = new ProgressDialog(LoginContainerActivity.this);
        this.progressDialog.setContentView(R.layout.custom_progress_bar);
        if (this.userDatabaseAccessor.isLoggedin()) {
            progressDialog.show();
            userDatabaseAccessor.getUserProfile(this);
        }
        this.loginWarn = findViewById(R.id.loginWarning);
        EmailTitle = findViewById(R.id.EmailTitle);
        PasswordTitle = findViewById(R.id.PasswordTitle);
        LoginEnterEmail = findViewById(R.id.LoginEnterEmail);
        EnterPassword = findViewById(R.id.EnterPassword);
        ForgetPassword = findViewById(R.id.ForgetPassword);
        SignUpText = findViewById(R.id.SignUpText);
        LoginButton = findViewById(R.id.LoginButton);
        page1 = findViewById(R.id.page1);

        SignUpEnterName = findViewById(R.id.SignUpEnterName);
        SignUpEmailTitle = findViewById(R.id.SignUpEmailTitle);
        SignUpEnterEmail = findViewById(R.id.SignUpEnterEmail);
        SignUpNameTitle = findViewById(R.id.SignUpNameTitle);
        VerifyButton = findViewById(R.id.VerifyButton);
        NextPageButton = findViewById(R.id.NextPageButton);
        SignUpWarning = findViewById(R.id.SignUpWarning);
        page2 = findViewById(R.id.page2);

        SignUpEnterPassword = findViewById(R.id.SignUpEnterPassword);
        SignUpEnterPasswordAgain = findViewById(R.id.SignUpEnterPasswordAgain);
        SignUpPasswordTitle = findViewById(R.id.SignUpPasswordTitle);
        SignUpPasswordTitleAgain = findViewById(R.id.SignUpPasswordTitleAgain);
        SignUpEnterPhone = findViewById(R.id.SignUpEnterPhone);
        SignUpPhoneNumber = findViewById(R.id.SignUpPhoneNumber);
        FinishSignUpButton = findViewById(R.id.FinishSignUpButton);
        NextPageButton2 = findViewById(R.id.NextPageButton2);
        page3 = findViewById(R.id.page3);
        page4 = findViewById(R.id.page4);

        populateHashMap(pageMap);

        loginWarn.setVisibility(View.INVISIBLE);
        SignUpWarning.setVisibility(View.INVISIBLE);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailData = LoginEnterEmail.getText().toString();
                String passwordData = EnterPassword.getText().toString();
                if (!verifyFields(emailData) && !verifyFields(passwordData)) {
                    loginWarn.setVisibility(View.VISIBLE);
                    return;
                } else {
                    loginWarn.setVisibility(View.INVISIBLE);
                }
                progressDialog.show();


                User user = new User();
                user.setEmail(emailData);
                user.setPassword(passwordData);
                userDatabaseAccessor.loginUser(user, LoginContainerActivity.this);
            }
        });
        //switch to sign up part
        SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_page(2);

            }
        });

        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signUpEmail = SignUpEnterEmail.getText().toString();
                String signUpName = SignUpEnterName.getText().toString();
                if (!verifyFields(signUpEmail) && !verifyFields(signUpName)) {
                    SignUpWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    SignUpWarning.setVisibility(View.INVISIBLE);
                }
            }
        });






    }

    @Override
    protected void onStart() {
        super.onStart();
        display_page(1);



    }
    private boolean verifyFields(String data) {

        return data.compareTo("") != 0;
    }
    private void populateHashMap(Map<Integer,CardView> pageMap){
        pageMap.put(1,page1);
        pageMap.put(2,page2);
        pageMap.put(3,page3);
        pageMap.put(4,page4);

    }
    private void display_page(int num){
        for (int i=1;i<5;i++){
            if(i!=num){
                pageMap.get(i).setVisibility(View.INVISIBLE);
            }else{
                pageMap.get(i).setVisibility(View.VISIBLE);
            }
        }
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
        if (!LoginContainerActivity.this.isFinishing() && progressDialog!=null) {
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
}