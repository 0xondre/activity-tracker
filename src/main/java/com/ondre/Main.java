package com.ondre;

import com.ondre.calendar.CalendarGoogle;
import com.ondre.calendar.CalendarService;

public class Main {
    public static void main(String[] args) {
        CalendarService calendarService = new CalendarService(new CalendarGoogle());
        calendarService.addEvent();
    }
}