package uw.studybuddy.Chat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import uw.studybuddy.R;
import uw.studybuddy.UserProfile.UserInfo;

public class chat_event extends AppCompatActivity {

    private Button bSend;
    private TextView receivedMsg;
    private EditText sendMsg;

    private DatabaseReference mDatabaseChat;

    private String eventname;
    private String username;

    private String chatUsername;
    private String chatMsg;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_event);

        bSend = (Button) findViewById(R.id.BsendMessage);
        receivedMsg = (TextView) findViewById(R.id.tvReceivedMsg);
        sendMsg = (EditText) findViewById(R.id.edSendMessage);

        eventname = getIntent().getExtras().get("eventname").toString();
        username = getIntent().getExtras().get("username").toString();

        setTitle(eventname);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("EventChat").child(eventname);//.child(mCurrentUser.getDisplayName()).child(eventname);

        //save data
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabaseMsg = mDatabaseChat.push();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("username", username);
                map.put("message", sendMsg.getText().toString());

                mDatabaseMsg.updateChildren(map);
                sendMsg.setText("");
            }
        });

        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateMsg(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateMsg(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Load msgs
    private void updateMsg(DataSnapshot dataSnapshot) {
        chatUsername = (String) dataSnapshot.child("username").getValue();
        chatMsg = (String) dataSnapshot.child("message").getValue();

        receivedMsg.append(chatUsername + " : " + chatMsg +"\n\n");
    }
}
