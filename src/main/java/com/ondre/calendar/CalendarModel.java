package com.ondre.calendar;

/**
 * Calendar classes model
 */
public interface CalendarModel {
    /**
     * add event to your calendar
     * @param name name of the event
     * @param startTime when event started
     * @param endTime when event ended
     */
    void addEvent(String name, long startTime, long endTime);

}
