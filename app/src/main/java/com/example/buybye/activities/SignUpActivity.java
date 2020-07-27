package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.LoginStatusListener;
import com.example.buybye.listeners.SignUpStatusListener;
import com.example.buybye.listeners.UserProfileStatusListener;

public class SignUpActivity extends AppCompatActivity implements LoginStatusListener, SignUpStatusListener, UserProfileStatusListener {
    private User user;
    // Database methods:
    private UserDatabaseAccessor userDatabaseAccessor;
    // UI components:
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText password2;
    private TextView passdiffwarn;
    private Button registerBtn;
    // alert progress dialog:
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.userDatabaseAccessor = new UserDatabaseAccessor();

        this.name = findViewById(R.id.regNameEditText);
        this.phoneNumber = findViewById(R.id.regPhoneNum);
        this.email = findViewById(R.id.regEmailEditText);
        this.password = findViewById(R.id.regPassword);
        this.password2 = findViewById(R.id.reRegPassword2);
        this.passdiffwarn = findViewById(R.id.passdiffwarn);
        this.passdiffwarn.setVisibility(View.INVISIBLE);
        this.registerBtn = findViewById(R.id.regButton);
    }
    private boolean verifyFields() {
        // initialize the user object to store:
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        final String password = this.password.getText().toString();
        String password2 = this.password2.getText().toString();
        String phoneNum = this.phoneNumber.getText().toString();
        if (email.length() == 0){
            Toast.makeText(getApplicationContext(),
                    "Email cannot be empty", Toast.LENGTH_LONG).show();
            this.passdiffwarn.setVisibility(View.INVISIBLE);
            return false;
        }
        // check to be non-empty
        if (name.length() == 0){
            Toast.makeText(getApplicationContext(),
                    "User name cannot be empty", Toast.LENGTH_LONG).show();
            this.passdiffwarn.setVisibility(View.INVISIBLE);
            return false;
        }

        if (phoneNum.length() == 0){
            Toast.makeText(getApplicationContext(),
                    "Phone number cannot be empty", Toast.LENGTH_LONG).show();
            this.passdiffwarn.setVisibility(View.INVISIBLE);
            return false;
        }

        // the length of the password must be greater than 6!
        if (password.length() <= 6 || password2.length() <= 6) {
            Toast.makeText(getApplicationContext(),
                    "Length of password must be greater than 6!", Toast.LENGTH_LONG).show();
            this.passdiffwarn.setVisibility(View.INVISIBLE);
            return false;
        }
        // phone number is not empty

        // the two password fields must be the same:
        if (password.compareTo(password2) != 0) {
            this.passdiffwarn.setVisibility(View.VISIBLE);
            return false;   // two passwords do not match
        }
        this.passdiffwarn.setVisibility(View.INVISIBLE);
        Log.v("1",password);
        Log.v("2",password2);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.registerBtn.setOnClickListener(view -> {
            if(!verifyFields()){
                return;
            }
            String passwordData = password.getText().toString();
            String nameData = name.getText().toString();
            String emailData = email.getText().toString();
            String phoneNumberData = phoneNumber.getText().toString();
            user = new User(emailData,nameData, phoneNumberData, passwordData);
            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setContentView(R.layout.custom_progress_bar);
            progressDialog.show();

            userDatabaseAccessor.registerUser(user, SignUpActivity.this);

        });

    }

    @Override
    public void onLoginSuccess() {
        // first dismiss the progress bar:
        this.progressDialog.dismiss();
        // initialize intent to go to the homepage:
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        Bundle bundle = new Bundle();
        // put the user object into the bundle, Profile activity can access directly:
        bundle.putSerializable("UserObject", this.user);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        // show success message here:
        Toast.makeText(getApplicationContext(),
                "logged in successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Login Failed, try again later.", Toast.LENGTH_SHORT).show();
        // go view the user profile:
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getApplicationContext(),
                "User created, logging in the user...", Toast.LENGTH_SHORT).show();
        this.userDatabaseAccessor.createUserProfile(this.user, this);
    }

    @Override
    public void onRegisterFailure() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Sign Up Failed, please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWeakPassword() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Password is too short, at least 7 digits.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserAlreadyExist() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Email is already used, please use another one.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmail() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Invalid email.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileStoreSuccess() {
        this.userDatabaseAccessor.loginUser(this.user, this);
    }

    @Override
    public void onProfileStoreFailure() {
        this.progressDialog.dismiss();
    }

    @Override
    public void onProfileRetrieveSuccess(User user) {

    }

    @Override
    public void onProfileRetrieveFailure() {
        this.progressDialog.dismiss();
    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {
        this.progressDialog.dismiss();
    }

    @Override
    public void onValidateSuccess() {

    }

    @Override
    public void onValidateFailure() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFailure() {

    }
}