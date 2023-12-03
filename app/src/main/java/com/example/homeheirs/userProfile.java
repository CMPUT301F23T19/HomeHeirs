package com.example.homeheirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Class thats responsible for showing some user information (thier email)
 * as well as handling the logout
 */
public class userProfile extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    private TextView email_toDisplay,password_toDisplay;

    private Button logout_button;




    private BottomNavigationView bottomNavigationView;


    /**
     * Method For when the acitivty starts, the activity sets the users username and presents a logout button
     * which logs the indiviudal out
     * @param savedInstanceState-Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user==null){
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);



        // The navigation view is used to navigate bewteen the different pages
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId()== R.id.navigation_logout) {

                    return true;
                } else if (item.getItemId()== R.id.navigation_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }


                return false;
            }
        });

        email_toDisplay=findViewById(R.id.user_email_textView);
       // password_toDisplay = findViewById(R.id.user_password_textView);
        logout_button = findViewById(R.id.logout_button);


        // set the two fields to the information
        email_toDisplay.setText(user.getEmail());
        // we cannot display the password currently

        // logout button logs the user out
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                // Due to Some thread error
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getApplicationContext()).clearDiskCache();
                    }
                }).start();
                Intent intent = new Intent(getApplicationContext(), login.class);

                startActivity(intent);
                finish();
            }
        });


    }


}