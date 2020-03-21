package edu.upenn.cis350.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.upenn.cis350.androidapp.UserProcessing.Account;
import edu.upenn.cis350.androidapp.UserProcessing.AccountJSONProcessor;

public class LoginActivity extends AppCompatActivity  {

    private int failedAttempts; // implement later
    private EditText usernameInput;
    private EditText passwordInput;
    private AccountJSONProcessor processor = AccountJSONProcessor.getInstance();

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
        processor = AccountJSONProcessor.getInstance();
        int loginResult = processor.attemptLogin(username, password);
        if (loginResult == 0) {
            successfulLogin(username);
        } else if (loginResult == 1){
            Toast.makeText(getApplicationContext(),
                    "Username not found. Create an account?", Toast.LENGTH_LONG).show();
            return;
        } else if (loginResult == 2) {
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

        long id = processor.getIdFromUsername(name);
        processor.updateLastLogin(id);
        i.putExtra("userId", id);
        startActivity(i);
    }

    // TODO
    // Lock login after 5 failed attempts
    private void lockLogin() {
        Toast.makeText(getApplicationContext(),
                "Try again in 30 seconds.", Toast.LENGTH_LONG).show();
    }

}
