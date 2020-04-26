package edu.upenn.cis350.androidapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
import android.content.*;
import android.view.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;


import java.io.File;
import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;

public class PostLost2Activity extends AppCompatActivity {

    private long posterId;
    private String category;
    private String around;
    private String description;
    private String additionalInfo;
    public String attachmentLoc = "";
    private double lat;
    private double lng;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNALSTORAGE = 1;
    private String imagePath;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost2);
        posterId = getIntent().getLongExtra("posterId",-1);
        category = getIntent().getStringExtra("category");
        around = getIntent().getStringExtra("around");
        lat = getIntent().getDoubleExtra("latitude", 39.9522);
        lng = getIntent().getDoubleExtra("longitude", -75.1932);
    }

    public void onMakeLostClick(View v) {
        //TODO find how to incorporate all of the necessary fields
        EditText describe = (EditText) findViewById(R.id.lost_description);
        description = describe.getText().toString();
        EditText addInfoField = (EditText) findViewById(R.id.lost_addinfo);
        additionalInfo = addInfoField.getText().toString();
        Date date = new Date();

        LostJSONWriter.getInstance().createLostItem(posterId, category, date, lat, lng,
                around, attachmentLoc, description, additionalInfo);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userId", posterId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onUploadImageClick(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, final Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView image = (ImageView) findViewById(R.id.lostImageView);
                image.setImageBitmap(selectedImage);

                imagePath = ImageFilePath.getPath(PostLost2Activity.this, data.getData());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PostLost2Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(PostLost2Activity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

}
