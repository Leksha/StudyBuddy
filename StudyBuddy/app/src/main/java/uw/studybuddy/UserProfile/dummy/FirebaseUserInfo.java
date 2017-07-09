package uw.studybuddy.UserProfile.dummy;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

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

import uw.studybuddy.CourseInfo;
import uw.studybuddy.Events.EventCreation;
import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by Yuna on 17/7/7.
 */


public class FirebaseUserInfo {

    final static String TAG = "Firebase UserInfo";

    // All fields in the Users table
    private static String table_users          = "table_users";
    private static String field_quest_id       = "quest_id";
    private static String field_display_name   = "display_name";
    private static String field_user_name      = "user_name";
    private static String field_about_me       = "about_me";

    // Tables in the user table
    private static String table_courses         = "courses";

    //update the whole User profile to the firebase
    //if the child is existed in the firebase, then override it.

    public static DatabaseReference getUsersTable() {
        return FirebaseDatabase.getInstance().getReference().child(table_users);
    }

    public static void update_UserInfo(UserPattern USER){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        //String key  = databaseReference.child("Users").push().getKey().toString();
        DatabaseReference DestReference = getUsersTable().child(USER.getmQuestID().toString());
        DestReference.setValue(USER);

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

    public static void set_DisplayName(String name){
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        //get the key
        getUsersTable().child(User.getDisplayName().toString()).child(field_display_name).setValue(name);

        return;
    }
    public static void set_mAboutMe(String mAboutMe){
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

        //get the key
        getUsersTable().child(User.getDisplayName().toString()).child(field_about_me).setValue(mAboutMe);
        return;
    }
    //add a course to the database.
    public static void add_mCourse(String subject, String num){
        String key = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String coursename = subject + num;
        if(key != null) {
            DatabaseReference mCourseReference = getUsersTable().child(key).child(table_courses);

            CourseInfo newcourse = new CourseInfo(subject, num);
            mCourseReference.child(coursename).setValue(newcourse);
        }
        return;
    }

    public static void remove_mCourse(final String subject, String num){
        String key = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String coursename = subject + num;
        if(key != null) {
            DatabaseReference mCourseReference = getUsersTable().child(key).child(table_courses);

            mCourseReference.child(coursename).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) { // No Errors
                        Log.d(TAG, "Error removing course: " + databaseError.getMessage());
                    } else {
                        Log.d(TAG, "Success removing course: ");
                    }
                }
            });
        }
    }



    //update the read filed to make the listener function run
    public static void listener_trigger(){
        String key = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        DatabaseReference mReadReference = getUsersTable().child("key").child("read");
        mReadReference.setValue("true");
    }

}
