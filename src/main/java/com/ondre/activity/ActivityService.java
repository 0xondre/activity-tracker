package com.ondre.activity;

import com.ondre.calendar.CalendarService;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityService implements Runnable {
    private final CalendarService calendarService;
    private long startTime;

    public ActivityService(CalendarService calendarService){
        this.calendarService = calendarService;
    }

    @Override
    public void run(){
        Activity activity = new Activity();

        TimerTask task = createTimerTask(activity);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1*60000);
        startTime = System.currentTimeMillis() + 3600000;

        Thread t = new Thread();
        t.start();

    }

    // Create a TimerTask based on the provided activity
    protected TimerTask createTimerTask(Activity activity) {
        return new TimerTask() {
            String processName = activity.sortProcessList();
            long endTime = System.currentTimeMillis() + 3600000;;

            @Override
            public void run() {
                endTime = System.currentTimeMillis() + 3600000;
                if (!(startTime==endTime)) {
                    calendarService.addEvent(processName, startTime, endTime);
                }
            }
        };
    }
}