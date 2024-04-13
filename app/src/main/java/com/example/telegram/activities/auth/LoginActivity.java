package com.example.telegram.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telegram.R;
import com.example.telegram.activities.Intro_activity;
import com.example.telegram.activities.MainActivity;
import com.example.telegram.database.UserEntity;
import com.example.telegram.models.User;
import com.example.telegram.utils.EmailPasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText userEdt,passEdt;
    private TextView txt_to_register;
    private Button loginBtn;

    private FirebaseAuth mAuth;

    private UserEntity userEntity;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            String email = currentUser.getEmail();
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEntity = new UserEntity(this,"UserTable",null,1);
        mAuth = FirebaseAuth.getInstance();
        initView();
    }



    private void initView() {
        userEdt = findViewById(R.id.edt_email_login);
        passEdt =  findViewById(R.id.edt_password_login);
        loginBtn =findViewById(R.id.btn_signin);
        txt_to_register = findViewById(R.id.txt_to_register);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEdt.getText().toString();
                String password = passEdt.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Fill your user and password", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String name = user.getEmail();
                                    User user_ = userEntity.getUserByEmail(email);
                                    if(user_ == null) {
                                        userEntity.addUser(new User(email,"image",true));
                                    } else {
                                        userEntity.updateUser(new User(email,"image",true));
                                    }
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        txt_to_register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }


}