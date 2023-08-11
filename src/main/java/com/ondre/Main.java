package com.ondre;

import com.ondre.activity.ActivityService;
import com.ondre.calendar.CalendarGoogle;
import com.ondre.calendar.CalendarService;

public class Main {
    public static void main(String[] args) {
        ActivityService activityService = new ActivityService(new CalendarService(new CalendarGoogle()));
        activityService.start();
    }
}