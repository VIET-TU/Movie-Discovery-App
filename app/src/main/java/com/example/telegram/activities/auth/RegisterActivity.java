package com.example.telegram.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telegram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText userEdt,passEdt,confirmpassEdt;
    private TextView txt_to_login;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        userEdt = findViewById(R.id.edt_email_login);
        passEdt =  findViewById(R.id.edt_password_login);
        confirmpassEdt = findViewById(R.id.edt_confirm_password_login);
        btnRegister = findViewById(R.id.btn_register);
        txt_to_login = findViewById(R.id.txt_to_login);

        txt_to_login.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            String email, password, confirmpasswrod;
            email = userEdt.getText().toString();
            password = passEdt.getText().toString();
            confirmpasswrod = confirmpassEdt.getText().toString();

            if(email.isEmpty() || email.isEmpty() || password.isEmpty() || password.isEmpty() || !password.equals(confirmpasswrod)){
                Toast.makeText(this,"Wrong information", Toast.LENGTH_LONG);
                return;
            }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Boolean a = task.isSuccessful();
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Account created successfully.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Account create failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        });

    }
}