package com.ondre.activity;

import com.ondre.calendar.CalendarService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Timer;
import java.util.TimerTask;

import static org.mockito.Mockito.*;

public class ActivityServiceTest {

    @Mock
    private CalendarService calendarService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStart() throws InterruptedException {
        // Create a mock Activity object
        Activity activity = mock(Activity.class);
        when(activity.sortProcessList()).thenReturn("Process1");

        // Create an ActivityService instance with the mock CalendarService and Activity
        ActivityService activityService = new ActivityService(calendarService);

        // Create a TimerTask to be executed by the Timer
        TimerTask task = activityService.createTimerTask(activity);

        // Create a Timer and schedule the task
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 60000);

        // Sleep for 2 minutes (simulating the application running)
        Thread.sleep(2 * 60 * 1000);

        // Verify that the addEvent method was called on the calendarService
        verify(calendarService, atLeastOnce()).addEvent(eq("Process1"), anyLong(), anyLong());

        // Cancel the timer
        timer.cancel();
    }
}