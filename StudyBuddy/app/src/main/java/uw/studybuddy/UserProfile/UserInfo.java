package uw.studybuddy.UserProfile;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.media.Image;
import android.support.v4.content.ContextCompat;


import uw.studybuddy.UserProfile.dummy.FirebaseUserInfo;
import uw.studybuddy.UserProfile.dummy.UserPattern;

import com.endercrest.uwaterlooapi.courses.models.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public static List getCoursesList() {
        return mCoursesList;
    }

    public static String getAboutMe() { return mAboutMe; }


    // Setters
    public static void setQuestID(String mName) { UserInfo.mQuestID = mName; }

    public static void updateCourseInfo(int index, String subject, String number) {
        CourseInfo newCourse = new CourseInfo(subject, number);
        mCoursesList.set(index, newCourse);
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
        FirebaseUserInfo.remove_mCourse(c.getSubject(), c.getCatalogNumber());
        mCoursesList.remove(index);
    }

    public static void addCourse(String subject, String catNum) {
        CourseInfo course = new CourseInfo(subject, catNum);
        mCoursesList.add(course);
        FirebaseUserInfo.add_mCourse(subject, catNum);
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

    public void PatternToUser(UserPattern Pattern){
        mQuestID =  Pattern.getmQuestID();
        mDisplayName =  Pattern.getDisplayName();
        mAboutMe =  Pattern.getAbout_me();

        mCoursesList = new ArrayList<>(Pattern.getCourse());
    }


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
        initInstance();
        return instance;
    }


}
