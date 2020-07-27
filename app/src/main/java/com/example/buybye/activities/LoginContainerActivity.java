package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.example.buybye.listeners.SignUpStatusListener;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class LoginContainerActivity extends AppCompatActivity implements UserProfileStatusListener, LoginStatusListener, SignUpStatusListener {

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
    private ProgressDialog VerifyWaitingDialog;
    private Button MoveForward;
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

    private Map<Integer,CardView> pageMap = new HashMap<>();
    private String phoneNum;
    private String password1;
    private String password2;
    private User tempUser;


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
        VerifyWaitingDialog = new ProgressDialog(LoginContainerActivity.this);
        VerifyWaitingDialog.setContentView(R.layout.verify_email_dialog);
        MoveForward = findViewById(R.id.MoveForward);
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
                VerifyWaitingDialog.show();
                tempUser = new User();
                tempUser.setEmail(signUpEmail);
                tempUser.setPassword(signUpName);
                userDatabaseAccessor.registerUser(tempUser, LoginContainerActivity.this);
            }});
        MoveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDatabaseAccessor.isValidated(LoginContainerActivity.this);
            }
        });
        password1 = SignUpEnterPassword.getText().toString();
        password2 = SignUpEnterPasswordAgain.getText().toString();

        FinishSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password1.compareTo(password2) == 0) {
                    tempUser.setPhoneNumber(phoneNum);
                    tempUser.setPassword(password1);
                    userDatabaseAccessor.updateUserProfile(tempUser,LoginContainerActivity.this);
                }else{
                    return;
                }
            }
        });







    }

    @Override
    protected void onStart() {
        super.onStart();
        display_page(1);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDatabaseAccessor.deleteUser(LoginContainerActivity.this);
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
        //the user has been successful updated
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        Bundle bundle = new Bundle();
        // put the user object into the bundle, Profile activity can access directly:
        bundle.putSerializable("UserObject", tempUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        // show success message here:
        Toast.makeText(getApplicationContext(),
                "logged in successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileUpdateFailure() {

    }

    @Override
    public void onValidateSuccess() {
        //the account has been validated
        Log.v("move forward", "Now move to the third page");
        Toast.makeText(getApplicationContext(),"Validate success", Toast.LENGTH_SHORT).show();
        VerifyWaitingDialog.dismiss();
        display_page(3);
        phoneNum = SignUpEnterPhone.getText().toString();
        NextPageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_page(4);
            }
        });
    }

    @Override
    public void onValidateFailure() {
        Toast.makeText(getApplicationContext(),"Validate failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteSuccess() {
        Log.v("Delete","Delete Success");
    }

    @Override
    public void onDeleteFailure() {
        Log.v("Delete","Delete Failure");
    }

    @Override
    public void onRegisterSuccess() {
        FirebaseUser user = userDatabaseAccessor.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("send", "Email sent.");
                            Toast.makeText(getApplicationContext(),"Please check your email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRegisterFailure() {

    }

    @Override
    public void onWeakPassword() {

    }

    @Override
    public void onUserAlreadyExist() {

    }

    @Override
    public void onInvalidEmail() {

    }
}