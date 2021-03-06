package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.LoginStatusListener;
import com.example.buybye.listeners.SignUpStatusListener;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private CardView page2;
    private ProgressDialog VerifyWaitingDialog;
    //widgets for page3
    private EditText SignUpEnterPhone;
    private TextView SignUpPhoneNumber;
    private TextView SignUpEnterLocationTitle;
    private Spinner ProvinceSpinner;
    private Spinner CitySpinner;
    private CardView page3;
    private ArrayList<String> provinceNames = new ArrayList<>();
    private ArrayList<String> cityNames = new ArrayList<>();
    private String selectedProvince;
    private String selectedCity;
    private HashMap<String, Integer> ArrayMap = new HashMap<>();
    //widgets for page4
    private EditText SignUpEnterPassword;
    private TextView SignUpPasswordTitle;
    private EditText SignUpEnterPasswordAgain;
    private TextView SignUpPasswordTitleAgain;
    private Button FinishSignUpButton;


    private Map<Integer,CardView> pageMap = new HashMap<>();
    private String phoneNum;
    private String password1;
    private String password2;
    private User tempUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar

        populateMap();


        setContentView(R.layout.activity_login_container);
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        this.progressDialog = new ProgressDialog(LoginContainerActivity.this);
        this.progressDialog.setContentView(R.layout.custom_progress_bar);
        ActivityCollector.addActivity(this);


        bindViews();
        provinceNames.addAll(Arrays.asList(getResources().getStringArray(R.array.Canada)));
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,provinceNames);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ProvinceSpinner.setAdapter(provinceAdapter);


        ProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedProvince = provinceNames.get(i);
                ArrayList<String> tempList = new ArrayList<>();
                tempList.addAll(Arrays.asList(getResources().getStringArray(ArrayMap.get(selectedProvince))));
                cityNames = tempList;
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,cityNames);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                CitySpinner.setAdapter(cityAdapter);
                cityAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedCity = cityNames.get(i);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signUpEmail = SignUpEnterEmail.getText().toString();
                String signUpName = SignUpEnterName.getText().toString();
                password1 = SignUpEnterPassword.getText().toString();
                password2 = SignUpEnterPasswordAgain.getText().toString();
                if (!verifyFields(signUpEmail) && !verifyFields(signUpName)) {
                    SignUpWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    SignUpWarning.setVisibility(View.INVISIBLE);
                }

                tempUser = new User();
                tempUser.setEmail(signUpEmail);
                tempUser.setUserName(signUpName);
                if (password1.compareTo(password2) == 0) {
                    tempUser.setPassword(password1);
                    userDatabaseAccessor.registerUser(tempUser, LoginContainerActivity.this);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_SHORT).show();
                }



            }});


    }
    private void populateMap(){
        ArrayMap.put("Alberta",R.array.Alberta);
        ArrayMap.put("British Columbia",R.array.British_Columbia);
        ArrayMap.put("Manitoba",R.array.Manitoba);
        ArrayMap.put("New Brunswick",R.array.New_Brunswick);
        ArrayMap.put("Newfoundland and Labrador",R.array.Newfoundland_and_Labrador);
        ArrayMap.put("Northwest Territories",R.array.Northwest_Territories);
        ArrayMap.put("Nova Scotia",R.array.Nova_Scotia);
        ArrayMap.put("Nunavut",R.array.Nunavut);
        ArrayMap.put("Ontario",R.array.Ontario);
        ArrayMap.put("Prince Edward Island",R.array.Prince_Edward_Island);
        ArrayMap.put("Quebec",R.array.Quebec);
        ArrayMap.put("Saskatchewan",R.array.Saskatchewan);
        ArrayMap.put("Yukon",R.array.Yukon);
    }
    private void bindViews(){
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
        SignUpWarning = findViewById(R.id.SignUpWarning);
        VerifyWaitingDialog = new ProgressDialog(LoginContainerActivity.this);
        VerifyWaitingDialog.setContentView(R.layout.verify_email_dialog);
        page2 = findViewById(R.id.page2);

        SignUpEnterPassword = findViewById(R.id.SignUpEnterPassword);
        SignUpEnterPasswordAgain = findViewById(R.id.SignUpEnterPasswordAgain);
        SignUpPasswordTitle = findViewById(R.id.SignUpPasswordTitle);
        SignUpPasswordTitleAgain = findViewById(R.id.SignUpPasswordTitleAgain);
        SignUpEnterPhone = findViewById(R.id.SignUpEnterPhone);
        SignUpPhoneNumber = findViewById(R.id.SignUpPhoneNumber);
        FinishSignUpButton = findViewById(R.id.FinishSignUpButton);
        SignUpEnterLocationTitle = findViewById(R.id.SignUpEnterLocationTitle);
        ProvinceSpinner = findViewById(R.id.ProvinceSpinner);
        CitySpinner = findViewById(R.id.CitySpinner);
        page3 = findViewById(R.id.page3);
    }
    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
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


    }
    private void display_page(int num){
        for (int i=1;i<4;i++){
            if(i!=num){
                Objects.requireNonNull(pageMap.get(i)).setVisibility(View.INVISIBLE);
            }else{
                Objects.requireNonNull(pageMap.get(i)).setVisibility(View.VISIBLE);
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
        //the user has been successful updated
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        Bundle bundle = new Bundle();
        // put the user object into the bundle, Profile activity can access directly:
        bundle.putParcelable("UserObject", tempUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        // show success message here:
        Toast.makeText(getApplicationContext(),
                "logged in successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        /*
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        Bundle bundle = new Bundle();
        // put the user object into the bundle, Profile activity can access directly:

        bundle.putParcelable("UserObject", user);
        intent.putExtras(bundle);

        startActivity(intent);
        if (!LoginContainerActivity.this.isFinishing() && progressDialog!=null) {
            this.progressDialog.dismiss();
        }

        finish();

         */
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
    public void onValidateSuccess() {
        //the account has been validated
        Log.v("move forward", "Now move to the third page");
        Toast.makeText(getApplicationContext(),"Validate success", Toast.LENGTH_SHORT).show();
        VerifyWaitingDialog.dismiss();
        display_page(3);

        FinishSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedProvince != null && selectedCity != null &&!selectedProvince.equals("") && !selectedCity.equals("")){
                    Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_SHORT).show();
                    phoneNum = SignUpEnterPhone.getText().toString();
                    tempUser.setPhoneNumber(phoneNum);
                    tempUser.setUserCity(selectedCity);
                    tempUser.setUserProvince(selectedProvince);
                    userDatabaseAccessor.createUserProfile(tempUser,LoginContainerActivity.this);
                }else{
                    Toast.makeText(getApplicationContext(),"Please select Province and City properly",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onValidateFailure() {
        Toast.makeText(getApplicationContext(),"Validate failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteSuccess() {
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onDeleteFailure() {
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onRegisterSuccess() {
        FirebaseUser user = userDatabaseAccessor.getFirebaseAuth().getCurrentUser();
        userDatabaseAccessor.getFirebaseAuth().signInWithEmailAndPassword(tempUser.getEmail(),tempUser.getPassword())
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                assert user != null;
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("send", "Email sent.");
                                    Toast.makeText(getApplicationContext(),"Please check your email", Toast.LENGTH_SHORT).show();
                                    VerifyWaitingDialog.setCancelable(false);
                                    VerifyWaitingDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Move Forward", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            userDatabaseAccessor.isValidated(LoginContainerActivity.this);
                                        }
                                    });
                                    VerifyWaitingDialog.show();

                                }
                            }
                        });
            } else {
                Log.v("test", "Login failed!");

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