package edu.upenn.cis350.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity  {

    private int failedAttempts; // implement later
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        failedAttempts = 0;
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }

    public void onLoginButtonClick(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (!checkUsername(username)) {
            Toast.makeText(getApplicationContext(),
                    "Username not found. Create an account?", Toast.LENGTH_LONG).show();
            return;
        }
        if (!checkPassword(username, password)) {
            if (failedAttempts == 5) {
                lockLogin();
                failedAttempts = 0;
                return;
            } else {
                failedAttempts++;
                Toast.makeText(getApplicationContext(),
                        "Incorrect password. Try again.", Toast.LENGTH_LONG).show();
                return;
            }
        }
        successfulLogin(username);
    }

    // TODO
    public boolean checkUsername(String username) {
        // Check if username exists in database
        return true;
    }

    // TODO
    public boolean checkPassword(String username, String password) {
        // Check if username/password combo exists in database
        return true;
    }

    public void onCreateNewAccountButtonClick(View view) {
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }

    public void onForgotPasswordButtonClick(View view) {
        Intent i = new Intent(this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    private void successfulLogin(String name) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", name);

        //TODO need userId to be passed around as well, use the processor
        long id = 1;
        i.putExtra("userId", id);
        startActivity(i);
    }

    // TODO
    // Lock login after 5 failed attempts
    private void lockLogin() {

    }

}
