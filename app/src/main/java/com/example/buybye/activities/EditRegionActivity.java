package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class EditRegionActivity extends AppCompatActivity {
    private ImageView backButton;
    private TextView titleText;
    private Spinner ProvinceSpinner;
    private Spinner CitySpinner;
    private ImageView updateProfileButton;
    private ArrayList<String> provinceNames = new ArrayList<>();
    private ArrayList<String> cityNames = new ArrayList<>();
    private String selectedProvince;
    private HashMap<String, Integer> ArrayMap = new HashMap<>();
    private String selectedCity;
    private User user;
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_edit_region);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        bindView();
        populateMap();
        user = getIntent().getParcelableExtra("User");
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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.removeActivity(EditRegionActivity.this);
                Intent intent = new Intent(EditRegionActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedProvince != null && selectedCity != null){
                    HashMap hashMap = new HashMap();
                    hashMap.put("userProvince",selectedProvince);
                    hashMap.put("userCity",selectedCity);
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
                            Intent intent = new Intent(getApplicationContext(),UserProfileDisplayActivity.class);
                            Toast.makeText(getApplicationContext(),"Update Region Finished", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

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
                }else{
                    Toast.makeText(getApplicationContext(),"Province and City can not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
    private void bindView(){
        backButton = findViewById(R.id.backButton);
        titleText = findViewById(R.id.titleText);
        ProvinceSpinner = findViewById(R.id.ProvinceSpinner);
        CitySpinner = findViewById(R.id.CitySpinner);
        updateProfileButton = findViewById(R.id.updateProfileButton);
    }
}