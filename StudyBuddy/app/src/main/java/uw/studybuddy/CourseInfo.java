package uw.studybuddy;

import android.support.v4.util.Pair;

import com.endercrest.uwaterlooapi.UWaterlooAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leksharamdenee on 2017-06-27.
 */

public class CourseInfo {
    private String mSubject; // E.g. CS
    private String mCatalogNumber; // E.g. 446
    private String mCourseId;

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        mCatalogNumber = catalogNumber;
    }

    public String toString() {
        return mSubject + " " + mCatalogNumber;
    }

    public CourseInfo(String subject, String number) {
        mSubject = subject;
        mCatalogNumber = number;
    }


    // E.g. Give it ["CS446", "CS115"] and converts it into a list of CourseInfo
    public static List<CourseInfo> getCourseListFromStringArray(String[] courses) {
        int len = courses.length;
        List<CourseInfo> course_list = new ArrayList<CourseInfo>();
        for (int i=0; i<len ; i++) {
            if (!courses[i].isEmpty()) {
                Pair<String, String> p = CourseInfo.processCourseString(courses[i]);
                course_list.add(new CourseInfo(p.first, p.second));
            }
        }
        return course_list;
    }

    // Takes in a for example "CS446" and returns <"CS", "446">
    public static Pair<String, String> processCourseString(String course) {
        char[] arr = course.toCharArray();
        int substr = 0;
        for (int i=0; i<arr.length; i++) {
            if (Character.isDigit(arr[i])) {
                substr = i;
                break;
            }
        }
        String subject = course.substring(0, substr);
        String catNum = course.substring(substr);
        return new Pair<>(subject.toUpperCase(), catNum);
    }

}
