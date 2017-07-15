package uw.studybuddy;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import uw.studybuddy.Events.EventCreation;


/**
 * Created by apalissery on 2017-07-05.
 */

public class FirebaseInstance {

   //Firebase.setAndroidContext(this);

    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabase;
    private static DatabaseReference mDatabaseUserInfo;
    private static FirebaseAuth.AuthStateListener mAuthStateListener;

    private static void initDBInstance() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
    }

    private static void initAuthInstance() {
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }

    public static DatabaseReference getDatabaseInstance() {
        initDBInstance();
        return mDatabase;
    }

    public static FirebaseAuth getFirebaseAuthInstance() {
        initAuthInstance();
        return mAuth;
    }

    public static FirebaseAuth.AuthStateListener getAuthStateListener() {
        return mAuthStateListener;
    }

    //when creating an event
    static boolean retVal = true;
    public static boolean addNewEventToDatabase(String course, String title, String location, String description,
                                                String uid, final String questId, String date, String time, String username){
        final HashMap<String, String> dataMap = new HashMap<String, String>();
        final DatabaseReference curDB = getDatabaseInstance().child("Event");
        /*mDatabaseUserInfo = getDatabaseInstance().child("Users");
        mDatabaseUserInfo.child(questId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = (String) dataSnapshot.child("displayName").getValue();
                //dataMap.put("username", username);
                curDB.child("username").setValue(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        if(!TextUtils.isEmpty(course) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)){
            dataMap.put("course", course);
            dataMap.put("description", description);
            dataMap.put("location", location);
            dataMap.put("title", title);
            dataMap.put("uid", uid);
            dataMap.put("questId", questId);
            dataMap.put("date", date);
            dataMap.put("time", time);
            dataMap.put("username", username);

            curDB.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        retVal = true;
                    } else {
                        retVal = false;
                    }
                }
            });
        }

        return retVal;

    }

}
