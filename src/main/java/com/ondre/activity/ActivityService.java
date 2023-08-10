package com.ondre.activity;

import com.ondre.calendar.CalendarModel;
import com.ondre.calendar.CalendarService;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityService {
    //It must update calendar few times so its prolly better if it calls calendarService
    private final CalendarModel calendarModel;
    public ActivityService(CalendarModel calendarModel){
        this.calendarModel = calendarModel;
    }

    public void start(){
        CalendarService calendarService = new CalendarService(calendarModel);

        Activity activity = new Activity();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            String processName = "";
            long startTime = 0;
            long endTime = 0;
            int i = 0;
            @Override
            public void run() {
                endTime=System.currentTimeMillis()+3600000;
                if(!processName.equals(activity.sortProcessList()) && i==0) {
                startTime = System.currentTimeMillis()+3600000;
                }else if(!processName.equals(activity.sortProcessList())){
                    calendarService.addEvent(processName, startTime, endTime);
                    startTime = System.currentTimeMillis()+3600000;
                }
                processName = activity.sortProcessList();
                //Update end time
                i++;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 60000);

        // 5 minutes of running
        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Cancel the timer (stop the task from running)
        timer.cancel();
    }

}
