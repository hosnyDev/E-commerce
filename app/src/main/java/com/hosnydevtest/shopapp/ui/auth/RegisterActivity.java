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
import com.google.firebase.firestore.FirebaseFirestore;
import com.hosnydevtest.shopapp.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, phone;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        phone = findViewById(R.id.phone_register);
        progressBar = findViewById(R.id.progress_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        findViewById(R.id.btn_register).setOnClickListener(v -> validationData());

    }


    private void validationData() {

        String _name = name.getText().toString().trim();
        String _email = email.getText().toString().trim();
        String _pass = password.getText().toString().trim();
        String _phone = phone.getText().toString().trim();

        if (_name.isEmpty()) {
            name.requestFocus();
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return;
        }

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
        if (_phone.isEmpty()) {
            password.requestFocus();
            Toast.makeText(this, "Phone is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (_phone.length() < 11) {
            password.requestFocus();
            Toast.makeText(this, "Phone must be 11 digit ", Toast.LENGTH_SHORT).show();
            return;
        }

        createNewUser(_email, _pass);

    }

    private void createNewUser(String email, String pass) {

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        addUserToFirestore();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Error + \n" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });

    }

    private void addUserToFirestore() {
        if (firebaseAuth.getCurrentUser() != null) {

            String uID = firebaseAuth.getCurrentUser().getUid();

            Map<String, Object> user = new HashMap<>();
            user.put("id", uID);
            user.put("name", name.getText().toString().trim());
            user.put("email", email.getText().toString().trim());
            user.put("password", password.getText().toString().trim());
            user.put("phone", phone.getText().toString().trim());

            firestore.collection("users")
                    .document(uID)
                    .set(user)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Congratulation account created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Error + \n" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

        }
    }

}