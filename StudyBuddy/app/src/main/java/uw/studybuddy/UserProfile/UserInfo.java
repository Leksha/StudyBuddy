package uw.studybuddy.UserProfile;

import android.media.Image;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uw.studybuddy.CourseInfo;


/**
 * Created by leksharamdenee on 2017-06-12.
 */
public class UserInfo {
    // Singleton
    public static UserInfo instance;

    //Attributes
    private static String mDisplayName;

    private static String mQuestID;
    private static List<CourseInfo> mCoursesList;

    private static String mAboutMe;
    private static Image mImage;

    //add by Yuna
    private static DataSnapshot mFriendlist_DS;
    private static DataSnapshot mUserTable_DS;
    //mUserTable_DS only get once

    // Getters



    public static String getDisplayName() {
        return mDisplayName;
    }

    public static String getQuestID() {
        return mQuestID;
    }

    public List getCoursesList() {
        return mCoursesList;
    }

    public static String getAboutMe() { return mAboutMe; }


    // Setters
    public static void setQuestID(String mName) { UserInfo.mQuestID = mName; }

    public static void updateCourseInfo(int index, String subject, String number) {
        CourseInfo newCourse = new CourseInfo(subject.toUpperCase(), number);
        mCoursesList.set(index, newCourse);
        FirebaseUserInfo.update_courseInfo(index, subject, number);
    }

    public static void setCourses(List<CourseInfo> newList) {
        UserInfo.mCoursesList = new ArrayList<>(newList);
    }

    public static void setDisplayName(String mDisplayName) {
        UserInfo.mDisplayName = mDisplayName;
        FirebaseUserInfo.set_DisplayName(mDisplayName);
    }

    public static void deleteCourse(int index) {
        CourseInfo c = mCoursesList.get(index);
        mCoursesList.remove(index);
        FirebaseUserInfo.update_coursesList(mCoursesList);
    }

    public static void addCourse(String subject, String catNum) {
        CourseInfo course = new CourseInfo(subject, catNum);
        FirebaseUserInfo.add_mCourse(mCoursesList.size(), subject, catNum);
        mCoursesList.add(course);
    }

    public static void setAboutMe(String mAboutMe) {
        UserInfo.mAboutMe = mAboutMe;
        FirebaseUserInfo.set_mAboutMe(mAboutMe);
    }




    // Constructors
    public UserInfo(String displayName, String questID, List<CourseInfo> courses, String aboutMe, DataSnapshot dataSnapshot) {
        mDisplayName = displayName;
        mFriendlist_DS = dataSnapshot;
        mQuestID = questID;
        UserInfo.mCoursesList = new ArrayList<>(courses);

        mAboutMe = aboutMe;
        instance = this;
    }

    private UserInfo() {
        CourseInfo[] courses = {new CourseInfo("CS", "446"), new CourseInfo("CS", "448"),
                new CourseInfo("Math", "235"), new CourseInfo("Econ", "220"), new CourseInfo("Math", "135"),
                new CourseInfo("Phil", "110b"), new CourseInfo("Fr", "221")};

        List<CourseInfo> courseList = new ArrayList<>(Arrays.asList(courses));

        String aboutMe = "I can teach you how to make things fly. Wingardium Leviosa!";

        mDisplayName = "Wizard Kid";

        mQuestID = "Harry Potter";
        setCourses(courseList);

        mAboutMe = aboutMe;
    }

//    public void PatternToUser(UserPattern Pattern){
//        mQuestID =  Pattern.getquest_id();
//        mDisplayName =  Pattern.getdisplay_name();
//        mAboutMe =  Pattern.getabout_me();
//
//        mCoursesList = new ArrayList<>(Pattern.getCourse());
//    }


    // instance methods
    public static void initInstance(String displayName, String name, List<CourseInfo> courses, String aboutMe) {
        if (instance == null) {
            instance = new UserInfo(displayName, name, courses, aboutMe, mFriendlist_DS);
        }
    }

    public static void initInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
    }

    public static UserInfo getInstance() {
        return instance;
    }


    public static DataSnapshot getmFriendlist_DS() {
        return mFriendlist_DS;
    }

    public static void setmFriendlist_DS(DataSnapshot mFriendlist_DS) {
        UserInfo.mFriendlist_DS = mFriendlist_DS;
    }

    public static DataSnapshot getmUserTable_DS() {
        return mUserTable_DS;
    }

    public static void setmUserTable_DS(DataSnapshot mUserTable_DS) {
        UserInfo.mUserTable_DS = mUserTable_DS;
    }
}
