package uw.studybuddy.Chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;

public class ChatActivity extends AppCompatActivity {

    private ListView JoinedEventList;
    private ArrayList<String> EventArrayList;
    private ArrayAdapter<String> EventAdapter;

    private String username;

    DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    UserInfo User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        JoinedEventList = (ListView) findViewById(R.id.JoinedEventList);

        EventArrayList = new ArrayList<String>();
        EventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,EventArrayList);

        JoinedEventList.setAdapter(EventAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("EventChat");//.child(mCurrentUser.getDisplayName());


        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        username = User.getInstance().getDisplayName();
        //requestEvent();

        //retrieve data
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();

                Set<String> set = new HashSet<String>();

                while (iterator.hasNext()){
                    // get names of all the events one by one
                    set.add((String) ((DataSnapshot)iterator.next()).getKey());

                }
                EventArrayList.clear();
                EventArrayList.addAll(set);

                EventAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        JoinedEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatActivity.this, chat_event.class);
                intent.putExtra("eventname", ((TextView)view).getText().toString());
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }

    private void requestEvent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter user");
        final EditText editText = new EditText(this);

        builder.setView(editText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                username = editText.getText().toString();

                if(!TextUtils.isEmpty(username)){

                } else {
                    requestEvent();
                }

            }
        }).setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                requestEvent();
            }
        });
        builder.show();
    }
}
