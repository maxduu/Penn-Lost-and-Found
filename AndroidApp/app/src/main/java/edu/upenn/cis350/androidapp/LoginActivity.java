package edu.upenn.cis350.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Ban;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.*;

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
            long id = processor.getIdFromUsername(username);
            Ban ban = checkBan(id);
            if (ban == null) {
                successfulLogin(username, id);
                return;
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.loginlayout),
                        "Your account has been banned until:\n" + ban.getUntil().toString() +
                        " \n\nReason: " + ban.getMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok, got it.", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setMaxLines(10);  // show multiple lines
                snackbar.show();
                return;
            }

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

    private void successfulLogin(String name, long id) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", name);

        processor.updateLastLogin(id);
        i.putExtra("userId", id);
        startActivity(i);
    }

    private Ban checkBan(long id) {
        BanProcessor banProcessor = BanProcessor.getInstance();
        Ban ban = banProcessor.getBanOfUser(id);
        return ban;
    }

    // TODO
    // Lock login after 5 failed attempts
    private void lockLogin() {
        Toast.makeText(getApplicationContext(),
                "Try again in 30 seconds.", Toast.LENGTH_LONG).show();
    }



}
