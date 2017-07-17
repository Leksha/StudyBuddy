package uw.studybuddy.Events;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

import uw.studybuddy.MainActivity;
import uw.studybuddy.R;
import uw.studybuddy.UserProfile.FirebaseUserInfo;
import uw.studybuddy.UserProfile.UserInfo;

public class EventDescription extends AppCompatActivity {

    private String mPostKey = null;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseJoin;
    private DatabaseReference mDatabaseJoinLoc;

    private TextView edCourse;
    private TextView edTitle;
    private TextView edDescription;
    private TextView edLocation;
    private TextView edDate;
    private TextView edTime;
    private TextView edQuestId;
    private TextView edParticipants;

    private Button bDeleteEvent;
    private Button bAddFriend;
    private Button bViewMap;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    UserInfo User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        edCourse = (TextView) findViewById(R.id.edCourse);
        edTitle = (TextView) findViewById(R.id.edTitle);
        edDescription = (TextView) findViewById(R.id.edDescription);
        edLocation = (TextView) findViewById(R.id.edLocation);
        edDate = (TextView) findViewById(R.id.edDate);
        edTime = (TextView) findViewById(R.id.edTime);
        edQuestId = (TextView) findViewById(R.id.edQuestId);
        edParticipants = (TextView) findViewById(R.id.edParticipants);

        bDeleteEvent = (Button) findViewById(R.id.BdeleteEvent);
        bAddFriend = (Button) findViewById(R.id.BaddFriend);
        bViewMap = (Button) findViewById(R.id.btnOpenMap);

        mPostKey = getIntent().getExtras().getString("event_id");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        mDatabaseJoin = FirebaseDatabase.getInstance().getReference().child("Participants");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();



        mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String course = (String) dataSnapshot.child("course").getValue();
                String title = (String) dataSnapshot.child("title").getValue();
                String description = (String) dataSnapshot.child("description").getValue();
                String location = (String) dataSnapshot.child("location").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String time = (String) dataSnapshot.child("time").getValue();
                String uid = (String) dataSnapshot.child("uid").getValue();
                final String questId = (String) dataSnapshot.child("questId").getValue();
                final String username = (String) dataSnapshot.child("username").getValue();

                edCourse.setText(course);
                edTime.setText(time);
                edDate.setText(date);
                edLocation.setText(location);
                edDescription.setText(description);
                edTitle.setText(title);
                edQuestId.setText(username);

                if(mCurrentUser.getUid().equals(uid)){
                    bDeleteEvent.setVisibility(View.VISIBLE);
                }

                if(mCurrentUser.getUid().equals(uid)){
                    bAddFriend.setVisibility(View.INVISIBLE);
                }

                bAddFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EventDescription.this, "You have add "+username+ " to the FriendList.", Toast.LENGTH_LONG).show();
                        FirebaseUserInfo.getCurrentUserRef().child(FirebaseUserInfo.table_friend).child(questId).setValue(questId);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseJoin.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //int numParticipants = (int) dataSnapshot.getChildrenCount();
                String participants = "";
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String participant = (String) child.getValue();
                    if(participants.equals("")){
                        participants = participant;
                    } else {
                        participants = participants + " , " + participant;
                    }
                    edParticipants.setText(participants);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPostKey).removeValue();
                mDatabaseJoin.child(mPostKey).removeValue();
                finish();
            }
        });


        bViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if(dataSnapshot.getChildren() != null) {
                            String location = (String) dataSnapshot.child("location").getValue();

                            if(location != null) {
                                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + location);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            } else {
                                Toast.makeText(EventDescription.this, "Location doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
