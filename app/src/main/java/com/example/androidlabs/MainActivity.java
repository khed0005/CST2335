package com.example.androidlabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.util.Log;

import static com.example.androidlabs.R.id.textView4;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.androidlabs.EXTRA_TEXT";
    SharedPreferences sp;
    EditText typeField;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_lab3);

        typeField = (EditText)findViewById(R.id.editText3);
        sp=getSharedPreferences ("FileName", Context.MODE_PRIVATE);
        String savedString = sp.getString("ReserveName", "");

        typeField.setText(savedString);
        login = findViewById(R.id.button4);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openProfileActivity();
            }
        });


    }
    public void openProfileActivity(){
        EditText editText1 = (EditText) findViewById(R.id.editText3);
        String text = editText1.getText().toString();

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(EXTRA_TEXT, text);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sp.edit();

        String whatWasTyped = typeField.getText().toString();
        editor.putString("ReserveName", whatWasTyped);
        editor.apply();
    }




}








