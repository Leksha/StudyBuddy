package uw.studybuddy;

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

}
