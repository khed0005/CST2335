package com.example.androidlabs;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        sentmsg = (Button)findViewById(R.id.labbutton2);
        receivemsg = (Button)findViewById(R.id.lab4button1);
        message = new ArrayList<Message>();
        txtMessage = (EditText)findViewById(R.id.lab4EditText);

        ListAdapter adt = new MyAdapter();
        ListView theList = (ListView)findViewById(R.id.listView1);
        theList.setAdapter(adt);

        sentmsg.setOnClickListener(e->{
            message.add(new Message(txtMessage.getText().toString(), SENT_MESSAGE));
            ((MyAdapter) adt).notifyDataSetChanged();
            txtMessage.setText("");
        });

        receivemsg.setOnClickListener(e->{
            message.add(new Message(txtMessage.getText().toString(), RECEIVED_MESSAGE));
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