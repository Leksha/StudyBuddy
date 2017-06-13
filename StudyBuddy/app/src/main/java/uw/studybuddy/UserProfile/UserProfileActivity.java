package uw.studybuddy.UserProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import uw.studybuddy.R;

public class UserProfileActivity extends AppCompatActivity {

    private TextView mUserDisplayName;
    private TextView mUserName;
    private TextView mUserCourses;
    private TextView mUserAboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUserDisplayName = (TextView)findViewById(R.id.user_profile_display_name);
        mUserName = (TextView)findViewById(R.id.user_profile_name);
        mUserCourses = (TextView)findViewById(R.id.user_profile_courses);
        mUserAboutMe = (TextView)findViewById(R.id.user_profile_about_me);

        // For the purpose of the demo, we will create a user to display
        UserInfo demoUser = new UserInfo();

        // Update the user profile view with the right user info
        String courses = "";
        String[] userCourses = demoUser.getCourses();
        int length = userCourses.length;
        for (int i=0; i<length; i++) {
            courses += userCourses[i];
            if (i<length-1) {
                courses += ", ";
            }
        }
        mUserDisplayName.setText(demoUser.getDisplayName());
        mUserName.setText(demoUser.getName());
        mUserCourses.setText(courses);
        mUserAboutMe.setText(demoUser.getAboutMe());
    }
}
