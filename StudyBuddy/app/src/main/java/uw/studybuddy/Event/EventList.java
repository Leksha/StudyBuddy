package uw.studybuddy.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by leksharamdenee on 2017-06-20.
 */

public class EventList {
    public static EventList instance = null;
    private List<EventInfo> allEvents = null;

    public EventList() {
        instance = getInstance();
    }

    public void addEvent(EventInfo newEvent) {
        allEvents.add(newEvent);
    }

    public void removeEvent(EventInfo event) {
        allEvents.remove(event);
    }

    public EventList getInstance() {
        if (instance == null) {
            instance = new EventList();
            allEvents = new ArrayList<EventInfo>();
            createDummyEvent();
        }
        return instance;
    }

    public int getEventListSize() {
        if (allEvents == null) {
            allEvents = new ArrayList<EventInfo>();
            createDummyEvent();
        }
        return allEvents.size();
    }

    public List<EventInfo> getEventList() {
        return allEvents;
    }

    public void createDummyEvent() {
        EventInfo dummy = new EventInfo("Assignment 1", new Date(), "Solve questions");
        addEvent(dummy);
    }

    public void createDummyEvent(String title, String des) {
        EventInfo dummy = new EventInfo(title, new Date(), des);
        addEvent(dummy);
    }

}
