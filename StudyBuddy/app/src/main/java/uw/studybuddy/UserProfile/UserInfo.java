package uw.studybuddy.UserProfile;

import android.media.Image;
import android.support.v4.content.ContextCompat;

/**
 * Created by leksharamdenee on 2017-06-12.
 */

public class UserInfo {
    // Singleton
    public static UserInfo instance;

    //Attributes
    private static String mDisplayName;
    private static String mName;
    private static String[] mCourses;
    private static String mAboutMe;
    private static Image mImage;

    // Getters and Setters
    public static String getDisplayName() {
        return mDisplayName;
    }

    public static void setDisplayName(String mDisplayName) {
        UserInfo.mDisplayName = mDisplayName;
    }

    public static String getName() {
        return mName;
    }

    public static void setName(String mName) {
        UserInfo.mName = mName;
    }

    public static String[] getCourses() {
        return mCourses;
    }

    public static void setCourses(String[] mCourses) {
        UserInfo.mCourses = mCourses.clone();
    }

    public static String getAboutMe() {
        return mAboutMe;
    }

    public static void setAboutMe(String mAboutMe) {
        UserInfo.mAboutMe = mAboutMe;
    }

    // Constructors
    public UserInfo(String displayName, String name, String[] courses, String aboutMe) {
        mDisplayName = displayName;
        mName = name;
        mCourses = courses.clone();
        mAboutMe = aboutMe;
    }

    public UserInfo() {
        String[] courses = {"Charms", "Dark Arts", "Herbology", "Muggle Studies"};
        String aboutMe = "I can teach you how to make things fly. Wingardium Leviosa!";

        mDisplayName = "Wizard Kid";
        mName = "Harry Potter";
        mCourses = courses;
        mAboutMe = aboutMe;
    }

    // instance methods
    public static void initInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
    }

    public static UserInfo getInstance() {
        initInstance();
        return instance;
    }
}
