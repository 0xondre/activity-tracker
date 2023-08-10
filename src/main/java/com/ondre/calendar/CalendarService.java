package com.ondre.calendar;

import com.ondre.activity.Activity;

public class CalendarService {
    private final CalendarModel calendar;
    Activity activity = new Activity();
    public CalendarService(CalendarModel calendar) {
        this.calendar = calendar;
    }
    // make this run every time new process consume most resources, maybe better in activity
    public void addEvent(String name, long startTime, long endTime){
        calendar.addEvent(name, startTime, endTime);
    }

}
