package uw.studybuddy.Events;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import uw.studybuddy.R;

/**
 * Created by leksharamdenee on 2017-06-27.
 * Uses the event_cardview.xml file
 */

public class EventCardViewHolder extends RecyclerView.ViewHolder {
    View mView;

    Button BjoinEvent;
    private DatabaseReference mDatabaseJoinEvent;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    public EventCardViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        BjoinEvent = (Button)mView.findViewById(R.id.BjoinEvent);

        mDatabaseJoinEvent = FirebaseDatabase.getInstance().getReference().child("Participants");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabaseJoinEvent.keepSynced(true);

    }

    /*if(mCurrentUser.getUid().equals(uid)){
        bDeleteEvent.setVisibility(View.VISIBLE);
    }*/

    public void setJoinEvent(final String eventkey, final String uid){
        mDatabaseJoinEvent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(eventkey).hasChild(mCurrentUser.getUid())) {
                    BjoinEvent.setText("Leave");
                } else if (mCurrentUser.getUid().equals(uid)) {
                    BjoinEvent.setText("Review");
                    //BjoinEvent.setVisibility(View.INVISIBLE);
                } else {
                    BjoinEvent.setText("Join");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setDate(String date){
        TextView dat = (TextView)mView.findViewById(R.id.tvDate);
        dat.setText(date);
    }
    public void setCourse(String course){
        TextView cour = (TextView)mView.findViewById(R.id.tvCourse);
        cour.setText(course);
    }
    public void setImage(Context ctx, Uri image){
        ImageView user_image = (ImageView) mView.findViewById(R.id.EventUserImage);
        Picasso.with(ctx).load(image).into(user_image);
    }
    public void setTitle(String title){
        TextView til = (TextView)mView.findViewById(R.id.tvSubject);
        til.setText(title);
    }
}
