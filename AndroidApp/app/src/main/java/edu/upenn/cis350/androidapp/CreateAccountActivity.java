package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.androidapp.MainActivity;
import edu.upenn.cis350.androidapp.R;
import edu.upenn.cis350.androidapp.UserProcessing.AccountJSONProcessor;

public class CreateAccountActivity extends AppCompatActivity  {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordVerifyInput;
    private AccountJSONProcessor processor = AccountJSONProcessor.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        usernameInput = findViewById(R.id.make_username);
        passwordInput = findViewById(R.id.make_password);
        passwordVerifyInput = findViewById(R.id.make_password_verify);
    }

    public void onCreateAccountButtonClick(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String passwordVerify = passwordVerifyInput.getText().toString();

        if (!usernameWorks(username)) {
            return;
        }

        if (!processor.usernameIsNew(username)) {
            Toast.makeText(getApplicationContext(),
                    "Username already has an associated account.",
                    Toast.LENGTH_LONG).show();
            return;
        }

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

        verifyEmail(username);
        Toast.makeText(getApplicationContext(),
                "Verification email sent to " + username +
                        ". Return to login screen after verifying.", Toast.LENGTH_LONG).show();

        // After verification
        confirmAccountCreation(username, password);
    }

    // TODO: Verification link (through Node.js?)
    private void verifyEmail(String username) {
        final String email = username;
        new AsyncTask<String, String, String>() {
            protected String doInBackground(String... inputs) {
                try {
                    GMailSender sender = new GMailSender("pennlostfound@gmail.com",
                            "ilovelukeyeagley");
                    sender.sendMail("Email Verification",
                            "Click on the following link to verify your Penn email: TODO",
                            "pennlostfound@gmail.com",
                            email);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                return null;
            }
            protected void onPostExecute(String input) {
            }
        }.execute();
    }

    // Call after email has been verified
    private void confirmAccountCreation(String username, String password) {
        processor.createNewAccount(username, password);
    }

    public boolean passwordWorks(String pass) {
        if (pass.length() < 6) {
            return false;
        }
        return true;
    }

    public boolean usernameWorks(String username) {
        if (!username.contains("@")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter an email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        String mail = username.substring(username.lastIndexOf("@") + 1);
        if (!mail.contains("upenn.edu")) {
            Toast.makeText(getApplicationContext(),
                    "Penn email address required.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
