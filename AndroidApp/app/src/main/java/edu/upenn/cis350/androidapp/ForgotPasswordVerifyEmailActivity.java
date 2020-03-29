package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ForgotPasswordVerifyEmailActivity extends AppCompatActivity  {

    private String username;
    private int code;
    private EditText codeInputField;
    private Random random;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        codeInputField = (EditText) findViewById(R.id.codeInput);
        random = new Random();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_verify_email);
        username = getIntent().getStringExtra("username");
        verifyEmail();
    }

    public void onForgotAttemptCodeButtonClick(View view) {
        EditText codeInputField = (EditText) findViewById(R.id.codeInput);
        String rawInput = codeInputField.getText().toString();
        int givenCode = Integer.parseInt(rawInput);
        if (givenCode == code) {
            Intent i = new Intent(this, ForgotPasswordNewPasswordActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Incorrect code. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void verifyEmail() {
        Toast.makeText(getApplicationContext(),
                "Verification code sent to " + username + ".", Toast.LENGTH_LONG).show();
        code = random.nextInt(900000) + 100000;
        final String email = username;
        final int emailCode = code;

        new AsyncTask<String, String, String>() {
            protected String doInBackground(String... inputs) {
                try {
                    GMailSender sender = new GMailSender("pennlostfound@gmail.com",
                            "ilovelukeyeagley");
                    sender.sendMail("Password Reset: Email Verification",
                            "An attempt has been made to reset your password.\n" +
                                    "Enter the following code into the app to verify your account:\n" +
                            emailCode,
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

    public void onForgotResendEmailButtonClick(View view) {
        verifyEmail();
    }


}
