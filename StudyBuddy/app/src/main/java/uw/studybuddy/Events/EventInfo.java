package uw.studybuddy.Events;

/**
 * Created by ViVi on 19/06/2017.
 */

public class EventInfo {
    private String course;
    private String description;
    private String location;
    private String title;
    private String uid;
    private String date;
    private String time;
    private String questId;
    private String holder; // holder's user name
    private String[] participants;


    public EventInfo(){}

    public EventInfo(String course, String description, String location, String title) {
        this.course = course;
        this.description = description;
        this.location = location;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String[] getParticipants() {
        return participants;
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
    }
}
