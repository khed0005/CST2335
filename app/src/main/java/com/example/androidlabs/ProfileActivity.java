//package com.example.androidlabs;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class ProfileActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//    }
//}

package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    Button chatRoom;
    Button toolbarClass;
    Button weatherForecast;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String text = ((Intent) intent).getStringExtra(MainActivity.EXTRA_TEXT);

        EditText email = (EditText) findViewById(R.id.email2);
        email.setText(text);

        mImageButton = (ImageButton)findViewById(R.id.imageclick);

        mImageButton.setOnClickListener(e->{
            dispatchTakePictureIntent();
        });
        chatRoom=findViewById(R.id.button5);
        chatRoom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ openChatRoomActivity();}
        });

        toolbarClass = findViewById(R.id.button6);
        toolbarClass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ openToolbarActivity();}
        });

        weatherForecast = findViewById(R.id.button7);
        weatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openWeatherForecast();}
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME,"In function: onActivityResult");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"In function: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"In function: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME,"In function: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME,"In function: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"In function: onDestroy");
    }
    public void openChatRoomActivity(){

        Intent chatRoomActivity = new Intent(ProfileActivity.this, ChatRoomActivity.class );
        startActivity(chatRoomActivity);

    }
    public void openToolbarActivity(){

        Intent toolbarActivity = new Intent(ProfileActivity.this, TestToolbar.class );
        startActivity(toolbarActivity);

    }
    public void openWeatherForecast(){
        Intent weatherForecastActivity = new Intent(ProfileActivity.this, WeatherForecast.class);
        startActivity(weatherForecastActivity);
    }

}

