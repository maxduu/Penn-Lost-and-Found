package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.androidapp.UserProcessing.Account;
import edu.upenn.cis350.androidapp.UserProcessing.AccountJSONProcessor;

public class ForgotPasswordActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void onResetPasswordEmailButtonClick(View view) {
        EditText usernameInput = findViewById(R.id.forgotPasswordEmail);
        String username = usernameInput.getText().toString();
        if (usernameExists(username)) {
            Intent i = new Intent(this, ForgotPasswordVerifyEmailActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),
                    "We did not find an account associated with this email.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean usernameExists(String username) {
        AccountJSONProcessor processor = AccountJSONProcessor.getInstance();
        for (Account a : processor.getAllAccounts()) {
            if (username.equals(a.getUsername())) {
                return true;
            }
        }
        return false;
    }
}
