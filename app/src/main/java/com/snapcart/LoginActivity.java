package com.snapcart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginBtn;
    private TextView goToRegisterBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_button);
        goToRegisterBtn = findViewById(R.id.go_to_register);
        loginBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(this, "✅ Login successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, ProductListActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            String errorMessage = e.getMessage();

                            if (errorMessage.contains("There is no user record")) {
                                Toast.makeText(this, "❌ Email not found. Please register.", Toast.LENGTH_LONG).show();
                            } else if (errorMessage.contains("The password is invalid")) {
                                Toast.makeText(this, "🔐 Incorrect password. Please try again.", Toast.LENGTH_LONG).show();
                            } else if (errorMessage.contains("email address is badly formatted")) {
                                Toast.makeText(this, "⚠️ Please enter a valid email address.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(this, "⚠️ Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        goToRegisterBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
