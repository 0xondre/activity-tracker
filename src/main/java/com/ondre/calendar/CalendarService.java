package com.ondre.calendar;

import com.ondre.activity.Activity;

public class CalendarService {
    CalendarModel calendar;
    Activity activity = new Activity();
    public CalendarService(CalendarModel calendar) {
        this.calendar = calendar;
    }
    public void addEvent(){
        calendar.addEvent(activity.sortProcessList());
    }

}
