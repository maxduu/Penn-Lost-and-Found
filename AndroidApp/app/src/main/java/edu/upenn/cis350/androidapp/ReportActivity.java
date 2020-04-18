package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.LostJSONWriter;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.ReportWriter;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.AccountJSONProcessor;

public class ReportActivity extends AppCompatActivity {

    private long reporterId;
    private long violatorId;
    private String category;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reporterId = getIntent().getLongExtra("reporterId", -1);
        violatorId = getIntent().getLongExtra("violatorId", -1);
        category = getIntent().getStringExtra("category");
        TextView violatorUsername = findViewById(R.id.violatorId);
        TextView reportCategory = findViewById(R.id.reportCategory);
        violatorUsername.setText(AccountJSONProcessor.getInstance().getAccount(violatorId).getUsername());
        reportCategory.setText(category);
    }

    public void onSendReportClick(View v) {
        EditText reportText = findViewById(R.id.reportText);
        message = reportText.getText().toString();
        if (message.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please explain briefly why you are reporting the user.", Toast.LENGTH_LONG).show();
        } else {
            ReportWriter.getInstance().createReport(reporterId, violatorId, category, message);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("userId", reporterId);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
