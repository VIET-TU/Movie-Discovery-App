package com.example.telegram.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.telegram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    TextInputEditText   editTextEmail, editTextPassWord;
    Button btnRegister;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
//        editTextEmail = findViewById(R.id.email);
//        editTextPassWord = findViewById(R.id.password);
//        btnRegister = findViewById(R.id.btn_register);
//        progressBar = findViewById(R.id.progressBar);



        btnRegister.setOnClickListener(v -> {
            progressBar.setVisibility(v.VISIBLE);
            String email, password;
            email = editTextEmail.getText().toString();
            password = editTextPassWord.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Enter email", Toast.LENGTH_LONG);
                return;
            }

//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            progressBar.setVisibility(v.GONE);
//                            if (task.isSuccessful()) {
//                                Toast.makeText(AuthActivity.this, "Account created.",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Toast.makeText(AuthActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(v.GONE);
                            Boolean a = task.isSuccessful();
                            if (task.isSuccessful()) {
                                Toast.makeText(AuthActivity.this, "Account created.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("Register", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(AuthActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        });

    }
}