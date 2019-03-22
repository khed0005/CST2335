package com.example.androidlabs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    private String itemOneMsg="This is the initial message";
    private Snackbar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        sb = Snackbar.make(tBar, "", Snackbar.LENGTH_LONG);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:

            case R.id.item4: Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG ).show();
            break;
            case R.id.item1: Toast.makeText(this, itemOneMsg, Toast.LENGTH_LONG ).show();
            break;
            case R.id.item2: aler();
            break;
            case R.id.item3: sb.setAction("Go Back?",e->finish()).show();
            break;

        }
        return true;
    }

    public void aler()
    {
        View middle = getLayoutInflater().inflate(R.layout.activity_dialog_box, null);

        EditText et = (EditText)middle.findViewById(R.id.editText);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        itemOneMsg= et.getText().toString() ;
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).setView(middle);

        builder.create().show();
    }
}
