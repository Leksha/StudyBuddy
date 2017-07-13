package uw.studybuddy.UserProfile;

import android.media.Image;


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
        CourseInfo newCourse = new CourseInfo(subject, number);
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
    public UserInfo(String displayName, String questID, List<CourseInfo> courses, String aboutMe) {
        mDisplayName = displayName;

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
            instance = new UserInfo(displayName, name, courses, aboutMe);
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


}
