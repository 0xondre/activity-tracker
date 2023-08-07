package com.ondre.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class CalendarGoogle implements CalendarModel {
    private static final String APPLICATION_NAME = "activity-tracker";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final String calendarId = "primary";
    //("https://www.googleapis.com/calendar/v3/calendars/%s/events", calendarId)

    public void addEvent() {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = getCredentials(httpTransport);

            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            Event event = new Event()
                    .setSummary("Google I/O 2015")
                    .setStart(new EventDateTime()
                            .setDateTime(new DateTime("2023-08-07T09:00:00-07:00"))
                            .setTimeZone("Europe/Prague"))
                    .setEnd(new EventDateTime()
                            .setDateTime(new DateTime(System.currentTimeMillis()+3600000))
                            .setTimeZone("Europe/Prague"));
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
