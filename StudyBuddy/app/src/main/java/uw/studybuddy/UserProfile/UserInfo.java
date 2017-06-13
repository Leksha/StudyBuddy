package uw.studybuddy.UserProfile;

/**
 * Created by leksharamdenee on 2017-06-12.
 */

public class UserInfo {
    //Attributes
    private static String mDisplayName;
    private static String mName;
    private static String[] mCourses;
    private static String mAboutMe;

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
        UserInfo.mCourses = mCourses;
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
        String aboutMe = "I can teach you how to make things fly. Wingardium Leviosa! Try it!";

        mDisplayName = "Wizard Kid";
        mName = "Harry Potter";
        mCourses = courses;
        mAboutMe = aboutMe;
    }
}
