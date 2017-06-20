package uw.studybuddy.Event;

import java.util.Date;

/**
 * Created by leksharamdenee on 2017-06-20.
 */

public class EventInfo {
    private String title;
    private Date date;
    private String description;

    public EventInfo(String eventTitle, Date eventDate, String eventDescription) {
        title = eventTitle;
        date = eventDate;
        description = eventDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
