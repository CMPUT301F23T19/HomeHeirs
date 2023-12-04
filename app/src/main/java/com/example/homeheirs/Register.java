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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


/**
 * Class That takes an email and password and creates an account using Firebase auth
 * @author Arsalan Ahmed
 * Source: Youtube video
 * Url : https://www.youtube.com/watch?v=QAKq8UBv4GI
 */

public class Register extends AppCompatActivity {

    // Initalize the text and editviews
    private EditText editText_username, editText_password, editText_password_confirm;
    private FirebaseAuth mAuth;

    private TextView register_button;


    /**
     * Method Describing what to do on Start, if user already logged in, take them to
     * the mainactivity
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
     * Method That actuallly, creates and account, where once created, logs the user
     * and starts the Login activity,
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // We are using our email as a username
        editText_username = findViewById(R.id.Username_input_register);
        editText_password = findViewById(R.id.Password_input_register);
        editText_password_confirm = findViewById(R.id.ConfirmPassword_input_register);
        mAuth = FirebaseAuth.getInstance();
        register_button = findViewById(R.id.register_textView);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, password_confirm;
                username = editText_username.getText().toString();
                password=editText_password.getText().toString();
                password_confirm = editText_password_confirm.getText().toString();




                // Create a validation fuction
                boolean check = validate(username,password,password_confirm);

                //code taken from firestore docs

                if (check) {
                    mAuth.createUserWithEmailAndPassword(username, password_confirm)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(Register.this, "Registered",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);
                                        finish();


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            // Handles the case where the user already exists
                                            editText_username.requestFocus();
                                            editText_username.setError("A User with this Email Already Exists");

                                            Toast.makeText(Register.this, "User already exists.",
                                                    Toast.LENGTH_SHORT).show();}
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                // When registered click automatically take to login page
            }
        });
    }

    /**
     * Method for Validating User input
     *  @param username:String- contains username to validate not empty
     *  @param password:String - contains password to validate not empty
     * @param confirm_password:String - Contains the confirm pass field and makes sure
     * that passwords are identical
     */
    public boolean validate(String username, String password, String confirm_password){
        // error check function
        boolean check = true;
        if (username.isEmpty()){
            editText_username.requestFocus();
            editText_username.setError("Field Can't be Empty");
            check=false;
        }
        if (password.isEmpty()){
            editText_password.requestFocus();
            editText_password.setError("Field Can't be Empty");
            check=false;


        }
        if (password.length()>=1 && password.length()<6) {
            editText_password.requestFocus();
            editText_password.setError("Password must be greater then 6 Characters");
            check = false;
        }
        if (confirm_password.isEmpty()){
            editText_password_confirm.requestFocus();
            editText_password_confirm.setError("Field Can't be Empty");
            check=false;
        }
        // Check to make sure password is the same
        if ( password.equals(confirm_password)==false){
            editText_password_confirm.requestFocus();
            editText_password_confirm.setError("Passwords must be same");
            editText_password.requestFocus();
            editText_password.setError("Passwords must be same");
            check=false;
        }
        return check;
    }
}

