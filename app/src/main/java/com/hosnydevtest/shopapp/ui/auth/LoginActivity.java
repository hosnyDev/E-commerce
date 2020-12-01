package com.hosnydevtest.shopapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hosnydevtest.shopapp.MainActivity;
import com.hosnydevtest.shopapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        progressBar = findViewById(R.id.progress_login);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.create_new_account).setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            validationData();
        });

    }

    private void validationData() {

        String _email = email.getText().toString().trim();
        String _pass = password.getText().toString().trim();


        if (_email.isEmpty()) {
            email.requestFocus();
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            email.requestFocus();
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (_pass.isEmpty()) {
            password.requestFocus();
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (_pass.length() < 6) {
            password.requestFocus();
            Toast.makeText(this, "Password must be 6 digit ", Toast.LENGTH_SHORT).show();
            return;
        }

        loginWithFirebase(_email, _pass);
    }

    private void loginWithFirebase(String email, String pass) {

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        getSharedPreferences("login", MODE_PRIVATE)
                                .edit()
                                .putBoolean("isLogin", true)
                                .apply();

                        goToMain();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Error \n " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void goToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean isLogin = getSharedPreferences("login", MODE_PRIVATE).getBoolean("isLogin", false);

        if (isLogin) {
            goToMain();
        }

    }
}