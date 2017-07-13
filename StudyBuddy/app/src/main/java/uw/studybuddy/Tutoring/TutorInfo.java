package uw.studybuddy.Tutoring;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by leksharamdenee on 2017-07-12.
 */

public class TutorInfo {
    private String course;
    private UserInfo tutor;
    private String price;
    private String phoneNumber;

    public TutorInfo(String course, UserInfo tutor, String price, String phoneNumber) {
        this.course = course;
        this.tutor = tutor;
        this.price = price;
        this.phoneNumber = phoneNumber;
    }

}
