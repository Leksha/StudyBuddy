package uw.studybuddy.UserProfile;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uw.studybuddy.CourseInfo;

/**
 * Created by Yuna on 17/7/7.
 */


public class FirebaseUserInfo {

    final static String TAG = "Firebase UserInfo";

    // All fields in the Users table
    private static String table_users          = "Users";
    private static String field_quest_id       = "quest_id";
    private static String field_display_name   = "display_name";
    private static String field_user_name      = "user_name";
    private static String field_about_me       = "about_me";
    private static String field_read           = "read";

    // Tables in the user table
    public static String table_courses         = "course";

    //update the whole User profile to the firebase
    //if the child is existed in the firebase, then override it.

    public static DatabaseReference getUsersTable() {
        return FirebaseDatabase.getInstance().getReference().child(table_users);
    }


    public static void update_UserInfo(UserPattern USER){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        //String key  = databaseReference.child("Users").push().getKey().toString();
        DatabaseReference DestReference = getUsersTable().child(USER.getquest_id().toString());
        DestReference.setValue(USER);


        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

        //the user.getemail() should display the key now
        UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(USER.getquest_id())
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
        DatabaseReference displayNameRef = getCurrentUserDisplayNameRef();
        //get the key
        displayNameRef.setValue(name);
    }


    // Getters

    public static String get_Email() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
    }

    public static String get_QuestId() {
        String email = get_Email();
        String QuestID = email.substring(0, email.length()-17);
        return QuestID;
    }

    public static DatabaseReference getCurrentUserDisplayNameRef(){
        return getCurrentUserRef().child(field_display_name);
    }

    public static DatabaseReference getCurrentUserRef() {
        return getUsersTable().child(get_QuestId());
    }

    public static FirebaseUser getCurrentFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static DatabaseReference getCurrentUserReadRef() {
        return getCurrentUserRef().child(field_read);
    }

    // Setters

    public static void set_mAboutMe(String mAboutMe){
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

        //get the key
        if(User.getDisplayName() == null) {
            String email = User.getEmail();
            String key = email.substring(0, email.length() - 17);
            getUsersTable().child(key).child(field_about_me).setValue(mAboutMe);
        }else {
            getUsersTable().child(User.getDisplayName().toString()).child(field_about_me).setValue(mAboutMe);
        }
        return;
    }
    // Add a course to the database.
    public static void add_mCourse(int index, String subject, String num){
        String key  = get_QuestId();
        String courseIndexSting = Integer.toString(index);
        if(key != null) {
            DatabaseReference mCourseReference = getUsersTable().child(key).child(table_courses);
            CourseInfo newcourse = new CourseInfo(subject, num);
            mCourseReference.child(courseIndexSting).setValue(newcourse);
        }
        return;
    }

    public static void update_courseInfo(int index, String subject, String num) {
        add_mCourse(index, subject, num);
    }

    public static void set_UserRead(String val) {
        getCurrentUserReadRef().setValue(val);
    }

    public static void remove_mCourse(final String subject, String num){
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        String key;
        if(User.getDisplayName() == null) {
            String email = User.getEmail();
            key = email.substring(0, email.length() - 17);
        }else {
            key  =FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        }
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
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        String key;
        if(User.getDisplayName() == null) {
            String email = User.getEmail();
            key = email.substring(0, email.length() - 17);
        }else {
            key  =FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        }
        DatabaseReference mReadReference = getUsersTable().child(key).child("read");
        mReadReference.setValue("true");
    }

}
