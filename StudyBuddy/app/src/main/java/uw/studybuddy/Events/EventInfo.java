package uw.studybuddy.Events;

/**
 * Created by ViVi on 19/06/2017.
 */

public class EventInfo {
    private String course;
    private String description;
    private String location;
    private String subject;

    public EventInfo(){

    }

    public EventInfo(String course, String description, String location, String subject) {
        this.course = course;
        this.description = description;
        this.location = location;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
}
