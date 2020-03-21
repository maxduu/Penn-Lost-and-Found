package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.androidapp.UserProcessing.AccountJSONProcessor;

public class ForgotPasswordNewPasswordActivity extends AppCompatActivity {

    private String username;
    private EditText passwordInput;
    private EditText passwordVerifyInput;
    private AccountJSONProcessor processor = AccountJSONProcessor.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_change_password);
        username = getIntent().getStringExtra("username");
    }

    public void onChangePasswordButtonClick(View view) {
        passwordInput = findViewById(R.id.change_password);
        passwordVerifyInput = findViewById(R.id.change_password_verify);
        String password = passwordInput.getText().toString();
        String passwordVerify = passwordVerifyInput.getText().toString();

        if (!password.equals(passwordVerify)) {
            Toast.makeText(getApplicationContext(),
                    "Passwords do not match.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!passwordWorks(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter a viable password.", Toast.LENGTH_LONG).show();
            return;
        }

        // If everything works:
        processor.changePassword(processor.getIdFromUsername(username), password);
        Toast.makeText(getApplicationContext(),
                "Your password has been changed. You may now log in.",
                Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public boolean passwordWorks(String pass) {
        if (pass.length() < 6) {
            return false;
        }
        return true;
    }

}