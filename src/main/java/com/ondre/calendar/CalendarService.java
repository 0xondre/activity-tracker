package com.ondre.calendar;

/**
 * handler for all types of calendars
 */
public class CalendarService {
    private final CalendarModel calendar;
    /**
     * constructor
     * @param calendar calendar object
     */
    public CalendarService(CalendarModel calendar) {
        this.calendar = calendar;
    }
    /**
     * add event to the calendar
     * @param name name of the event
     * @param startTime when event started
     * @param endTime when event ended
     */
    public void addEvent(String name, long startTime, long endTime){
        calendar.addEvent(name, startTime, endTime);
    }

}
