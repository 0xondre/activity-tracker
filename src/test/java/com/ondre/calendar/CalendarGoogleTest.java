package com.ondre.calendar;

import com.ondre.calendar.CalendarGoogle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalendarGoogleTest {
    private CalendarGoogle calendar;

    @Before
    public void setUp() {
        calendar = new CalendarGoogle();
    }

    @Test
    public void testAddEvent() {
        // Provide the event details for testing
        String eventName = "Test Event";
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 3600000; // Adding 1 hour to start time

        // Test the addEvent method
        try {
            calendar.addEvent(eventName, startTime, endTime);
        } catch (Exception e) {
            fail("Exception thrown when adding an event: " + e.getMessage());
        }

    }
}