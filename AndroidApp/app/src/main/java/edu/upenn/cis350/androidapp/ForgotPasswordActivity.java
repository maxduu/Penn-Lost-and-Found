package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void onResetPasswordEmailButtonClick(View view) {
        EditText usernameInput = findViewById(R.id.forgotPasswordEmail);
        String username = usernameInput.getText().toString();
        Intent i = new Intent(this, ForgotPasswordVerifyEmailActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }
}
