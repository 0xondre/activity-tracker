package com.ondre.calendar;

import com.ondre.activity.Activity;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityTest {
    private Activity activity;

    @Before
    public void setUp() {
        activity = new Activity();
    }

    @Test
    public void testSortProcessList() {
        String expectedProcess = "idea64.exe";

        activity = new Activity();

        String result = activity.sortProcessList();

        assertEquals(expectedProcess, result);
    }
}