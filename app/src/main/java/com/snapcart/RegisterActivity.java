package com.snapcart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, confirmPassword;
    private Button registerBtn;
    private TextView goToLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
        registerBtn = findViewById(R.id.register_button);
        goToLogin = findViewById(R.id.go_to_login);

        registerBtn.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String passText = password.getText().toString().trim();
            String confirmText = confirmPassword.getText().toString().trim();

            if (emailText.isEmpty() || passText.isEmpty() || confirmText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!passText.equals(confirmText)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(emailText, passText)
                    .addOnSuccessListener(authResult -> {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
