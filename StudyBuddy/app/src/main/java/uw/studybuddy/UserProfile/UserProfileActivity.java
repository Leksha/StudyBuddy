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

    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUserDisplayName = (TextView)findViewById(R.id.user_profile_display_name);
        mUserName = (TextView)findViewById(R.id.user_profile_name);
        mUserCourses = (TextView)findViewById(R.id.user_profile_courses);
        mUserAboutMe = (TextView)findViewById(R.id.user_profile_about_me);

        // For the purpose of the demo, we will create a user to display
        user = UserInfo.getInstance();

        // Update the user profile view with the right user info
        mUserDisplayName.setText(user.getDisplayName());
        mUserName.setText(user.getName());
        mUserCourses.setText(getCoursesString());
        mUserAboutMe.setText(user.getAboutMe());
    }

    // Process the way user courses is displayed
    private String getCoursesString() {
        if (user == null) {
            return "";
        }
        String courses = "";
        String[] userCourses = user.getCourses();
        int length = userCourses.length;
        for (int i=0; i<length; i++) {
            courses += userCourses[i];
            if (i<length-1) {
                courses += ", ";
            }
        }
        return courses;
    }
}
