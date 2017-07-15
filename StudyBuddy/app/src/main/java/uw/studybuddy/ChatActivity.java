package uw.studybuddy;

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

import uw.studybuddy.UserProfile.UserInfo;

public class ChatActivity extends AppCompatActivity {
    private Button bSend;
    private TextView receivedMsg;
    private EditText sendMsg;

    private DatabaseReference mDatabaseChat;

    private String chatname;
    private String friendname;

    private String chatUsername;
    private String chatMsg;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    UserInfo User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bSend = (Button) findViewById(R.id.BsendMessage);
        receivedMsg = (TextView) findViewById(R.id.tvReceivedMsg);
        sendMsg = (EditText) findViewById(R.id.edSendMessage);

        chatname = getIntent().getExtras().get("chatname").toString();
        friendname = getIntent().getExtras().get("friendname").toString();
        //Toast.makeText(ChatActivity.this, "hello", Toast.LENGTH_LONG).show();

        setTitle("Chat with " + friendname);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("FriendChat").child(chatname);//.child(mCurrentUser.getDisplayName()).child(eventname);

        //save data
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabaseMsg = mDatabaseChat.push();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("username", User.getInstance().getDisplayName());
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
