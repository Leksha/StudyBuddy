package uw.studybuddy.Notification;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uw.studybuddy.Chat.ChatActivity;
import uw.studybuddy.Chat.chat_event;
import uw.studybuddy.Events.EventDescription;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;

public class NotificationActivity extends AppCompatActivity {

    private ListView NotificationList;
    private ArrayList<String> NotificationArrayList;
    private ArrayAdapter<String> NotificationAdapter;

    DatabaseReference mDatabase;
    DatabaseReference mDatabaseEvent;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String eventkey;
    private String joinname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        NotificationList = (ListView) findViewById(R.id.NotificationList);

        NotificationArrayList = new ArrayList<String>();
        NotificationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,NotificationArrayList);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        NotificationList.setAdapter(NotificationAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notifications").child(mCurrentUser.getDisplayName());
        mDatabaseEvent = FirebaseDatabase.getInstance().getReference().child("Event");


        //retrieve data
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();

                Set<String> set = new HashSet<String>();

                while (iterator.hasNext()){
                    // get names of all the events one by one
                    DataSnapshot cur = (DataSnapshot) iterator.next();
                    eventkey = (String) cur.getKey();
                    joinname = (String) cur.getValue();
                    //Toast.makeText(NotificationActivity.this, joinname, Toast.LENGTH_LONG).show();
                    set.add(joinname + " joined your event, click to see details.");

                }
                NotificationArrayList.clear();
                NotificationArrayList.addAll(set);

                NotificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        NotificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickedEvent = new Intent(NotificationActivity.this, EventDescription.class);
                clickedEvent.putExtra("event_id", eventkey);
                startActivity(clickedEvent);
            }
        });

    }
}
