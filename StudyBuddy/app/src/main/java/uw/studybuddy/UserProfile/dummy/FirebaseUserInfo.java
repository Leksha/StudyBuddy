package uw.studybuddy.UserProfile.dummy;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by Yuna on 17/7/7.
 */


public class FirebaseUserInfo {

    static String TAG = "Firebase UserInfo";

    //update the whole user info (create a new reference)
    public static void update_UserInfo(UserPattern USER){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        //String key  = databaseReference.child("Users").push().getKey().toString();
        DatabaseReference DestReference = databaseReference.child("Users").child(USER.getmQuestID().toString());
        DestReference.setValue(USER);

        final String TAG = "Firebase UserInfo";


        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

        //the user.getemail() should display the key now
        UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(USER.getmQuestID())
                .build();
        User.updateProfile(profileupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User profile updated.");
                }
            }
        });

        return;
    }

    public static void set_Username(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        //get the key
        databaseReference.child("Users").child(User.getDisplayName().toString()).child("DisplayName").setValue(name);

        return;
    }
    public static void set_mAboutMe(String mAboutMe){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

        //get the key
        databaseReference.child("Users").child(User.getDisplayName().toString()).child("About_me").setValue(mAboutMe);
        return;
    }

}
