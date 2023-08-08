package com.ondre;

import com.ondre.activity.Activity;
import com.ondre.calendar.CalendarGoogle;

public class Main {
    public static void main(String[] args) {
        Activity activity = new Activity();
        System.out.println(activity.sortProcessList());
        CalendarGoogle calendarGoogle = new CalendarGoogle();
        calendarGoogle.addEvent(activity.sortProcessList());
    }
}