package uw.studybuddy;

import android.support.v4.util.Pair;

import com.endercrest.uwaterlooapi.UWaterlooAPI;

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
        return new Pair<>(subject, catNum);
    }

}
