package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.HashMap;
import java.util.Objects;

public class UserProfileDisplayActivity extends AppCompatActivity implements UserProfileStatusListener {
    private EditText nameText;
    private TextView nameTitle;
    private TextView regionTitle;

    private TextView regionText;
    private TextView phoneTitle;
    private EditText phoneText;
    private TextView emailTitle;
    private EditText emailText;
    private TextView title;
    private ImageView backButton;
    private ImageView editProfileButton;
    private boolean isEdit = false;
    private ImageView updateProfileButton;
    private User user;
    private String name;
    private String phone;
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_user_profile_display);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActivityCollector.addActivity(this);
        bindViews();
        updateProfileButton.setVisibility(View.INVISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);

            }
        });
        userDatabaseAccessor.getUserProfile(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void bindViews(){
        nameText = findViewById(R.id.nameText);
        nameTitle = findViewById(R.id.nameTitle);
        regionTitle = findViewById(R.id.regionTitle);
        regionText = findViewById(R.id.regionText);
        phoneTitle = findViewById(R.id.phoneTitle);
        phoneText = findViewById(R.id.phoneText);
        emailTitle = findViewById(R.id.emailTitle);
        emailText = findViewById(R.id.emailText);
        title = findViewById(R.id.title);
        backButton = findViewById(R.id.backButton);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        editProfileButton = findViewById(R.id.editProfileButton);
    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.user = user;
        name = user.getUserName();
        phone = user.getPhoneNumber();
        nameText.setText(name);
        regionText.setText(String.format("%s %s",user.getUserProvince(),user.getUserCity()));

        phoneText.setText(phone);
        emailText.setText(user.getEmail());
        nameText.setEnabled(false);


        phoneText.setEnabled(false);
        emailText.setEnabled(false);
        regionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditRegionActivity.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("User",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameText.setEnabled(true);
                phoneText.setEnabled(true);

                updateProfileButton.setVisibility(View.VISIBLE);
                updateProfileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap hashMap = new HashMap();
                        String newName = nameText.getText().toString();
                        String newPhone = phoneText.getText().toString();
                        if(!newName.equals(name)){
                            hashMap.put("userName",newName);
                        }
                        if(!newPhone.equals(phone)){
                            hashMap.put("phoneNumber",newPhone);
                        }
                        userDatabaseAccessor.updateUserProfile(user, hashMap, new UserProfileStatusListener() {
                            @Override
                            public void onProfileStoreSuccess() {

                            }

                            @Override
                            public void onProfileStoreFailure() {

                            }

                            @Override
                            public void onProfileRetrieveSuccess(User user) {

                            }

                            @Override
                            public void onProfileRetrieveFailure() {

                            }

                            @Override
                            public void onProfileUpdateSuccess(User user) {
                                nameText.setText(newName);
                                phoneText.setText(newPhone);
                                nameText.setEnabled(false);
                                phoneText.setEnabled(false);
                                Toast.makeText(getApplicationContext(),"Update Success",Toast.LENGTH_SHORT).show();
                                updateProfileButton.setVisibility(View.INVISIBLE);

                            }

                            @Override
                            public void onProfileUpdateFailure() {

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
                        });
                    }
                });

            }
        });
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