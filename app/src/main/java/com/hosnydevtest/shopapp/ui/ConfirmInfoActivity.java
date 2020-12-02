package com.hosnydevtest.shopapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hosnydevtest.shopapp.R;

public class ConfirmInfoActivity extends AppCompatActivity {

    private TextView name_confirmation, email_confirmation, phone_confirmation, address_confirmation;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_info);

        name_confirmation = findViewById(R.id.name_confirmation);
        email_confirmation = findViewById(R.id.email_confirmation);
        phone_confirmation = findViewById(R.id.phone_confirmation);
        address_confirmation = findViewById(R.id.address_confirmation);
        progressBar = findViewById(R.id.progress_confirmation);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        findViewById(R.id.image_back_confirmation).setOnClickListener(v -> finish());

        findViewById(R.id.btn_update_info_confirmation).setOnClickListener(v -> startActivity(new Intent(this, UpdateProfileActivity.class)));

        getUserData();

    }


    private void getUserData() {

        progressBar.setVisibility(View.VISIBLE);

        if (firebaseAuth.getCurrentUser() != null) {

            DocumentReference documentReference = firestore.collection("users")
                    .document(firebaseAuth.getCurrentUser().getUid());

            documentReference.get().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    DocumentSnapshot snapshot = task.getResult();

                    assert snapshot != null;
                    if (snapshot.exists()) {

                        String name = snapshot.getString("name");
                        String email = snapshot.getString("email");
                        String phone = snapshot.getString("phone");
                        String address = snapshot.getString("address");

                        assert name != null;
                        if (name.isEmpty()) {
                            name_confirmation.setVisibility(View.GONE);
                        } else {
                            name_confirmation.setText(name);
                        }

                        assert email != null;
                        if (email.isEmpty()) {
                            email_confirmation.setVisibility(View.GONE);
                        } else {
                            email_confirmation.setText(email);
                        }

                        assert phone != null;
                        if (phone.isEmpty()) {
                            phone_confirmation.setVisibility(View.GONE);
                        } else {
                            phone_confirmation.setText(phone);
                        }

                        assert address != null;
                        if (address.isEmpty()) {
                            address_confirmation.setVisibility(View.GONE);
                        } else {
                            address_confirmation.setText(address);
                        }

                        progressBar.setVisibility(View.GONE);

                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            });


        } else {
            progressBar.setVisibility(View.GONE);
            logOut();
        }


    }

    private void logOut() {
        getSharedPreferences("login", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
        startActivity(new Intent(ConfirmInfoActivity.this, SplashActivity.class));
    }

}