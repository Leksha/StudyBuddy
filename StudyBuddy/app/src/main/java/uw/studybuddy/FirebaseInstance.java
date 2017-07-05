package uw.studybuddy;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import uw.studybuddy.Events.EventCreation;


/**
 * Created by apalissery on 2017-07-05.
 */

public class FirebaseInstance {

   //Firebase.setAndroidContext(this);

    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabase;

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
        //when creating an event
    static boolean retVal = false;
    public static boolean addNewEventToDatabase(String course, String title, String location, String description){
        DatabaseReference curDB = getDatabaseInstance().child("EventInfo");

        final HashMap<String, String> dataMap = new HashMap<String, String>();
        if(!TextUtils.isEmpty(course) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(title)){
            dataMap.put("COURSE", course);
            dataMap.put("DESCRIPTION", description);
            dataMap.put("LOCATION", location);
            dataMap.put("TITLE", title);

            curDB.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        retVal = true;
                    }
                }
            });
        }

        return retVal;

    }



}
