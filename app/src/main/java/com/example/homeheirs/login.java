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

public class login extends AppCompatActivity {


    private EditText editText_username, editText_password;
    private FirebaseAuth mAuth;

    // should change variables later
   private TextView register_button, login_button;


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


                // Create a validation fuction
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
                                    editText_username.requestFocus();
                                    editText_username.setError("Wrong username or Password");

                                }
                            }
                        });
            }
            }
        });





    }

    private boolean validate(String username, String password){
        // error check function
        if (username.isEmpty()){
            editText_username.requestFocus();
            editText_username.setError("Field Can'nt be Empty");
            return false;
        }
        if (password.isEmpty()){
            editText_username.requestFocus();
            editText_username.setError("Field Can'nt be Empty");
            return false;
        }


        return true;
    }
}