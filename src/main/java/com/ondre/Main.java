package com.ondre;

import com.ondre.activity.ActivityService;
import com.ondre.calendar.CalendarGoogle;

public class Main {
    public static void main(String[] args) {
        ActivityService activityService = new ActivityService(new CalendarGoogle());
        activityService.start();
    }
}