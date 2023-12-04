package com.example.homeheirs;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class Responsible for the initial login screen on
 * startup using Firebase auth
 * Source used: Youtube video
 * URL: https://www.youtube.com/watch?v=QAKq8UBv4GI
 * @author : Arsalan
 */
public class login extends AppCompatActivity {

    // initialize the variables
    private EditText editText_username, editText_password;
    private FirebaseAuth mAuth;

   private TextView register_button, login_button;

    /**
     * Method for when the app starts, checks to make sure if they are already logged in, in which
     * case they dont need to login again
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Method for the actual logging in, uses Firebase auth to validate the login
     * and once validated, starts the mainactivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_username = findViewById(R.id.Username_input);
        editText_password = findViewById(R.id.Password_input);

        mAuth = FirebaseAuth.getInstance();
        register_button = findViewById(R.id.registration_textview);
        login_button = findViewById(R.id.ok_textView);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, password_confirm;
                username = editText_username.getText().toString();
                password = editText_password.getText().toString();


                // Create a validation function
                boolean check = validate(username, password);

                //code taken from firestore docs
                if (check){
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(login.this, "Correct.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    editText_username.requestFocus();
                                    editText_username.setError("Wrong username or Password");
                                  
                                    editText_password.requestFocus();
                                    editText_password.setError("Wrong username or Password");

                                }
                            }
                        });
                }
            }
        });
    }


    /**
     * Fuction for validating input to prevent error crashes
     * @param username:String- contains username to validate not empty
     * @param password:String - contains password to validate not empty
     */
    public boolean validate(String username, String password){
        // error check function
         boolean check = true;
        if (username.isEmpty()){
            editText_username.requestFocus();
            editText_username.setError("Field Can't be Empty");
            check = false;
        }
        if (password.isEmpty()){
            editText_password.requestFocus();
            editText_password.setError("Field Can't be Empty");
            check=false;
        }

        return  check;
    }
}