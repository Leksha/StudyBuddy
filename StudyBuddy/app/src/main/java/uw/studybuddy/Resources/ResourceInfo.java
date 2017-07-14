package uw.studybuddy.Resources;

import android.widget.Button;

import java.util.UUID;

import uw.studybuddy.UserProfile.UserInfo;

/**
 * Created by leksharamdenee on 2017-07-14.
 */

public class ResourceInfo {
    private String uuid;
    private String questId;
    private String course;
    private String title;
    private String link;
    private boolean isAnonymous;

    public ResourceInfo(){}

    public ResourceInfo(String course, String title, UserInfo user, String link, boolean isAnonymous) {
        this.uuid = UUID.randomUUID().toString();
        this.course = course;
        this.title = title;
        this.questId = user.getQuestID();
        this.link = link;
        this.isAnonymous = isAnonymous;
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

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
