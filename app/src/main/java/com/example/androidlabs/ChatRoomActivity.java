package com.example.androidlabs;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private Button sentmsg;
    private Button receivemsg;
    private EditText txtMessage;
    private ArrayList<Message> message;
    private final static int SENT_MESSAGE = 0;
    private final static int RECEIVED_MESSAGE = 1;

   public void printCursor( Cursor c){

       Log.e("DB Version Number ", String.valueOf(MyDatabaseOpener.VERSION_NUM));
       Log.e("The number of the columns in the cursor", String.valueOf(c.getColumnCount()));
       Log.e("The name of columns in the cursor", c.getColumnName(0)+", "+ c.getColumnName(1)+", "+ c.getColumnName(2));
       Log.e("The number of results in the cursor", String.valueOf(c.getCount()));

       int idColIndex = c.getColumnIndex(MyDatabaseOpener.COL_ID);
       int msgCOlIndex = c.getColumnIndex(MyDatabaseOpener.COL_Message);
       int typeColIndex = c.getColumnIndex(MyDatabaseOpener.COL_Type);
       c.moveToFirst();

       while ( c.moveToNext() ){
           long id = c.getLong(idColIndex);
           String msg = c.getString(msgCOlIndex);
           int type = c.getInt(typeColIndex);

           Log.e("id: ",id+ "Message: "+ msg+ "Type: "+ type);

       }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        sentmsg = (Button)findViewById(R.id.labbutton2);
        receivemsg = (Button)findViewById(R.id.lab4button1);
        message = new ArrayList<Message>();
        txtMessage = (EditText)findViewById(R.id.lab4EditText);

        MyDatabaseOpener dbOpener = new MyDatabaseOpener( this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        String[] columns = {MyDatabaseOpener.COL_ID, MyDatabaseOpener.COL_Message, MyDatabaseOpener.COL_Type};
        Cursor results = db.query(false, MyDatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null );

        int idColIndex = results.getColumnIndex(MyDatabaseOpener.COL_ID);
        int msgCOlIndex = results.getColumnIndex(MyDatabaseOpener.COL_Message);
        int typeColIndex = results.getColumnIndex(MyDatabaseOpener.COL_Type);

        while (results.moveToNext()){
            long id = results.getLong(idColIndex);
            String msg = results.getString(msgCOlIndex);
            int type = results.getInt(typeColIndex);

            message.add(new Message(msg, type, id));
        }
         printCursor(results);

        ListAdapter adt = new MyAdapter();
        ListView theList = (ListView)findViewById(R.id.listView1);
        theList.setAdapter(adt);

        sentmsg.setOnClickListener(e->{

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpener.COL_Message, txtMessage.getText().toString() );
            newRowValues.put(MyDatabaseOpener.COL_Type, SENT_MESSAGE);
            long newID = db.insert(MyDatabaseOpener.TABLE_NAME, null, newRowValues );
            message.add(new Message(txtMessage.getText().toString(), SENT_MESSAGE, newID));
            ((MyAdapter) adt).notifyDataSetChanged();
            txtMessage.setText("");

        });

        receivemsg.setOnClickListener(e->{

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpener.COL_Message, txtMessage.getText().toString() );
            newRowValues.put(MyDatabaseOpener.COL_Type, RECEIVED_MESSAGE);
            long newID = db.insert(MyDatabaseOpener.TABLE_NAME, null, newRowValues );
            message.add(new Message(txtMessage.getText().toString(), RECEIVED_MESSAGE, newID));
            ((MyAdapter) adt).notifyDataSetChanged();
            txtMessage.setText("");

        });
    }

    protected class MyAdapter extends BaseAdapter {
        @Override
        public int getCount(){ return message.size();}

        public Object getItem( int position){ return message.get(position).getMessage().toString(); }

        public View getView(int position, View old, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            if( message.get(position).getMessageType() == SENT_MESSAGE){
                View newView = inflater.inflate( R.layout.activity_send, parent, false);
                TextView textSend = (TextView)newView.findViewById(R.id.textView10);
                String stringToShow = getItem(position).toString();
                textSend.setText(stringToShow);
                return newView;
            }else{
                View newView = inflater.inflate( R.layout.activity_receive, parent, false);
                TextView textReceive = (TextView)newView.findViewById(R.id.textView11);
                String stringToShow = getItem(position).toString();
                textReceive.setText(stringToShow);
                return newView;
            }
        }

        public long getItemId( int position){ return 0;}
    }
}