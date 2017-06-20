package uw.studybuddy.Event;

import java.text.SimpleDateFormat;
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

    public String getDateAsString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String dateString = sdf.format(date);
        return dateString;
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
