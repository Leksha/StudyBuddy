package uw.studybuddy.Tutoring;

import java.util.UUID;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by leksharamdenee on 2017-07-12.
 */

public class TutorInfo {
    private String uuid;
    private String questId;
    private String name;
    private String course;
    private String price;
    private String phoneNumber;
    private String email;

    public TutorInfo(String course, UserInfo user, String price, String phoneNumber, String email) {
        this.uuid = UUID.randomUUID().toString();
        this.questId = user.getInstance().getQuestID();
        this.name = user.getInstance().getDisplayName();
        this.course = course;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
