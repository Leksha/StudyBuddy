package uw.studybuddy.Resources;

import java.util.UUID;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by leksharamdenee on 2017-07-14.
 */

public class ResourceInfo {
    private String uuid;
    private String course;
    private String title;
    private String postedBy;
    private String link;

    public ResourceInfo(){}

    public ResourceInfo(String course, String title, UserInfo user, String link) {
        this.uuid = UUID.randomUUID().toString();
        this.course = course;
        this.title = title;
        this.postedBy = user.getDisplayName();
        this.link = link;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
