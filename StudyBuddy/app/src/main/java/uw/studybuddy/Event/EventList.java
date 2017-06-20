package uw.studybuddy.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leksharamdenee on 2017-06-20.
 */

public class EventList {
    private List<EventInfo> allEvents;

    public EventList() {
        allEvents = new ArrayList<EventInfo>();
    }

    public void addEvent(EventInfo newEvent) {
        allEvents.add(newEvent);
    }

    public void removeEvent(EventInfo event) {
        allEvents.remove(event);
    }

}
