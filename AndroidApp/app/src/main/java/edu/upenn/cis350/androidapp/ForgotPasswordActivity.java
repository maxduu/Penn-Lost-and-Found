package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText usernameInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        usernameInput = findViewById(R.id.forgotPasswordEmail);
    }

    // TODO
    private void sendEmail(String username) {

    }
}
