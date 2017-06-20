package uw.studybuddy.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leksharamdenee on 2017-06-20.
 */

public class EventList {
    private List<EventInfo> allEvents;
    public static EventList instance;

    public void addEvent(EventInfo newEvent) {
        getInstance().allEvents.add(newEvent);
    }

    public void removeEvent(EventInfo event) {
        getInstance().allEvents.remove(event);
    }

    public EventList getInstance() {
        if (instance == null) {
            instance = new EventList();
            allEvents = new ArrayList<EventInfo>();
        }
        return instance;
    }

}
