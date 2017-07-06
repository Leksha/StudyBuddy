package uw.studybuddy.UserProfile;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.media.Image;
import android.support.v4.content.ContextCompat;

import com.endercrest.uwaterlooapi.courses.models.Course;

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
    private static String mName;
    private static List<CourseInfo> mCoursesList;
    private static String mAboutMe;
    private static Image mImage;

    // Getters
    public static String getDisplayName() {
        return mDisplayName;
    }

    public static String getName() {
        return mName;
    }

    public static List<CourseInfo> getCourses() {
        return mCoursesList;
    }

    public static String getAboutMe() {
        return mAboutMe;
    }

    // Setters
    public static void updateCourseInfo(int index, String subject, String number) {
        CourseInfo newCourse = new CourseInfo(subject, number);
        mCoursesList.set(index, newCourse);
    }

    public static void setCourses(List<CourseInfo> newList) {
        UserInfo.mCoursesList = new ArrayList<>(newList);
    }
    public static void setDisplayName(String mDisplayName) {
        UserInfo.mDisplayName = mDisplayName;
    }

    public static void setName(String mName) {
        UserInfo.mName = mName;
    }

    public static void setAboutMe(String mAboutMe) {
        UserInfo.mAboutMe = mAboutMe;
    }


    // Constructors
    public UserInfo(String displayName, String name, List<CourseInfo> courses, String aboutMe) {
        mDisplayName = displayName;
        mName = name;
        UserInfo.mCoursesList = new ArrayList<>(courses);
        mAboutMe = aboutMe;
    }

    public UserInfo() {
        CourseInfo[] courses = {new CourseInfo("CS", "446"), new CourseInfo("Cs", "448"),
                new CourseInfo("Math", "235"), new CourseInfo("Econ", "220"), new CourseInfo("Math", "135"),
                new CourseInfo("Phil", "110b"), new CourseInfo("Fr", "221")};

        List<CourseInfo> courseList = new ArrayList<>(Arrays.asList(courses));

        String aboutMe = "I can teach you how to make things fly. Wingardium Leviosa!";

        mDisplayName = "Wizard Kid";
        mName = "Harry Potter";
        setCourses(courseList);
        mAboutMe = aboutMe;
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
